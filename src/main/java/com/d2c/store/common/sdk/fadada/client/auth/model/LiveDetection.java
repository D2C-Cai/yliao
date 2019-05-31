package com.d2c.store.common.sdk.fadada.client.auth.model;

/**
 * 活体检测信息json数据
 */
public class LiveDetection {

    /**
     * 活体检测交易号
     */
    private String transaction_id;
    /**
     * 活体检测结果
     */
    private String result;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
