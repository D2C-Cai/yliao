package com.d2c.store.api.callback;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.store.api.base.BaseController;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.sdk.p2p.KSP2PClient;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.core.service.P2PService;
import com.d2c.store.modules.member.model.AccountDO;
import com.d2c.store.modules.member.query.AccountQuery;
import com.d2c.store.modules.member.service.AccountService;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import com.d2c.store.modules.order.query.OrderItemQuery;
import com.d2c.store.modules.order.query.OrderQuery;
import com.d2c.store.modules.order.service.OrderItemService;
import com.d2c.store.modules.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Cai
 */
@Api(description = "第三方回调业务")
@RestController
@RequestMapping("/api/callback")
public class C_CallbackController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private KSP2PClient kSP2PClient;
    @Autowired
    private P2PService p2PService;

    @ApiOperation(value = "法大大回调接口")
    @RequestMapping(value = "/fadada", method = RequestMethod.POST)
    public R fadada(String transaction_id, String contract_id, String result_code, String result_desc, String timestamp, String msg_digest) {
        Asserts.eq(result_code, "3000", "签约失败，合同编号：" + contract_id);
        OrderQuery oq = new OrderQuery();
        oq.setContractId(contract_id);
        OrderDO orderDO = orderService.getOne(QueryUtil.buildWrapper(oq));
        if (OrderDO.StatusEnum.WAIT_MEM_SIGN.name().equals(orderDO.getStatus())) {
            OrderDO order = new OrderDO();
            order.setId(orderDO.getId());
            order.setStatus(OrderDO.StatusEnum.WAIT_P2P_SIGN.name());
            order.setMemSignDate(new Date());
            orderService.updateById(order);
        }
        noticeP2p(orderDO);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    /**
     * 异步通知p2p平台
     *
     * @param orderDO
     */
    private void noticeP2p(final OrderDO orderDO) {
        ThreadUtil.excAsync(new Runnable() {
            @Override
            public void run() {
                //除了订单外 还需要授权金额，订单明细，p2p secret信息
                P2PDO p2PDO = p2PService.getById(orderDO.getP2pId());
                if (StringUtils.isBlank(p2PDO.getNotifyUrl())) {
                    return;
                }
                AccountQuery accountQuery = new AccountQuery();
                accountQuery.setMemberId(orderDO.getMemberId());
                accountQuery.setP2pId(orderDO.getP2pId());
                AccountDO accountDO = accountService.getOne(QueryUtil.buildWrapper(accountQuery));
                OrderItemQuery oiq = new OrderItemQuery();
                oiq.setOrderSn(new String[]{orderDO.getSn()});
                List<OrderItemDO> orderItems = orderItemService.list(QueryUtil.buildWrapper(oiq));
                //推送的Map
                Map obj = new HashMap();
                obj.put("orderNo", orderDO.getSn());
                obj.put("orderTime", DateUtil.format(orderDO.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
                obj.put("orderAmount", orderDO.getPayAmount().floatValue());
                obj.put("limitAmount", accountDO.getOauthAmount().floatValue());
                obj.put("freightAmount", orderDO.getFreightAmount().floatValue());
                obj.put("realName", orderDO.getName());
                obj.put("phone", orderDO.getMobile());
                obj.put("address", orderDO.getProvince() + orderDO.getCity() + orderDO.getDistrict() + orderDO.getAddress());
                obj.put("totalPrice", orderDO.getProductAmount().floatValue());
                JSONArray array = new JSONArray();
                for (OrderItemDO orderItemDO : orderItems) {
                    JSONObject item = new JSONObject();
                    item.put("goodsName", orderItemDO.getProductName());
                    item.put("skuNo", orderItemDO.getSkuSn());
                    item.put("quantity", orderItemDO.getQuantity());
                    item.put("price", orderItemDO.getProductPrice().floatValue());
                    array.add(item);
                }
                obj.put("orderDetail", array.toJSONString());
                //生成签名和发送消息
                OrderDO order = new OrderDO();
                if (!"KS".equals(p2PDO)) {
                    String signContent = getSign(obj);
                    String signature = DigestUtil.md5Hex(signContent + p2PDO.getSecret());
                    obj.put("sign", signature);
                    String response = HttpUtil.post(p2PDO.getNotifyUrl(), obj);
                    if ("100".equals(JSON.parseObject(response).getString("code"))) {
                        order.setSendP2p(1);
                    } else {
                        order.setSendP2p(-1);
                    }
                } else {
                    //KS先特殊处理
                    order.setSendP2p(kSP2PClient.send(obj));
                }
                order.setId(orderDO.getId());
                orderService.updateById(order);
            }
        }, false);
    }

    /**
     * 签名方法
     *
     * @param params
     * @return
     */
    private String getSign(Map<String, String> params) {
        params.remove("sign");
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); ++i) {
            String key = String.valueOf(keys.get(i));
            String value = String.valueOf(params.get(key));
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        return content.toString();
    }

}
