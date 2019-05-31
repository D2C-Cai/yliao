package com.d2c.store.common.sdk.fadada.client.auth.model;

import java.io.File;

public class PersonDepositReq {

    /**
     * 客户编号
     */
    private String customer_id;
    /**
     * 存证名称 len<=100
     */
    private String preservation_name;
    /**
     * 存证描述 len<=100
     */
    private String preservation_desc;
    /**
     * 存证数据提供方 len<=30
     */
    private String preservation_data_provider;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件类型 默认是1：身份证
     */
    private String document_type;
    /**
     * 证件号
     */
    private String idcard;
    /**
     * 证件照正面
     */
    private File idcard_positive_file;
    /**
     * 证件照反面
     */
    private File idcard_negative_file;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 实名时间
     */
    private String verified_time;
    /**
     * 实名存证类型
     * 1:公安部二要素(姓名+身份证);
     * 2:手机三要素(姓名+身份证+手机号);
     * 3:银行卡三要素(姓名+身份证+银行卡);
     * 4:四要素(姓名+身份证+手机号+银行卡)
     */
    private String verified_type;
    /**
     * verified_type =1 公安部二要素 verified_type =1必填
     */
    private PublicSecurityEssentialFactor public_security_essential_factor;
    /**
     * verified_type =2 手机三要素 verified_type =2必填
     */
    private MobileEssentialFactor mobile_essential_factor;
    /**
     * verified_type =3 银行卡三要素 verified_type =3必填
     */
    private BankEssentialFactor bank_essential_factor;
    /**
     * verified_type =4 四要素 verified_type =4必填
     */
    private MobileAndBankEssentialFactor mobile_and_bank_essential_factor;
    /**
     * 活体检测信息json数据
     */
    private LiveDetection live_detection;
    /**
     * 活体检测图
     */
    private File live_detection_file;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPreservation_name() {
        return preservation_name;
    }

    public void setPreservation_name(String preservation_name) {
        this.preservation_name = preservation_name;
    }

    public String getPreservation_desc() {
        return preservation_desc;
    }

    public void setPreservation_desc(String preservation_desc) {
        this.preservation_desc = preservation_desc;
    }

    public String getPreservation_data_provider() {
        return preservation_data_provider;
    }

    public void setPreservation_data_provider(String preservation_data_provider) {
        this.preservation_data_provider = preservation_data_provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public File getIdcard_positive_file() {
        return idcard_positive_file;
    }

    public void setIdcard_positive_file(File idcard_positive_file) {
        this.idcard_positive_file = idcard_positive_file;
    }

    public File getIdcard_negative_file() {
        return idcard_negative_file;
    }

    public void setIdcard_negative_file(File idcard_negative_file) {
        this.idcard_negative_file = idcard_negative_file;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerified_time() {
        return verified_time;
    }

    public void setVerified_time(String verified_time) {
        this.verified_time = verified_time;
    }

    public String getVerified_type() {
        return verified_type;
    }

    public void setVerified_type(String verified_type) {
        this.verified_type = verified_type;
    }

    public PublicSecurityEssentialFactor getPublic_security_essential_factor() {
        return public_security_essential_factor;
    }

    public void setPublic_security_essential_factor(PublicSecurityEssentialFactor public_security_essential_factor) {
        this.public_security_essential_factor = public_security_essential_factor;
    }

    public MobileEssentialFactor getMobile_essential_factor() {
        return mobile_essential_factor;
    }

    public void setMobile_essential_factor(MobileEssentialFactor mobile_essential_factor) {
        this.mobile_essential_factor = mobile_essential_factor;
    }

    public BankEssentialFactor getBank_essential_factor() {
        return bank_essential_factor;
    }

    public void setBank_essential_factor(BankEssentialFactor bank_essential_factor) {
        this.bank_essential_factor = bank_essential_factor;
    }

    public MobileAndBankEssentialFactor getMobile_and_bank_essential_factor() {
        return mobile_and_bank_essential_factor;
    }

    public void setMobile_and_bank_essential_factor(MobileAndBankEssentialFactor mobile_and_bank_essential_factor) {
        this.mobile_and_bank_essential_factor = mobile_and_bank_essential_factor;
    }

    public LiveDetection getLive_detection() {
        return live_detection;
    }

    public void setLive_detection(LiveDetection live_detection) {
        this.live_detection = live_detection;
    }

    public File getLive_detection_file() {
        return live_detection_file;
    }

    public void setLive_detection_file(File live_detection_file) {
        this.live_detection_file = live_detection_file;
    }

}
