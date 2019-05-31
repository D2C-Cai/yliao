package com.d2c.store.modules.order.model.support;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Cai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeliverImportBean implements Serializable {

    @Excel(name = "订单号")
    private String orderSn;
    @Excel(name = "SKU条码")
    private String skuSn;
    @Excel(name = "物流公司")
    private String logisticsCom;
    @Excel(name = "物流单号")
    private String logisticsNum;

}
