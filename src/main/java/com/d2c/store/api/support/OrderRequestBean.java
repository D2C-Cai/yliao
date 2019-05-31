package com.d2c.store.api.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cai
 */
@Data
@ApiModel(description = "订单请求POJO")
public class OrderRequestBean implements Serializable {

    @ApiModelProperty(value = "购物车ID")
    private List<Long> cartIds;
    @ApiModelProperty(value = "SKU的ID")
    private Long skuId;
    @ApiModelProperty(value = "SKU的数量")
    private Integer quantity;
    @ApiModelProperty(value = "收货地址ID")
    private Long addressId;

}
