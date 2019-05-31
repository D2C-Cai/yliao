package com.d2c.store.modules.order.query;

import com.d2c.store.common.api.annotation.Condition;
import com.d2c.store.common.api.base.BaseQuery;
import com.d2c.store.common.api.emuns.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderItemQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "会员账号")
    private String memberAccount;
    @Condition(condition = ConditionEnum.IN)
    @ApiModelProperty(value = "订单号")
    private String[] orderSn;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "类型")
    private String type;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "状态")
    private String status;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "SKU条码")
    private String skuSn;

}
