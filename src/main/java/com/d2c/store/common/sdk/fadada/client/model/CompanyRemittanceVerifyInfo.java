package com.d2c.store.common.sdk.fadada.client.model;

import java.io.File;

public class CompanyRemittanceVerifyInfo {

    private AgentInfo agentInfo;
    private BankInfo bankInfo;
    private CompanyInfo companyInfo;
    private LegalInfo legalInfo;
    private File legalIdImageFile; //法人身份证正反面复印件
    private File agentImageFile; //代理人手持身份证照片
    private File authorizationImageFile;    //授权委托书
    private File licenseImageFile;    //多合一营业执照图片
    private File legalImageFile; //法人手持身份证照片
    private String legalIdImageUrl; //法人身份证正反面复印件
    private String agentImageUrl; //代理人手持身份证照片
    private String authorizationImageUrl;    //授权委托书
    private String licenseImageUrl;    //多合一营业执照图片
    private String legalImageUrl; //法人手持身份证照片

    public AgentInfo getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(AgentInfo agentInfo) {
        this.agentInfo = agentInfo;
    }

    public BankInfo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public LegalInfo getLegalInfo() {
        return legalInfo;
    }

    public void setLegalInfo(LegalInfo legalInfo) {
        this.legalInfo = legalInfo;
    }

    public File getLegalIdImageFile() {
        return legalIdImageFile;
    }

    public void setLegalIdImageFile(File legalIdImageFile) {
        this.legalIdImageFile = legalIdImageFile;
    }

    public File getAgentImageFile() {
        return agentImageFile;
    }

    public void setAgentImageFile(File agentImageFile) {
        this.agentImageFile = agentImageFile;
    }

    public File getAuthorizationImageFile() {
        return authorizationImageFile;
    }

    public void setAuthorizationImageFile(File authorizationImageFile) {
        this.authorizationImageFile = authorizationImageFile;
    }

    public File getLicenseImageFile() {
        return licenseImageFile;
    }

    public void setLicenseImageFile(File licenseImageFile) {
        this.licenseImageFile = licenseImageFile;
    }

    public File getLegalImageFile() {
        return legalImageFile;
    }

    public void setLegalImageFile(File legalImageFile) {
        this.legalImageFile = legalImageFile;
    }

    public String getLegalIdImageUrl() {
        return legalIdImageUrl;
    }

    public void setLegalIdImageUrl(String legalIdImageUrl) {
        this.legalIdImageUrl = legalIdImageUrl;
    }

    public String getAgentImageUrl() {
        return agentImageUrl;
    }

    public void setAgentImageUrl(String agentImageUrl) {
        this.agentImageUrl = agentImageUrl;
    }

    public String getAuthorizationImageUrl() {
        return authorizationImageUrl;
    }

    public void setAuthorizationImageUrl(String authorizationImageUrl) {
        this.authorizationImageUrl = authorizationImageUrl;
    }

    public String getLicenseImageUrl() {
        return licenseImageUrl;
    }

    public void setLicenseImageUrl(String licenseImageUrl) {
        this.licenseImageUrl = licenseImageUrl;
    }

    public String getLegalImageUrl() {
        return legalImageUrl;
    }

    public void setLegalImageUrl(String legalImageUrl) {
        this.legalImageUrl = legalImageUrl;
    }

}
