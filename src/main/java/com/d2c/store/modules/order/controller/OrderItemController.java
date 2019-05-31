package com.d2c.store.modules.order.controller;

import cn.afterturn.easypoi.entity.vo.BigExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.PageModel;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.api.base.extension.BaseExcelCtrl;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.common.utils.RequestUtil;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import com.d2c.store.modules.order.model.support.DeliverImportBean;
import com.d2c.store.modules.order.model.support.OrderItemExportBean;
import com.d2c.store.modules.order.query.OrderItemQuery;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BaiCai
 */
@Api(description = "订单明细管理")
@RestController
@RequestMapping("/back/order_item")
public class OrderItemController extends BaseExcelCtrl<OrderItemDO, OrderItemQuery> {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @ApiOperation(value = "分页导出数据")
    @RequestMapping(value = "/excel/page", method = RequestMethod.GET)
    public R excelPage(OrderItemQuery query, ModelMap map) throws Exception {
        ExportParams params = new ExportParams("订单明细表导出", "sheet1", ExcelType.XSSF);
        map.put(BigExcelConstants.CLASS, OrderItemExportBean.class);
        map.put(BigExcelConstants.PARAMS, params);
        map.put(BigExcelConstants.FILE_NAME, "order_item_export");
        map.put(BigExcelConstants.DATA_PARAMS, query);
        map.put(BigExcelConstants.DATA_INTER, excelExportServer);
        return renderExcel(map);
    }

    @Override
    public List<Object> selectListForExcelExport(Object o, int i) {
        OrderItemQuery query = (OrderItemQuery) o;
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        query.setSupplierId(user.getSupplierId());
        Page page = new Page(i, PageModel.MAX_SIZE, false);
        Page<OrderItemDO> pager = (Page<OrderItemDO>) service.page(page, QueryUtil.buildWrapper(query));
        List<OrderItemExportBean> beanList = new ArrayList<>();
        Set<Long> orderIds = new HashSet<>();
        for (OrderItemDO oi : pager.getRecords()) {
            orderIds.add(oi.getOrderId());
            OrderItemExportBean oiBean = new OrderItemExportBean();
            BeanUtils.copyProperties(oi, oiBean);
            beanList.add(oiBean);
        }
        if (orderIds.size() == 0) return new ArrayList<>();
        List<OrderDO> orders = (List<OrderDO>) orderService.listByIds(orderIds);
        Map<Long, OrderDO> orderMap = new ConcurrentHashMap<>();
        for (OrderDO order : orders) {
            orderMap.put(order.getId(), order);
        }
        for (OrderItemExportBean oiBean : beanList) {
            if (orderMap.get(oiBean.getOrderId()) != null) {
                OrderDO order = orderMap.get(oiBean.getOrderId());
                oiBean.setOrder(order);
            }
        }
        List<Object> result = new ArrayList<>();
        result.addAll(beanList);
        return result;
    }

    @ApiOperation(value = "供应商查询数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R<Page<OrderItemDO>> list(PageModel page, OrderItemQuery query) {
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        query.setSupplierId(user.getSupplierId());
        Page<OrderItemDO> pager = (Page<OrderItemDO>) service.page(page, QueryUtil.buildWrapper(query));
        Set<Long> orderIds = new HashSet<>();
        for (OrderItemDO oi : pager.getRecords()) {
            orderIds.add(oi.getOrderId());
        }
        if (orderIds.size() == 0) return Response.restResult(pager, ResultCode.SUCCESS);
        List<OrderDO> orders = (List<OrderDO>) orderService.listByIds(orderIds);
        Map<Long, OrderDO> orderMap = new ConcurrentHashMap<>();
        for (OrderDO order : orders) {
            orderMap.put(order.getId(), order);
        }
        for (OrderItemDO oi : pager.getRecords()) {
            if (orderMap.get(oi.getOrderId()) != null) {
                OrderDO order = orderMap.get(oi.getOrderId());
                oi.setOrder(order);
            }
        }
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "订单明细发货")
    @RequestMapping(value = "/deliver", method = RequestMethod.POST)
    public R deliverItem(Long orderItemId, String logisticsCom, String logisticsNum) {
        OrderItemDO orderItem = orderItemService.getById(orderItemId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, orderItem);
        Asserts.eq(orderItem.getStatus(), OrderItemDO.StatusEnum.WAIT_DELIVER.name(), "订单明细状态异常");
        OrderItemDO entity = new OrderItemDO();
        entity.setId(orderItemId);
        entity.setLogisticsCom(logisticsCom);
        entity.setLogisticsNum(logisticsNum);
        entity.setStatus(OrderItemDO.StatusEnum.DELIVERED.name());
        entity.setDeliveredDate(new Date());
        orderItemService.updateById(entity);
        OrderDO entity2 = new OrderDO();
        entity2.setId(orderItem.getOrderId());
        entity2.setStatus(OrderDO.StatusEnum.DELIVERED.name());
        orderService.updateById(entity2);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "导入明细发货")
    @RequestMapping(value = "/import/deliver", method = RequestMethod.POST)
    public R importMember(HttpServletRequest request) throws IOException {
        File file = RequestUtil.getRequestFile(request);
        List<DeliverImportBean> list = ExcelImportUtil.importExcel(file, DeliverImportBean.class, new ImportParams());
        for (DeliverImportBean deliverBean : list) {
            OrderItemQuery query = new OrderItemQuery();
            query.setOrderSn(new String[]{deliverBean.getOrderSn()});
            query.setSkuSn(deliverBean.getSkuSn());
            OrderItemDO orderItem = orderItemService.getOne(QueryUtil.buildWrapper(query));
            if (orderItem != null && orderItem.getStatus().equals(OrderItemDO.StatusEnum.WAIT_DELIVER.name())) {
                OrderItemDO entity = new OrderItemDO();
                entity.setId(orderItem.getId());
                entity.setLogisticsCom(deliverBean.getLogisticsCom());
                entity.setLogisticsNum(deliverBean.getLogisticsNum());
                entity.setStatus(OrderItemDO.StatusEnum.DELIVERED.name());
                entity.setDeliveredDate(new Date());
                orderItemService.updateById(entity);
                OrderDO entity2 = new OrderDO();
                entity2.setId(orderItem.getOrderId());
                entity2.setStatus(OrderDO.StatusEnum.DELIVERED.name());
                orderService.updateById(entity2);
            }
        }
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
