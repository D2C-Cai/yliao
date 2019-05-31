package com.d2c.store.common.sdk.fadada.client.auth.model;

public class PublicSecurityEssentialFactor {

    /**
     * 申请编号
     */
    private String apply_num;
    /**
     * 查询时间
     */
    private String query_time;
    /**
     * 系统标志
     */
    private String system_flag;
    /**
     * 身份证号对比
     */
    private String idcard_compare;
    /**
     * 姓名对比
     */
    private String name_compare;
    /**
     * 实名认证提供方
     */
    private String vrified_provider;

    public String getApply_num() {
        return apply_num;
    }

    public void setApply_num(String apply_num) {
        this.apply_num = apply_num;
    }

    public String getQuery_time() {
        return query_time;
    }

    public void setQuery_time(String query_time) {
        this.query_time = query_time;
    }

    public String getSystem_flag() {
        return system_flag;
    }

    public void setSystem_flag(String system_flag) {
        this.system_flag = system_flag;
    }

    public String getIdcard_compare() {
        return idcard_compare;
    }

    public void setIdcard_compare(String idcard_compare) {
        this.idcard_compare = idcard_compare;
    }

    public String getName_compare() {
        return name_compare;
    }

    public void setName_compare(String name_compare) {
        this.name_compare = name_compare;
    }

    public String getVrified_provider() {
        return vrified_provider;
    }

    public void setVrified_provider(String vrified_provider) {
        this.vrified_provider = vrified_provider;
    }

}
