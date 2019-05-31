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
public class SupplierQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.LIKE)
    @ApiModelProperty(value = "名称")
    private String name;

}

