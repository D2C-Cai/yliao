package com.d2c.store.modules.product.controller;

import com.d2c.store.common.api.base.BaseCtrl;
import com.d2c.store.modules.product.model.SupplierDO;
import com.d2c.store.modules.product.query.SupplierQuery;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "供应商管理")
@RestController
@RequestMapping("/back/supplier")
public class SupplierController extends BaseCtrl<SupplierDO, SupplierQuery> {

}
