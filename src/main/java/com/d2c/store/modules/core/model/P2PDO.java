package com.d2c.store.modules.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.store.common.api.annotation.Assert;
import com.d2c.store.common.api.base.extension.BaseDelDO;
import com.d2c.store.common.api.emuns.AssertEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author Cai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("CORE_P2P")
@ApiModel(description = "P2P平台表")
public class P2PDO extends BaseDelDO {

    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "密钥")
    private String secret;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "名称")
    private String name;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "企业名称")
    private String companyName;
    @ApiModelProperty(value = "网站地址")
    private String website;
    @ApiModelProperty(value = "联系方式")
    private String mobile;
    @ApiModelProperty(value = "首页轮播")
    private String banner;
    @ApiModelProperty(value = "回调地址")
    private String notifyUrl;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "销售金额")
    private BigDecimal salesAmount;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "最低消费金额")
    private BigDecimal minAmount;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "允许偏差金额")
    private BigDecimal diffAmount;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "授权有效期-小时")
    private Integer oauthTime;
    //
    @ApiModelProperty(value = "法大大客户编号")
    private String customerId;
    @ApiModelProperty(value = "企业存证编号")
    private String evidenceNo;
    @ApiModelProperty(value = "信用代码")
    private String creditCode;
    @ApiModelProperty(value = "信用代码电子版")
    private String creditCodeFile;
    @ApiModelProperty(value = "委托书电子版")
    private String powerAttorneyFile;
    @ApiModelProperty(value = "法人名字")
    private String legalName;
    @ApiModelProperty(value = "法人身份证")
    private String identity;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "签章图片")
    private String signId;
    @ApiModelProperty(value = "法人客户编号")
    private String legalCustomerId;
    @ApiModelProperty(value = "合同模板的ID")
    private String templateId;

}
