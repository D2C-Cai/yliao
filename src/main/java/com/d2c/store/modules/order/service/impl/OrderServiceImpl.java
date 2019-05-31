package com.d2c.store.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.store.common.api.base.BaseService;
import com.d2c.store.common.utils.ExecutorUtil;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.modules.member.model.AccountDO;
import com.d2c.store.modules.member.query.AccountQuery;
import com.d2c.store.modules.member.service.AccountService;
import com.d2c.store.modules.order.mapper.OrderMapper;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import com.d2c.store.modules.order.query.OrderItemQuery;
import com.d2c.store.modules.order.query.OrderQuery;
import com.d2c.store.modules.order.service.OrderItemService;
import com.d2c.store.modules.order.service.OrderService;
import com.d2c.store.modules.product.service.ProductService;
import com.d2c.store.modules.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author BaiCai
 */
@Service
public class OrderServiceImpl extends BaseService<OrderMapper, OrderDO> implements OrderService {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public boolean save(OrderDO entity) {
        boolean success = super.save(entity);
        return success;
    }

    @Override
    @Transactional
    public OrderDO doCreate(OrderDO order) {
        List<OrderItemDO> orderItemList = order.getOrderItemList();
        if (orderItemList.size() == 0) {
            throw new ApiException("订单明细不能为空");
        }
        // 创建订单
        this.save(order);
        for (OrderItemDO orderItem : orderItemList) {
            // 扣减库存
            int success = productSkuService.doDeductStock(orderItem.getProductSkuId(), orderItem.getProductId(), orderItem.getQuantity());
            if (success == 0) {
                throw new ApiException(orderItem.getProductSkuId() + "的SKU库存不足");
            }
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getSn());
            orderItem.setType(order.getType());
            orderItemService.save(orderItem);
        }
        // 账户清零
        AccountQuery query = new AccountQuery();
        query.setMemberId(order.getMemberId());
        query.setP2pId(order.getP2pId());
        AccountDO account = new AccountDO();
        account.setOauthAmount(BigDecimal.ZERO);
        accountService.update(account, QueryUtil.buildWrapper(query));
        redisTemplate.delete("MEMBER::session:" + order.getMemberAccount());
        ExecutorUtil.fixedPool.submit(() -> {
                    for (OrderItemDO orderItem : orderItemList) {
                        // 商品销量统计
                        productService.doUpdateSales(orderItem.getProductId(), orderItem.getQuantity());
                    }
                }
        );
        return order;
    }

    @Override
    @Transactional
    public boolean doClose(OrderDO order) {
        boolean success = true;
        // 关闭订单
        OrderDO o = new OrderDO();
        o.setId(order.getId());
        o.setStatus(OrderDO.StatusEnum.CLOSED.name());
        o.setCloseDate(new Date());
        success &= this.updateById(o);
        // 关闭订单明细
        OrderItemDO oi = new OrderItemDO();
        oi.setStatus(OrderItemDO.StatusEnum.CLOSED.name());
        OrderItemQuery oiq = new OrderItemQuery();
        oiq.setOrderSn(new String[]{order.getSn()});
        success &= orderItemService.update(oi, QueryUtil.buildWrapper(oiq));
        // 账户重置
        AccountQuery query = new AccountQuery();
        query.setMemberId(order.getMemberId());
        query.setP2pId(order.getP2pId());
        AccountDO account = new AccountDO();
        account.setDeadline(new Date());
        accountService.update(account, QueryUtil.buildWrapper(query));
        OrderItemQuery itemQuery = new OrderItemQuery();
        itemQuery.setOrderSn(new String[]{order.getSn()});
        List<OrderItemDO> orderItemList = orderItemService.list(QueryUtil.buildWrapper(itemQuery));
        for (OrderItemDO orderItem : orderItemList) {
            // 返还库存
            productSkuService.doReturnStock(orderItem.getProductSkuId(), orderItem.getProductId(), orderItem.getQuantity());
        }
        ExecutorUtil.fixedPool.submit(() -> {
                    for (OrderItemDO orderItem : orderItemList) {
                        // 商品销量统计
                        productService.doUpdateSales(orderItem.getProductId(), orderItem.getQuantity() * -1);
                    }
                }
        );
        return success;
    }

    @Override
    @Transactional
    public boolean doDelete(String orderSn) {
        boolean success = true;
        OrderQuery oq = new OrderQuery();
        oq.setSn(orderSn);
        success &= this.remove(QueryUtil.buildWrapper(oq));
        OrderItemQuery oiq = new OrderItemQuery();
        oiq.setOrderSn(new String[]{orderSn});
        success &= orderItemService.remove(QueryUtil.buildWrapper(oiq));
        return success;
    }

    @Override
    @Transactional
    public boolean doFilling(OrderDO order) {
        boolean success = true;
        // 合同归档，订单转为待发货
        OrderQuery oq = new OrderQuery();
        oq.setSn(order.getSn());
        OrderDO orderDO = new OrderDO();
        orderDO.setStatus(OrderDO.StatusEnum.WAIT_DELIVER.name());
        orderDO.setCusSignDate(new Date());
        success &= this.update(orderDO, QueryUtil.buildWrapper(oq));
        OrderItemDO oi = new OrderItemDO();
        oi.setStatus(OrderItemDO.StatusEnum.WAIT_DELIVER.name());
        OrderItemQuery oiq = new OrderItemQuery();
        oiq.setOrderSn(new String[]{order.getSn()});
        success &= orderItemService.update(oi, QueryUtil.buildWrapper(oiq));
        // 账户重置
        AccountQuery query = new AccountQuery();
        query.setMemberId(order.getMemberId());
        query.setP2pId(order.getP2pId());
        AccountDO account = new AccountDO();
        account.setDeadline(new Date());
        accountService.update(account, QueryUtil.buildWrapper(query));
        return success;
    }

}
