package com.d2c.store.common.sdk.fadada.client.auth;

import com.alibaba.fastjson.JSON;
import com.d2c.store.common.sdk.fadada.client.auth.model.CompanyDepositReq;
import com.d2c.store.common.sdk.fadada.client.common.FddClient;
import com.d2c.store.common.sdk.fadada.util.crypt.MsgDigestUtil;
import com.d2c.store.common.sdk.fadada.util.http.ClientMultipartFormPost;
import com.d2c.store.common.sdk.fadada.util.http.HttpsUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CompanyDeposit extends FddClient {

    public CompanyDeposit(String appId, String secret, String version, String url) {
        super(appId, secret, version, url);
    }

    /**
     * 企业信息实名存证地址
     *
     * @return
     */
    public String getCompanyDeposit() {
        return super.getUrl() + "company_deposit.api";
    }

    /**
     * 企业信息实名存证接口
     *
     * @param req
     * @return
     * @date: 2018年12月23日
     */
    public String invokeCompanyDeposit(CompanyDepositReq req) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, File> files = new HashMap<String, File>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            params.put("customer_id", req.getCustomer_id());
            params.put("preservation_name", req.getPreservation_name());
            params.put("preservation_desc", req.getPreservation_desc());
            params.put("preservation_data_provider", req.getPreservation_data_provider());
            params.put("company_name", req.getCompany_name());
            params.put("document_type", req.getDocument_type());
            params.put("credit_code", req.getCredit_code());
            params.put("licence", req.getLicence());
            params.put("organization", req.getOrganization());
            params.put("verified_time", req.getVerified_time());
            params.put("verified_mode", req.getVerified_mode());
            params.put("public_branch_bank", req.getPublic_branch_bank());
            params.put("public_bank_account", req.getPublic_bank_account());
            params.put("customer_bank", req.getCustomer_bank());
            params.put("customer_branch_bank", req.getCustomer_branch_bank());
            params.put("customer_bank_account", req.getCustomer_bank_account());
            params.put("pay_type", req.getPay_type());
            params.put("amount_or_random_code", req.getAmount_or_random_code());
            params.put("user_back_fill_amount_or_random_code", req.getUser_back_fill_amount_or_random_code());
            params.put("company_principal_type", req.getCompany_principal_type());
            params.put("legal_name", req.getLegal_name());
            params.put("legal_idcard", req.getLegal_idcard());
            params.put("transaction_id", req.getTransaction_id());
            if (null != req.getCompany_principal_verified_msg()) {
                String company_principal_verified_msg = JSON.toJSONString(req.getCompany_principal_verified_msg());
                params.put("company_principal_verified_msg", company_principal_verified_msg);
            }
            String[] sortforParameters = MsgDigestUtil.sortforParameters(params);
            msgDigest = MsgDigestUtil.getCheckMsgDigest(
                    super.getAppId(), super.getSecret(),
                    timeStamp, sortforParameters);
            if (null != req.getCredit_code_file()) {
                files.put("credit_code_file", req.getCredit_code_file());
            }
            if (null != req.getLicence_file()) {
                files.put("licence_file", req.getLicence_file());
            }
            if (null != req.getOrganization_file()) {
                files.put("organization_file", req.getOrganization_file());
            }
            if (null != req.getPower_attorney_file()) {
                files.put("power_attorney_file", req.getPower_attorney_file());
            }
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
        return ClientMultipartFormPost.doPost(this.getCompanyDeposit(), params, files);
    }

}
