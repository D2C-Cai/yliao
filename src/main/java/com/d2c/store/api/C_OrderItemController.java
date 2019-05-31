package com.d2c.store.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.store.api.base.BaseController;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import com.d2c.store.modules.order.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "订单明细业务")
@RestController
@RequestMapping("/api/order_item")
public class C_OrderItemController extends BaseController {

    @Autowired
    private OrderItemService orderItemService;

    @ApiOperation(value = "订单明细签收")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public R receiveItem(Long orderItemId) {
        OrderItemDO orderItem = orderItemService.getById(orderItemId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, orderItem);
        Asserts.eq(orderItem.getStatus(), OrderItemDO.StatusEnum.DELIVERED.name(), "订单明细状态异常");
        MemberDO member = loginMemberHolder.getLoginMember();
        Asserts.eq(orderItem.getMemberId(), member.getId(), "订单不属于本人");
        OrderItemDO entity = new OrderItemDO();
        entity.setId(orderItemId);
        entity.setStatus(OrderItemDO.StatusEnum.RECEIVED.name());
        orderItemService.updateById(entity);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
