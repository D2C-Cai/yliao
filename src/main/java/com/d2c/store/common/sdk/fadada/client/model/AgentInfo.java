package com.d2c.store.common.sdk.fadada.client.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 代理人信息
 *
 * @author ratacer
 */
public class AgentInfo {

    /**
     * 法人姓名（选填）
     */
    private String legal_name;
    /**
     * 法人身份证号（选填）
     */
    private String legal_id;
    /**
     * 代理人姓名
     */
    private String agent_name;
    /**
     * 代理人身份证号码
     */
    private String agent_id;
    /**
     * 代理人手机号
     */
    private String agent_mobile;

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

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_mobile() {
        return agent_mobile;
    }

    public void setAgent_mobile(String agent_mobile) {
        this.agent_mobile = agent_mobile;
    }

    @Override
    public String toString() {
        return "AgentInfo [legal_name=" + legal_name + ", legal_id=" + legal_id + ", agent_name=" + agent_name
                + ", agent_id=" + agent_id + ", agent_mobile=" + agent_mobile + "]";
    }

    public boolean checkNull() {
        return StringUtils.isBlank(agent_name) || StringUtils.isBlank(agent_id) || StringUtils.isBlank(agent_mobile);
    }

}
