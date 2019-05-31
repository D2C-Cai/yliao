package com.d2c.store.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.store.common.api.annotation.Assert;
import com.d2c.store.common.api.annotation.Prevent;
import com.d2c.store.common.api.base.extension.BaseDelDO;
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
@TableName("P_PRODUCT_SKU")
@ApiModel(description = "商品SKU表")
public class ProductSkuDO extends BaseDelDO {

    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品ID")
    private Long productId;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "品牌ID")
    private Long brandId;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "条码")
    private String sn;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "规格")
    private String standard;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "销售价")
    private BigDecimal sellPrice;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "运费")
    private String freight;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "结算比例")
    private BigDecimal ratio;

}
