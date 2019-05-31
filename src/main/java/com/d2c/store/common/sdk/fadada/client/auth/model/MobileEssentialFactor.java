package com.d2c.store.common.sdk.fadada.client.auth.model;

public class MobileEssentialFactor {

    /**
     * 交易号
     */
    private String transaction_id;
    /**
     * 实名认证提供方
     */
    private String verified_provider;
    /**
     * 实名结果
     */
    private String result;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getVerified_provider() {
        return verified_provider;
    }

    public void setVerified_provider(String verified_provider) {
        this.verified_provider = verified_provider;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
