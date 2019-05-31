package com.d2c.store.common.sdk.fadada.client.auth;

import com.d2c.store.common.sdk.fadada.client.common.FddClient;
import com.d2c.store.common.sdk.fadada.util.crypt.FddEncryptTool;
import com.d2c.store.common.sdk.fadada.util.http.HttpsUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ApplyClientNumCert extends FddClient {

    public ApplyClientNumCert(String appId, String secret, String version, String url) {
        super(appId, secret, version, url);
    }

    public String getApplyClientNumCertUrl() {
        return super.getUrl() + "apply_client_numcert.api";
    }

    /**
     * 编号证书申请接口
     *
     * @param customer_id 客户编号 注册账号时返回
     * @param evidence_no 存证编号 实名信息存证时返回
     * @return
     * @date: 2018年12月23日
     */
    public String invokeapplyClinetNumcert(String customer_id, String evidence_no) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(appid+md5(timestamp)+SHA1(appsecret+customer_id+sign_password+verified_serialno)))
            String sha1 = FddEncryptTool.sha1(super.getAppId()
                    + FddEncryptTool.md5Digest(timeStamp)
                    + FddEncryptTool.sha1(super.getSecret()
                    + customer_id
                    + evidence_no));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("evidence_no", evidence_no));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doCaPost(this.getApplyClientNumCertUrl(), params);
    }

}
