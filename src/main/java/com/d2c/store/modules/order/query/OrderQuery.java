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
public class OrderQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "平台ID")
    private Long p2pId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "订单号")
    private String sn;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "类型")
    private String type;
    @Condition(condition = ConditionEnum.IN)
    @ApiModelProperty(value = "状态")
    private String[] status;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "合同编号")
    private String contractId;

}
