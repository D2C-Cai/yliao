package com.d2c.store.modules.order.model.support;

import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Cai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderItemExportBean extends OrderItemDO {

    @ExcelEntity(name = "订单")
    private OrderDO order;

}
