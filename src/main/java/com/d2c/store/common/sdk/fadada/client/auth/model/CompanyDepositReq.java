package com.d2c.store.common.sdk.fadada.client.auth.model;

import java.io.File;

public class CompanyDepositReq {

    /**
     * 客户编号
     */
    private String customer_id;
    /**
     * 存证名称（len<=100）
     */
    private String preservation_name;
    /**
     * 存证描述（len<=100）
     */
    private String preservation_desc;
    /**
     * 存证数据提供方（len<=30）
     */
    private String preservation_data_provider;
    /**
     * 企业名称
     */
    private String company_name;
    /**
     * 证件类型
     * 1:三证合一
     * 2：旧版营业执照
     */
    private String document_type;
    /**
     * 统一社会信用代码 document_type =1时必填
     */
    private String credit_code;
    /**
     * 统一社会信用代码电子版
     */
    private File credit_code_file;
    /**
     * 营业注册号
     */
    private String licence;
    /**
     * 营业注册号电子版
     */
    private File licence_file;
    /**
     * 组织机构代码 document_type =2时必填
     */
    private String organization;
    /**
     * 组织机构代码电子版
     */
    private File organization_file;
    /**
     * 实名时间 yyyyMMddHHmmss
     */
    private String verified_time;
    /**
     * 实名认证方式
     * 1:授权委托书
     * 2:银行对公打款
     */
    private String verified_mode;
    /**
     * 授权委托书电子版 调资源维护接口返回verifiedMode =1必填
     */
    private File power_attorney_file;
    /**
     * 己方银行支行 verified_mode =2填
     */
    private String public_branch_bank;
    /**
     * 己方银行账号 verified_mode =2填
     */
    private String public_bank_account;
    /**
     * 客户打款银行 verified_mode =2填
     */
    private String customer_bank;
    /**
     * 客户银行支行 verified_mode =2填
     */
    private String customer_branch_bank;
    /**
     * 客户银行账号 verified_mode =2填
     */
    private String customer_bank_account;
    /**
     * 打款类型  1.随机码 2.随机金额  verified_mode =2填
     */
    private String pay_type;
    /**
     * 打款金额/打款随机码
     */
    private String amount_or_random_code;
    /**
     * 用户回填金额/随机码
     */
    private String user_back_fill_amount_or_random_code;
    /**
     * 企业负责人身份:1.法人，2代理人
     */
    private String company_principal_type;
    /**
     * json 企业负责人实名存证信息
     */
    private CompanyPrincipalVerifiedMsg company_principal_verified_msg;
    /**
     * 法人姓名 company_principal_type=2必填
     */
    private String legal_name;
    /**
     * 法人身份证号 company_principal_type=2必填
     */
    private String legal_idcard;
    /**
     * 交易号
     */
    private String transaction_id;
    /**
     * 证件照正面
     */
    private File idcard_positive_file;
    /**
     * 证件照反面
     */
    private File idcard_negative_file;
    /**
     * 活体检测图
     */
    private File live_detection_file;

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

    public File getLive_detection_file() {
        return live_detection_file;
    }

    public void setLive_detection_file(File live_detection_file) {
        this.live_detection_file = live_detection_file;
    }

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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getCredit_code() {
        return credit_code;
    }

    public void setCredit_code(String credit_code) {
        this.credit_code = credit_code;
    }

    public File getCredit_code_file() {
        return credit_code_file;
    }

    public void setCredit_code_file(File credit_code_file) {
        this.credit_code_file = credit_code_file;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public File getLicence_file() {
        return licence_file;
    }

    public void setLicence_file(File licence_file) {
        this.licence_file = licence_file;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public File getOrganization_file() {
        return organization_file;
    }

    public void setOrganization_file(File organization_file) {
        this.organization_file = organization_file;
    }

    public String getVerified_time() {
        return verified_time;
    }

    public void setVerified_time(String verified_time) {
        this.verified_time = verified_time;
    }

    public String getVerified_mode() {
        return verified_mode;
    }

    public void setVerified_mode(String verified_mode) {
        this.verified_mode = verified_mode;
    }

    public File getPower_attorney_file() {
        return power_attorney_file;
    }

    public void setPower_attorney_file(File power_attorney_file) {
        this.power_attorney_file = power_attorney_file;
    }

    public String getPublic_branch_bank() {
        return public_branch_bank;
    }

    public void setPublic_branch_bank(String public_branch_bank) {
        this.public_branch_bank = public_branch_bank;
    }

    public String getPublic_bank_account() {
        return public_bank_account;
    }

    public void setPublic_bank_account(String public_bank_account) {
        this.public_bank_account = public_bank_account;
    }

    public String getCustomer_bank() {
        return customer_bank;
    }

    public void setCustomer_bank(String customer_bank) {
        this.customer_bank = customer_bank;
    }

    public String getCustomer_branch_bank() {
        return customer_branch_bank;
    }

    public void setCustomer_branch_bank(String customer_branch_bank) {
        this.customer_branch_bank = customer_branch_bank;
    }

    public String getCustomer_bank_account() {
        return customer_bank_account;
    }

    public void setCustomer_bank_account(String customer_bank_account) {
        this.customer_bank_account = customer_bank_account;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getAmount_or_random_code() {
        return amount_or_random_code;
    }

    public void setAmount_or_random_code(String amount_or_random_code) {
        this.amount_or_random_code = amount_or_random_code;
    }

    public String getUser_back_fill_amount_or_random_code() {
        return user_back_fill_amount_or_random_code;
    }

    public void setUser_back_fill_amount_or_random_code(String user_back_fill_amount_or_random_code) {
        this.user_back_fill_amount_or_random_code = user_back_fill_amount_or_random_code;
    }

    public String getCompany_principal_type() {
        return company_principal_type;
    }

    public void setCompany_principal_type(String company_principal_type) {
        this.company_principal_type = company_principal_type;
    }

    public CompanyPrincipalVerifiedMsg getCompany_principal_verified_msg() {
        return company_principal_verified_msg;
    }

    public void setCompany_principal_verified_msg(CompanyPrincipalVerifiedMsg company_principal_verified_msg) {
        this.company_principal_verified_msg = company_principal_verified_msg;
    }

    public String getLegal_name() {
        return legal_name;
    }

    public void setLegal_name(String legal_name) {
        this.legal_name = legal_name;
    }

    public String getLegal_idcard() {
        return legal_idcard;
    }

    public void setLegal_idcard(String legal_idcard) {
        this.legal_idcard = legal_idcard;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

}
