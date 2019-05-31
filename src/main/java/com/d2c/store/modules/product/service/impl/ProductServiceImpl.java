package com.d2c.store.modules.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.store.common.api.PageModel;
import com.d2c.store.common.api.base.BaseService;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.modules.product.mapper.ProductMapper;
import com.d2c.store.modules.product.model.ProductDO;
import com.d2c.store.modules.product.model.ProductSkuDO;
import com.d2c.store.modules.product.query.ProductQuery;
import com.d2c.store.modules.product.query.ProductSkuQuery;
import com.d2c.store.modules.product.service.ProductService;
import com.d2c.store.modules.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BaiCai
 */
@Service
public class ProductServiceImpl extends BaseService<ProductMapper, ProductDO> implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuService productSkuService;

    @Override
    @Transactional
    public ProductDO doCreate(ProductDO product) {
        this.initStock(product);
        product.setSales(0);
        this.save(product);
        for (ProductSkuDO sku : product.getSkuList()) {
            sku.setProductId(product.getId());
            sku.setBrandId(product.getBrandId());
            sku.setSupplierId(product.getSupplierId());
            sku.setStatus(product.getStatus());
            sku.setFreight(product.getFreight());
            productSkuService.save(sku);
        }
        return product;
    }

    @Override
    @Transactional
    public boolean doUpdate(ProductDO product) {
        this.initStock(product);
        product.setSales(null);
        ProductSkuQuery query = new ProductSkuQuery();
        query.setProductId(product.getId());
        List<ProductSkuDO> oldList = productSkuService.list(QueryUtil.buildWrapper(query));
        Map<Long, ProductSkuDO> oldMap = new ConcurrentHashMap<>();
        oldList.forEach(item -> oldMap.put(item.getId(), item));
        for (ProductSkuDO sku : product.getSkuList()) {
            if (sku.getId() != null) {
                // 更新
                sku.setProductId(product.getId());
                sku.setBrandId(product.getBrandId());
                sku.setSupplierId(product.getSupplierId());
                sku.setStatus(product.getStatus());
                sku.setFreight(product.getFreight());
                productSkuService.updateById(sku);
                oldMap.remove(sku.getId());
            } else {
                // 新增
                sku.setProductId(product.getId());
                sku.setBrandId(product.getBrandId());
                sku.setSupplierId(product.getSupplierId());
                sku.setStatus(product.getStatus());
                sku.setFreight(product.getFreight());
                productSkuService.save(sku);
            }
        }
        // 删除
        for (ProductSkuDO sku : oldMap.values()) {
            productSkuService.removeById(sku.getId());
        }
        boolean success = this.updateById(product);
        return success;
    }

    private void initStock(ProductDO product) {
        int stock = 0;
        for (ProductSkuDO sku : product.getSkuList()) {
            stock += sku.getStock();
        }
        product.setStock(stock);
    }

    @Override
    @Transactional
    public int doDeductStock(Long id, Integer quantity) {
        return productMapper.doDeductStock(id, quantity);
    }

    @Override
    @Transactional
    public int doReturnStock(Long id, Integer quantity) {
        return productMapper.doReturnStock(id, quantity);
    }

    @Override
    public Page<ProductDO> findByQuery(Long p2pId, ProductQuery query, PageModel page) {
        Page<ProductDO> pager = new Page<>();
        int total = productMapper.countByQuery(p2pId, query);
        if (total > 0) {
            List<ProductDO> list = productMapper.findByQuery(p2pId, query, page.offset(), page.getSize(), page.orderByStr("p"));
            pager.setCurrent(page.getCurrent());
            pager.setSize(page.getSize());
            pager.setTotal(total);
            pager.setRecords(list);
        }
        return pager;
    }

    @Override
    public int doUpdateSales(Long id, Integer quantity) {
        return productMapper.doUpdateSales(id, quantity);
    }

}
