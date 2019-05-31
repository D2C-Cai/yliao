package com.d2c.store.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.store.common.api.annotation.Assert;
import com.d2c.store.common.api.base.BaseDO;
import com.d2c.store.common.api.emuns.AssertEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("P_FREIGHT")
@ApiModel(description = "运费模板表")
public class FreightDO extends BaseDO {

    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "名称")
    private String name;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "公式")
    private String formula;

    public static BigDecimal calculate(Integer quantity, String formula) {
        try {
            String[] array = formula.split(":");
            if (quantity <= 1) {
                return new BigDecimal(array[0]);
            } else {
                return new BigDecimal(array[0]).add(new BigDecimal(quantity - 1).multiply(new BigDecimal(array[1])));
            }
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

}
