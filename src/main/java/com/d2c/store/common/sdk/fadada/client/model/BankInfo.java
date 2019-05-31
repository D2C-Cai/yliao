package com.d2c.store.common.sdk.fadada.client.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 对公账号信息
 *
 * @author ratacer
 */
public class BankInfo {

    /**
     * 银行名称
     */
    private String bank_name;
    /**
     * 银行帐号
     */
    private String bank_id;
    /**
     * 开户支行名称
     */
    private String subbranch_name;
    /**
     * 开户支行所在省
     */
    private String subbranch_province;
    /**
     * 开户支行所在市
     */
    private String subbranch_city;

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getSubbranch_name() {
        return subbranch_name;
    }

    public void setSubbranch_name(String subbranch_name) {
        this.subbranch_name = subbranch_name;
    }

    public String getSubbranch_province() {
        return subbranch_province;
    }

    public void setSubbranch_province(String subbranch_province) {
        this.subbranch_province = subbranch_province;
    }

    public String getSubbranch_city() {
        return subbranch_city;
    }

    public void setSubbranch_city(String subbranch_city) {
        this.subbranch_city = subbranch_city;
    }

    @Override
    public String toString() {
        return "BankInfo [bank_name=" + bank_name + ", bank_id=" + bank_id
                + ", subbranch_name=" + subbranch_name
                + ", subbranch_province=" + subbranch_province
                + ", subbranch_city=" + subbranch_city + "]";
    }

    public boolean checkNull() {
        return StringUtils.isBlank(bank_name) || StringUtils.isBlank(bank_id) ||
                StringUtils.isBlank(subbranch_name);
    }

}
