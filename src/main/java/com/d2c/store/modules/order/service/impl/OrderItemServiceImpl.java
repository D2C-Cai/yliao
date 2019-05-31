package com.d2c.store.modules.order.service.impl;

import com.d2c.store.common.api.base.BaseService;
import com.d2c.store.modules.order.mapper.OrderItemMapper;
import com.d2c.store.modules.order.model.OrderItemDO;
import com.d2c.store.modules.order.service.OrderItemService;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class OrderItemServiceImpl extends BaseService<OrderItemMapper, OrderItemDO> implements OrderItemService {

}
