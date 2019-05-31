package com.d2c.store.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.store.common.api.annotation.Assert;
import com.d2c.store.common.api.base.BaseDO;
import com.d2c.store.common.api.emuns.AssertEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("P_BRAND")
@ApiModel(description = "品牌表")
public class BrandDO extends BaseDO {

    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;

}
