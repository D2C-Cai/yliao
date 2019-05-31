package com.d2c.store.modules.order.model.support;

/**
 * @author Cai
 */
public interface ITradeItem {

    Long getP2pId();

    Long getMemberId();

    String getMemberAccount();

    Long getProductId();

    Long getProductSkuId();

    Integer getQuantity();

    String getStandard();

    String getProductName();

    String getProductPic();

}
