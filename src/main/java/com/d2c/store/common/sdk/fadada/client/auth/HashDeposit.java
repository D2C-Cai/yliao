package com.d2c.store.common.sdk.fadada.client.auth;

import com.d2c.store.common.sdk.fadada.client.common.FddClient;
import com.d2c.store.common.sdk.fadada.util.crypt.FddEncryptTool;
import com.d2c.store.common.sdk.fadada.util.crypt.MsgDigestUtil;
import com.d2c.store.common.sdk.fadada.util.http.ClientMultipartFormPost;
import com.d2c.store.common.sdk.fadada.util.http.HttpsUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HashDeposit extends FddClient {

    public HashDeposit(String appId, String secret, String version, String url) {
        super(appId, secret, version, url);
    }

    /**
     * 实名信息哈希存证地址
     *
     * @return
     */
    public String getHashDepositUrl() {
        return super.getUrl() + "hash_deposit.api";
    }

    /**
     * 实名信息哈希存证
     *
     * @param customer_id       客户编号
     * @param preservation_name 存证名称
     * @param preservation_desc 存证描述
     * @param file              存证文件
     * @param transaction_id    交易号
     * @return
     * @date: 2018年12月23日
     */
    public String invokeHashDeposit(String customer_id, String preservation_name,
                                    String preservation_desc, File file, String transaction_id) {
        String file_name = "";//fileName 文件名
        String file_size = "";//fileSize 文件大小
        String original_sha256 = "";//sha256 sha256值
        String noper_time = "";//文件最后修改时间
        if (null != file) {
            file_name = file.getName();//fileName 文件名
            file_size = String.valueOf(file.length());//fileSize 文件大小
            original_sha256 = FddEncryptTool.sha256(file);//sha256 sha256值
            noper_time = String.valueOf(file.lastModified() / 1000);//文件最后修改时间
        }
        return invokeHashDeposit(customer_id, preservation_name,
                preservation_desc, file_name, noper_time, file_size,
                original_sha256, transaction_id);
    }

    /**
     * 实名信息哈希存证
     *
     * @param customer_id       客户编号
     * @param preservation_name 存证名称
     * @param preservation_desc 存证描述
     * @param file_name         文件名
     * @param noper_time        文件最后修改时间
     * @param file_size         文件大小
     * @param original_sha256   文件hash值
     * @param transaction_id    交易号
     * @return
     * @date: 2018年12月23日
     */
    public String invokeHashDeposit(String customer_id, String preservation_name,
                                    String preservation_desc, String file_name,
                                    String noper_time, String file_size,
                                    String original_sha256, String transaction_id) {
        Map<String, String> params = new HashMap<String, String>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            params.put("customer_id", customer_id);
            params.put("preservation_name", preservation_name);
            params.put("preservation_desc", preservation_desc);
            params.put("file_name", file_name);
            params.put("noper_time", noper_time);
            params.put("file_size", file_size);
            params.put("original_sha256", original_sha256);
            params.put("transaction_id", transaction_id);
            String[] sortforParameters = MsgDigestUtil.sortforParameters(params);
            msgDigest = MsgDigestUtil.getCheckMsgDigest(
                    super.getAppId(), super.getSecret(),
                    timeStamp, sortforParameters);
            params.put("app_id", super.getAppId());
            params.put("timestamp", timeStamp);
            params.put("v", super.getVersion());
            params.put("msg_digest", msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ClientMultipartFormPost.doPost(this.getHashDepositUrl(), params, null);
    }

}
