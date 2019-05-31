package com.d2c.store.api.handler;

import com.d2c.store.modules.order.model.OrderDO;

/**
 * @author Cai
 */
public interface OrderHandler {

    void operator(OrderDO order, Object... conditions);

}
