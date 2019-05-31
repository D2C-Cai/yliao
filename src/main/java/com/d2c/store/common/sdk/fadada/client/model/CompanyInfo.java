package com.d2c.store.common.sdk.fadada.client.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 企业信息
 *
 * @author ratacer
 */
public class CompanyInfo {

    /**
     * 企业邮箱（选填）
     */
    private String email;
    /**
     * 企业名称
     */
    private String company_name;
    /**
     * 企业营业执照号（统一社会信用代码）
     */
    private String license_no;
    /**
     * 资源编号(该主体在接入平台的唯一标识，length<100)
     */
    private String resource_id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    @Override
    public String toString() {
        return "CompanyInfo [email=" + email + ", company_name=" + company_name
                + ", license_no=" + license_no + ", resource_id=" + resource_id + "]";
    }

    public boolean checkNull() {
        return StringUtils.isBlank(company_name) || StringUtils.isBlank(license_no) || StringUtils.isBlank(resource_id);
    }

}
