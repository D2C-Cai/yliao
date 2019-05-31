package com.d2c.store.modules.order.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.store.common.api.annotation.Assert;
import com.d2c.store.common.api.annotation.Prevent;
import com.d2c.store.common.api.base.extension.BaseDelDO;
import com.d2c.store.common.api.emuns.AssertEnum;
import com.d2c.store.modules.order.model.support.ITradeItem;
import com.d2c.store.modules.product.model.ProductDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("O_ORDER_ITEM")
@ApiModel(description = "订单明细表")
public class OrderItemDO extends BaseDelDO implements ITradeItem {

    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "平台ID")
    private Long p2pId;
    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "会员ID")
    private Long memberId;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "会员账号")
    private String memberAccount;
    @Excel(name = "商品ID")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品ID")
    private Long productId;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品SKU的ID")
    private Long productSkuId;
    @Excel(name = "商品数量")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品数量")
    private Integer quantity;
    @Excel(name = "商品规格")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品规格")
    private String standard;
    @Excel(name = "商品名称")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品图片")
    private String productPic;
    @Excel(name = "SKU条码")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "SKU条码")
    private String skuSn;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "品牌ID")
    private Long brandId;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;
    @Excel(name = "成本价")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;
    @Excel(name = "结算比例")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "结算比例")
    private BigDecimal ratio;
    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "订单ID")
    private Long orderId;
    @Prevent
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "类型")
    private String type;
    @Excel(name = "状态", replace = {"签约中_WAIT_SIGN", "待发货_WAIT_DELIVER", "已发货_DELIVERED", "已收货_RECEIVED", "交易成功_SUCCESS", "交易关闭_CLOSED"})
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "状态")
    private String status;
    @Excel(name = "商品单价")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "商品单价")
    private BigDecimal productPrice;
    @Excel(name = "实时单价")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "实时单价")
    private BigDecimal realPrice;
    @Excel(name = "运费价格")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "运费价格")
    private BigDecimal freightAmount;
    @Excel(name = "实际支付")
    @Assert(type = AssertEnum.NOT_NULL)
    @ApiModelProperty(value = "实际支付")
    private BigDecimal payAmount;
    @Excel(name = "物流公司")
    @ApiModelProperty(value = "物流公司")
    private String logisticsCom;
    @Excel(name = "物流单号")
    @ApiModelProperty(value = "物流单号")
    private String logisticsNum;
    @Excel(name = "发货时间")
    @ApiModelProperty(value = "发货时间")
    private Date deliveredDate;
    @TableField(exist = false)
    @ApiModelProperty(value = "类型名")
    private String typeName;
    @TableField(exist = false)
    @ApiModelProperty(value = "状态名")
    private String statusName;
    @TableField(exist = false)
    @ApiModelProperty(value = "活动商品")
    private ProductDO product;
    @TableField(exist = false)
    @ApiModelProperty(value = "对应订单")
    private OrderDO order;

    public String getTypeName() {
        if (StrUtil.isBlank(type)) return "";
        return OrderDO.TypeEnum.valueOf(type).getDescription();
    }

    public String getStatusName() {
        if (StrUtil.isBlank(status)) return "";
        return StatusEnum.valueOf(status).getDescription();
    }

    public enum StatusEnum {
        //
        WAIT_SIGN("签约中"), WAIT_DELIVER("待发货"),
        DELIVERED("已发货"), RECEIVED("已收货"),
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
