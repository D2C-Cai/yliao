package com.d2c.store.modules.product.controller;

import com.d2c.store.common.api.base.BaseCtrl;
import com.d2c.store.modules.product.model.BrandDO;
import com.d2c.store.modules.product.query.BrandQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "品牌管理")
@RestController
@RequestMapping("/back/brand")
public class BrandController extends BaseCtrl<BrandDO, BrandQuery> {

}
