package com.d2c.store.common.sdk.sms.emay.eucp.inter.http.v1.dto.request;

import com.d2c.store.common.sdk.sms.emay.eucp.inter.framework.dto.PersonalityParams;

/**
 * 批量短信发送参数
 *
 * @author Frank
 */
public class SmsPersonalityAllRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    private PersonalityParams[] smses;

    public PersonalityParams[] getSmses() {
        return smses;
    }

    public void setSmses(PersonalityParams[] smses) {
        this.smses = smses;
    }

}
