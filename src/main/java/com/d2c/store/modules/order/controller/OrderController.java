package com.d2c.store.modules.order.controller;

import cn.afterturn.easypoi.entity.vo.BigExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.PageModel;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.api.base.extension.BaseExcelCtrl;
import com.d2c.store.common.api.constant.PrefixConstant;
import com.d2c.store.common.sdk.fadada.FadadaClient;
import com.d2c.store.common.sdk.p2p.KSP2PClient;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.core.service.P2PService;
import com.d2c.store.modules.member.model.AccountDO;
import com.d2c.store.modules.member.query.AccountQuery;
import com.d2c.store.modules.member.service.AccountService;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import com.d2c.store.modules.order.model.support.OrderExportBean;
import com.d2c.store.modules.order.query.OrderItemQuery;
import com.d2c.store.modules.order.query.OrderQuery;
import com.d2c.store.modules.order.service.OrderItemService;
import com.d2c.store.modules.order.service.OrderService;
import com.d2c.store.modules.security.model.UserDO;
import com.d2c.store.modules.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BaiCai
 */
@Api(description = "订单管理")
@RestController
@RequestMapping("/back/order")
public class OrderController extends BaseExcelCtrl<OrderDO, OrderQuery> {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private P2PService p2PService;
    @Autowired
    private FadadaClient fadadaClient;
    @Autowired
    private KSP2PClient kSP2PClient;

    @ApiOperation(value = "分页导出数据")
    @RequestMapping(value = "/excel/page", method = RequestMethod.GET)
    public R excelPage(OrderQuery query, ModelMap map) throws Exception {
        ExportParams params = new ExportParams("订单表导出", "sheet1", ExcelType.XSSF);
        map.put(BigExcelConstants.CLASS, OrderExportBean.class);
        map.put(BigExcelConstants.PARAMS, params);
        map.put(BigExcelConstants.FILE_NAME, "order_export");
        map.put(BigExcelConstants.DATA_PARAMS, query);
        map.put(BigExcelConstants.DATA_INTER, excelExportServer);
        return renderExcel(map);
    }

    @Override
    public List<Object> selectListForExcelExport(Object o, int i) {
        OrderQuery query = (OrderQuery) o;
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        query.setP2pId(user.getP2pId());
        Page page = new Page(i, PageModel.MAX_SIZE, false);
        Page<OrderDO> pager = (Page<OrderDO>) orderService.page(page, QueryUtil.buildWrapper(query, false));
        List<OrderExportBean> beanList = new ArrayList<>();
        Map<String, OrderExportBean> beanMap = new ConcurrentHashMap<>();
        List<String> orderSns = new ArrayList<>();
        for (OrderDO order : pager.getRecords()) {
            orderSns.add(order.getSn());
            OrderExportBean oBean = new OrderExportBean();
            BeanUtils.copyProperties(order, oBean);
            beanList.add(oBean);
            beanMap.put(oBean.getSn(), oBean);
        }
        if (orderSns.size() == 0) return new ArrayList<>();
        OrderItemQuery itemQuery = new OrderItemQuery();
        itemQuery.setOrderSn(orderSns.toArray(new String[0]));
        List<OrderItemDO> orderItemList = orderItemService.list(QueryUtil.buildWrapper(itemQuery));
        for (OrderItemDO orderItem : orderItemList) {
            if (beanMap.get(orderItem.getOrderSn()) != null) {
                beanMap.get(orderItem.getOrderSn()).getOrderItemList().add(orderItem);
            }
        }
        List<Object> result = new ArrayList<>();
        result.addAll(beanList);
        return result;
    }

