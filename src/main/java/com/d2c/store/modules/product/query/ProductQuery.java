package com.d2c.store.modules.product.query;

import com.d2c.store.common.api.annotation.Condition;
import com.d2c.store.common.api.base.BaseQuery;
import com.d2c.store.common.api.emuns.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "品牌ID")
    private Long brandId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "品类ID")
    private Long categoryId;
    @Condition(condition = ConditionEnum.IN, field = "category_id")
    @ApiModelProperty(value = "品类ID")
    private Long[] categoryIds;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "款号")
    private String sn;
    @Condition(condition = ConditionEnum.LIKE)
    @ApiModelProperty(value = "名称")
    private String name;
    @Condition(condition = ConditionEnum.GE, field = "ratio")
    @ApiModelProperty(value = "最低比例")
    private BigDecimal ratioL;
    @Condition(condition = ConditionEnum.LE, field = "ratio")
    @ApiModelProperty(value = "最高比例")
    private BigDecimal ratioR;
    @Condition(condition = ConditionEnum.GE, field = "price")
    @ApiModelProperty(value = "最低价格")
    private BigDecimal priceL;
    @Condition(condition = ConditionEnum.LE, field = "price")
    @ApiModelProperty(value = "最高价格")
    private BigDecimal priceR;
    @Condition(condition = ConditionEnum.GE, field = "market_price")
    @ApiModelProperty(value = "最低市场价")
    private BigDecimal marketPriceL;
    @Condition(condition = ConditionEnum.LE, field = "market_price")
    @ApiModelProperty(value = "最高市场价")
    private BigDecimal marketPriceR;

}
