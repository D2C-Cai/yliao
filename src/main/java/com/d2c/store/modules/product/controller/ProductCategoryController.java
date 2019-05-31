package com.d2c.store.modules.product.controller;

import com.d2c.store.common.api.base.BaseCtrl;
import com.d2c.store.modules.product.model.ProductCategoryDO;
import com.d2c.store.modules.product.query.ProductCategoryQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "商品品类管理")
@RestController
@RequestMapping("/back/product_category")
public class ProductCategoryController extends BaseCtrl<ProductCategoryDO, ProductCategoryQuery> {

}
