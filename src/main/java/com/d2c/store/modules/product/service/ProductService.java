package com.d2c.store.modules.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.store.common.api.PageModel;
import com.d2c.store.modules.product.model.ProductDO;
import com.d2c.store.modules.product.query.ProductQuery;

/**
 * @author BaiCai
 */
public interface ProductService extends IService<ProductDO> {

    ProductDO doCreate(ProductDO product);

    boolean doUpdate(ProductDO product);

    int doDeductStock(Long id, Integer quantity);

    int doReturnStock(Long id, Integer quantity);

    Page<ProductDO> findByQuery(Long p2pId, ProductQuery query, PageModel page);

    int doUpdateSales(Long id, Integer quantity);

}
