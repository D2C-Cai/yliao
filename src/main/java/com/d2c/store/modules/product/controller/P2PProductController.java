package com.d2c.store.modules.product.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.api.base.BaseCtrl;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.modules.product.model.P2PProductDO;
import com.d2c.store.modules.product.query.P2PProductQuery;
import com.d2c.store.modules.product.service.P2PProductService;
import com.d2c.store.modules.security.model.UserDO;
import com.d2c.store.modules.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiCai
 */
@Api(description = "平台商品关系")
@RestController
@RequestMapping("/back/p2p_product")
public class P2PProductController extends BaseCtrl<P2PProductDO, P2PProductQuery> {

    @Autowired
    private P2PProductService p2PProductService;
    @Autowired
    private UserService userService;

    @Override
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<P2PProductDO> insert(@RequestBody P2PProductDO entity) {
        P2PProductQuery query = new P2PProductQuery();
        query.setP2pId(entity.getP2pId());
        query.setProductId(new Long[]{entity.getProductId()});
        p2PProductService.remove(QueryUtil.buildWrapper(query));
        return super.insert(entity);
    }

    @ApiOperation(value = "批量新增数据")
    @RequestMapping(value = "/batch/insert", method = RequestMethod.POST)
    public R batchInsert(Long[] productIds) {
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        for (Long productId : productIds) {
            P2PProductDO entity = new P2PProductDO();
            entity.setP2pId(user.getP2pId());
            entity.setProductId(productId);
            insert(entity);
        }
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/batch/remove", method = RequestMethod.POST)
    public R batchRemove(Long[] productIds) {
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        for (Long productId : productIds) {
            P2PProductQuery query = new P2PProductQuery();
            query.setP2pId(user.getP2pId());
            query.setProductId(new Long[]{productId});
            p2PProductService.remove(QueryUtil.buildWrapper(query));
        }
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
