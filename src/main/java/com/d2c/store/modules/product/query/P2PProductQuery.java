package com.d2c.store.modules.product.query;

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
public class P2PProductQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "平台ID")
    private Long p2pId;
    @Condition(condition = ConditionEnum.IN)
    @ApiModelProperty(value = "商品ID")
    private Long[] productId;

}