    @ApiOperation(value = "P2P查询数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R<Page<OrderDO>> list(PageModel page, OrderQuery query) {
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        query.setP2pId(user.getP2pId());
        Page<OrderDO> pager = (Page<OrderDO>) service.page(page, QueryUtil.buildWrapper(query));
        List<String> orderSns = new ArrayList<>();
        Map<String, OrderDO> orderMap = new ConcurrentHashMap<>();
        for (OrderDO order : pager.getRecords()) {
            orderSns.add(order.getSn());
            orderMap.put(order.getSn(), order);
        }
        if (orderSns.size() == 0) return Response.restResult(pager, ResultCode.SUCCESS);
        OrderItemQuery itemQuery = new OrderItemQuery();
        itemQuery.setOrderSn(orderSns.toArray(new String[0]));
        List<OrderItemDO> orderItemList = orderItemService.list(QueryUtil.buildWrapper(itemQuery));
        for (OrderItemDO orderItem : orderItemList) {
            if (orderMap.get(orderItem.getOrderSn()) != null) {
                orderMap.get(orderItem.getOrderSn()).getOrderItemList().add(orderItem);
            }
        }
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "P2P签约")
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public R p2pSign(Long id) {
        OrderDO orderDO = service.getById(id);
        Asserts.eq(orderDO.getStatus(), OrderDO.StatusEnum.WAIT_P2P_SIGN.name(), "订单状态不符");
        // P2P自动签章
        P2PDO p2PDO = p2PService.getById(orderDO.getP2pId());
        fadadaClient.extSignAuto(p2PDO.getCustomerId(), PrefixConstant.FDD_ORDER_C_TRANSATION_PREFIX + id, orderDO.getContractId(), p2PDO.getName() + "债权合同");
        // 修改订单状态为客服待审核
        OrderDO order = new OrderDO();
        order.setId(id);
        order.setStatus(OrderDO.StatusEnum.WAIT_CUS_SIGN.name());
        order.setP2pSignDate(new Date());
        service.updateById(order);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "客服审核")
    @RequestMapping(value = "/filling", method = RequestMethod.POST)
    public R filling(Long id) {
        OrderDO orderDO = service.getById(id);
        Asserts.eq(orderDO.getStatus(), OrderDO.StatusEnum.WAIT_CUS_SIGN.name(), "订单状态不符");
        // 合同归档
        fadadaClient.contractFilling("C_" + orderDO.getSn());
        // 修改订单状态为待发货
        orderService.doFilling(orderDO);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "合同查看")
    @RequestMapping(value = "/view/contract", method = RequestMethod.POST)
    public R viewContract(String orderSn) {
        OrderQuery oq = new OrderQuery();
        oq.setSn(orderSn);
        OrderDO orderDO = service.getOne(QueryUtil.buildWrapper(oq));
        String viewUrl = fadadaClient.viewContract(orderDO.getContractId());
        return Response.restResult(viewUrl, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "通知p2p平台")
    @RequestMapping(value = "/notice/p2p", method = RequestMethod.POST)
    private R noticeP2p(Long orderId) {
        OrderDO orderDO = service.getById(orderId);
        return noticeP2p(orderDO);
    }

    /**
     * 通知p2p平台
     */
    private R noticeP2p(final OrderDO orderDO) {
        //OrderDO orderDO = service.getById(orderId);
        AccountQuery accountQuery = new AccountQuery();
        accountQuery.setMemberId(orderDO.getMemberId());
        accountQuery.setP2pId(orderDO.getP2pId());
        AccountDO accountDO = accountService.getOne(QueryUtil.buildWrapper(accountQuery));
        JSONObject obj = new JSONObject();
        obj.put("orderNo", orderDO.getSn());
        obj.put("orderTime", DateUtil.format(orderDO.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        obj.put("orderAmount", orderDO.getPayAmount().floatValue());
        obj.put("limitAmount", accountDO.getOauthAmount().floatValue());
        obj.put("freightAmount", orderDO.getFreightAmount().floatValue());
        obj.put("realName", orderDO.getName());
        obj.put("phone", orderDO.getMobile());
        obj.put("address", orderDO.getProvince() + orderDO.getCity() + orderDO.getDistrict() + orderDO.getAddress());
        obj.put("totalPrice", orderDO.getProductAmount().floatValue());
        OrderItemQuery oiq = new OrderItemQuery();
        oiq.setOrderSn(new String[]{orderDO.getSn()});
        List<OrderItemDO> orderItems = orderItemService.list(QueryUtil.buildWrapper(oiq));
        JSONArray array = new JSONArray();
        for (OrderItemDO orderItemDO : orderItems) {
            JSONObject item = new JSONObject();
            item.put("goodsName", orderItemDO.getProductName());
            item.put("skuNo", orderItemDO.getSkuSn());
            item.put("quantity", orderItemDO.getQuantity());
            item.put("price", orderItemDO.getProductPrice().floatValue());
            array.add(item);
        }
        obj.put("orderDetail", array);
        String sign = kSP2PClient.getSignToken(obj);
        obj.put("sign", sign);
        String response = kSP2PClient.sendPost(KSP2PClient.NOTICE_URL, obj);
        JSONObject result = JSONObject.parseObject(response);
        Asserts.eq(result.getString("code"), "100", result.getString("message"));
        OrderDO order = new OrderDO();
        order.setSendP2p(1);
        order.setId(orderDO.getId());
        service.updateById(order);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
