package com.d2c.store.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.store.modules.order.model.OrderDO;

/**
 * @author BaiCai
 */
public interface OrderService extends IService<OrderDO> {

    OrderDO doCreate(OrderDO order);

    boolean doClose(OrderDO order);

    boolean doDelete(String orderSn);

    boolean doFilling(OrderDO order);

}
