package com.d2c.store.modules.member.model;

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
import java.util.Date;

/**
 * @author Cai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("M_ACCOUNT")
@ApiModel(description = "账户表")
public class AccountDO extends BaseDelDO {

    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "平台ID")
    private Long p2pId;
    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "授权金额")
    private BigDecimal oauthAmount;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "过期时间")
    private Date deadline;

}
