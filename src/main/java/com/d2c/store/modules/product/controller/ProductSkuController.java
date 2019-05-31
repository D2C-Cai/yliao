package com.d2c.store.modules.product.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.api.base.BaseCtrl;
import com.d2c.store.modules.product.model.ProductSkuDO;
import com.d2c.store.modules.product.query.ProductSkuQuery;
import com.d2c.store.modules.product.service.ProductService;
import com.d2c.store.modules.product.service.ProductSkuService;
import com.d2c.store.modules.security.model.UserDO;
import com.d2c.store.modules.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "商品SKU管理")
@RestController
@RequestMapping("/back/product_sku")
public class ProductSkuController extends BaseCtrl<ProductSkuDO, ProductSkuQuery> {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    @ApiOperation(value = "更改库存")
    @RequestMapping(value = "/update/stock", method = RequestMethod.POST)
    public R updateStock(Long id, Integer stock) {
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        ProductSkuDO productSku = productSkuService.getById(id);
        Asserts.eq(user.getSupplierId(), productSku.getSupplierId(), "您不是该商品供应商");
        Integer distance = stock - productSku.getStock();
        ProductSkuDO entity = new ProductSkuDO();
        entity.setId(id);
        entity.setStock(stock);
        productSkuService.updateById(entity);
        productService.doReturnStock(productSku.getProductId(), distance);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
