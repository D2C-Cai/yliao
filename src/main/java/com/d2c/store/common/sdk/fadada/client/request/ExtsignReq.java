package com.d2c.store.common.sdk.fadada.client.request;

public class ExtsignReq {

    /**
     * 客户编号
     */
    private String customer_id;
    /**
     * 合同id
     */
    private String contract_id;
    /**
     * 交易号
     */
    private String transaction_id;
    /**
     * 合同标题
     */
    private String doc_title = "";
    /**
     * 关键字
     */
    private String sign_keyword;
    /**
     * 关键字策略
     */
    private String keyword_strategy = "0";
    /**
     * 客户角色 自动签特有参数 必传
     * 1-接入平台；
     * 2-仅适用互金行业担保公司或担保人；
     * 3-接入平台客户（互金行业指投资人）；
     * 4-仅适用互金行业借款企业或者借款人
     */
    private String client_role;
    /**
     * 定位类型 0-关键字（默认） 1-坐标
     */
    private String position_type = "0";
    /**
     * 类型：String
     * 格式：JsonArray<SearchLocation>
     * 当position_type为1时，此参数必填
     */
    private String signature_positions;
    /**
     * 异步 同步回调地址
     */
    private String notify_url = "";
    private String return_url = "";

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDoc_title() {
        return doc_title;
    }

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public String getSign_keyword() {
        return sign_keyword;
    }

    public void setSign_keyword(String sign_keyword) {
        this.sign_keyword = sign_keyword;
    }

    public String getKeyword_strategy() {
        return keyword_strategy;
    }

    public void setKeyword_strategy(String keyword_strategy) {
        this.keyword_strategy = keyword_strategy;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getClient_role() {
        return client_role;
    }

    public void setClient_role(String client_role) {
        this.client_role = client_role;
    }

    public String getPosition_type() {
        return position_type;
    }

    public void setPosition_type(String position_type) {
        this.position_type = position_type;
    }

    public String getSignature_positions() {
        return signature_positions;
    }

    public void setSignature_positions(String signature_positions) {
        this.signature_positions = signature_positions;
    }

}
