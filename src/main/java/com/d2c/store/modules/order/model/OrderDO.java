package com.d2c.store.modules.order.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.store.common.api.annotation.Assert;
import com.d2c.store.common.api.annotation.Prevent;
import com.d2c.store.common.api.base.extension.BaseDelDO;
import com.d2c.store.common.api.emuns.AssertEnum;
import com.d2c.store.modules.member.model.support.IAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("O_ORDER")
@ApiModel(description = "订单表")
public class OrderDO extends BaseDelDO implements IAddress {

    @Prevent
    @Excel(name = "平台ID")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "平台ID")
    private Long p2pId;
    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @Excel(name = "会员账号")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "会员账号")
    private String memberAccount;
    @Excel(name = "省份")
    @ApiModelProperty(value = "省份")
    private String province;
    @Excel(name = "城市")
    @ApiModelProperty(value = "城市")
    private String city;
    @Excel(name = "区县")
    @ApiModelProperty(value = "区县")
    private String district;
    @Excel(name = "地址")
    @ApiModelProperty(value = "地址")
    private String address;
    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;
    @Excel(name = "手机")
    @ApiModelProperty(value = "手机")
    private String mobile;
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String mark;
    @Excel(name = "订单号")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "订单号")
    private String sn;
    @Excel(name = "类型", replace = {"普通_NORMAL"})
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "类型")
    private String type;
    @Excel(name = "状态", replace = {"待用户签约_WAIT_MEM_SIGN", "待P2P审核_WAIT_P2P_SIGN", "待客服审核_WAIT_CUS_SIGN", "待发货_WAIT_DELIVER", "已发货_DELIVERED", "交易成功_SUCCESS", "交易关闭_CLOSED"})
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "状态")
    private String status;
    @Excel(name = "商品总价")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品总价")
    private BigDecimal productAmount;
    @Excel(name = "运费价格")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "运费价格")
    private BigDecimal freightAmount;
    @Excel(name = "实际支付")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "实际支付")
    private BigDecimal payAmount;
    @Excel(name = "合同编号")
    @ApiModelProperty(value = "合同编号")
    private String contractId;
    @Excel(name = "用户签约时间")
    @ApiModelProperty(value = "用户签约时间")
    private Date memSignDate;
    @Excel(name = "P2P签约时间")
    @ApiModelProperty(value = "P2P签约时间")
    private Date p2pSignDate;
    @Excel(name = "电商签约时间")
    @ApiModelProperty(value = "电商签约时间")
    private Date cusSignDate;
    @Excel(name = "订单关闭时间")
    @ApiModelProperty(value = "订单关闭时间")
    private Date closeDate;
    @Excel(name = "推送P2P平台结果", replace = {"已推送_1", "未推送_0"})
    @ApiModelProperty(value = "推送P2P平台结果")
    private Integer sendP2p;
    @TableField(exist = false)
    @ApiModelProperty(value = "类型名")
    private String typeName;
    @TableField(exist = false)
    @ApiModelProperty(value = "状态名")
    private String statusName;
    @TableField(exist = false)
    @ApiModelProperty(value = "订单明细列表")
    private List<OrderItemDO> orderItemList = new ArrayList<>();

    public String getTypeName() {
        if (StrUtil.isBlank(type)) return "";
        return TypeEnum.valueOf(type).getDescription();
    }

    public String getStatusName() {
        if (StrUtil.isBlank(status)) return "";
        return StatusEnum.valueOf(status).getDescription();
    }

    public int getExpireMinute() {
        return TypeEnum.NORMAL.expireMinutes;
    }

    public enum TypeEnum {
        //
        NORMAL("普通", 1440);
        //
        private String description;
        private Integer expireMinutes;

        TypeEnum(String description, Integer expired) {
            this.description = description;
            this.expireMinutes = expired;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public enum StatusEnum {
        //
        WAIT_MEM_SIGN("待用户签约"), WAIT_P2P_SIGN("待P2P审核"), WAIT_CUS_SIGN("待客服审核"),
        WAIT_DELIVER("待发货"), DELIVERED("已发货"),
        SUCCESS("交易成功"), CLOSED("交易关闭");
        //
        private String description;

        StatusEnum(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
