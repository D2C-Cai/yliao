package com.d2c.store.common.sdk.fadada.client.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 法人信息
 *
 * @author ratacer
 */
public class LegalInfo {

    /**
     * 法人姓名
     */
    private String legal_name;
    /**
     * 法人证件号(身份证)
     */
    private String legal_id;
    /**
     * 法人手机号(仅支持国内运营商)
     */
    private String legal_mobile;

    public String getLegal_name() {
        return legal_name;
    }

    public void setLegal_name(String legal_name) {
        this.legal_name = legal_name;
    }

    public String getLegal_id() {
        return legal_id;
    }

    public void setLegal_id(String legal_id) {
        this.legal_id = legal_id;
    }

    public String getLegal_mobile() {
        return legal_mobile;
    }

    public void setLegal_mobile(String legal_mobile) {
        this.legal_mobile = legal_mobile;
    }

    @Override
    public String toString() {
        return "LegalInfo [legal_name=" + legal_name + ", legal_id=" + legal_id
                + ", legal_mobile=" + legal_mobile + "]";
    }

    public boolean checkNull() {
        return StringUtils.isBlank(legal_name) || StringUtils.isBlank(legal_id) || StringUtils.isBlank(legal_mobile);
    }

}
