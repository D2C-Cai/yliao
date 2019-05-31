package com.d2c.store.api.support;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Cai
 */
@Data
@ApiModel(description = "授权请求POJO")
public class OauthBean implements Serializable {

    @ApiModelProperty(value = "平台ID")
    private String appId;
    @Excel(name = "授权账号")
    @ApiModelProperty(value = "授权账号")
    private String mobile;
    @Excel(name = "真实姓名")
    @ApiModelProperty(value = "真实姓名")
    private String name;
    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String identity;
    @Excel(name = "授权金额")
    @ApiModelProperty(value = "授权金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "请求签名")
    private String sign;

}
