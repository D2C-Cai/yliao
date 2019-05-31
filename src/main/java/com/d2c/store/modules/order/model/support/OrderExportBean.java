package com.d2c.store.modules.order.model.support;

import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderExportBean extends OrderDO {

    @ExcelCollection(name = "订单明细", orderNum = "90")
    private List<OrderItemDO> orderItemList = new ArrayList<>();

}
