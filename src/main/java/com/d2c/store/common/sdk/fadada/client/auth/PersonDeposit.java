package com.d2c.store.common.sdk.fadada.client.auth;

import com.alibaba.fastjson.JSON;
import com.d2c.store.common.sdk.fadada.client.auth.model.PersonDepositReq;
import com.d2c.store.common.sdk.fadada.client.common.FddClient;
import com.d2c.store.common.sdk.fadada.util.crypt.MsgDigestUtil;
import com.d2c.store.common.sdk.fadada.util.http.ClientMultipartFormPost;
import com.d2c.store.common.sdk.fadada.util.http.HttpsUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PersonDeposit extends FddClient {

    public PersonDeposit(String appId, String secret, String version, String url) {
        super(appId, secret, version, url);
    }

    /**
     * 个人实名信息存证地址
     *
     * @return
     */
    public String getPersonDeposit() {
        return super.getUrl() + "person_deposit.api";
    }

    /**
     * 个人实名信息存证接口
     *
     * @param req
     * @return
     * @date: 2018年12月23日
     */
    public String invokePersonDeposit(PersonDepositReq req) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, File> files = new HashMap<String, File>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            params.put("customer_id", req.getCustomer_id());
            params.put("preservation_name", req.getPreservation_name());
            params.put("preservation_desc", req.getPreservation_desc());
            params.put("preservation_data_provider", req.getPreservation_data_provider());
            params.put("name", req.getName());
            params.put("document_type", req.getDocument_type());
            params.put("idcard", req.getIdcard());
            params.put("mobile", req.getMobile());
            params.put("verified_time", req.getVerified_time());
            params.put("verified_type", req.getVerified_type());
            if (null != req.getPublic_security_essential_factor()) {
                String public_security_essential_factor = JSON.toJSONString(req.getPublic_security_essential_factor());
                params.put("public_security_essential_factor", public_security_essential_factor);
            }
            if (null != req.getMobile_essential_factor()) {
                String mobile_essential_factor = JSON.toJSONString(req.getMobile_essential_factor());
                params.put("mobile_essential_factor", mobile_essential_factor);
            }
            if (null != req.getBank_essential_factor()) {
                String bank_essential_factor = JSON.toJSONString(req.getBank_essential_factor());
                params.put("bank_essential_factor", bank_essential_factor);
            }
            if (null != req.getMobile_and_bank_essential_factor()) {
                String mobile_and_bank_essential_factor = JSON.toJSONString(req.getMobile_and_bank_essential_factor());
                params.put("mobile_and_bank_essential_factor", mobile_and_bank_essential_factor);
            }
            if (null != req.getLive_detection()) {
                String live_detection = JSON.toJSONString(req.getLive_detection());
                params.put("live_detection", live_detection);
            }
            String[] sortforParameters = MsgDigestUtil.sortforParameters(params);
            msgDigest = MsgDigestUtil.getCheckMsgDigest(
                    super.getAppId(), super.getSecret(),
                    timeStamp, sortforParameters);
            if (null != req.getIdcard_positive_file()) {
                files.put("idcard_positive_file", req.getIdcard_positive_file());
            }
            if (null != req.getIdcard_negative_file()) {
                files.put("idcard_negative_file", req.getIdcard_negative_file());
            }
            if (null != req.getLive_detection_file()) {
                files.put("live_detection_file", req.getLive_detection_file());
            }
            params.put("app_id", super.getAppId());
            params.put("timestamp", timeStamp);
            params.put("v", super.getVersion());
            params.put("msg_digest", msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ClientMultipartFormPost.doPost(this.getPersonDeposit(), params, files);
    }

}
