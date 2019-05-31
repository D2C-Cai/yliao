/*
 * 版权所有：深圳法大大网络科技有限公司
 * Copyright 2015 fadada.com Inc.
 * All right reserved.
 * ====================================================
 * 文件名称: FadadaClient.java
 * 修订记录：
 * No    日期				作者(操作:具体内容)
 * 1.    Dec 18, 2015			Mocuishle(创建:创建文件)
 * ====================================================
 * 类描述：(说明未实现或其它不应生成javadoc的内容)
 */
package com.d2c.store.common.sdk.fadada.client;

import com.d2c.store.common.sdk.fadada.client.common.FddClient;
import com.d2c.store.common.sdk.fadada.util.crypt.FddEncryptTool;
import com.d2c.store.common.sdk.fadada.util.http.HttpsUtil;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>概要:</h3> 扩展接口调用类 <br>
 * <h3>功能:</h3>
 * <ol>
 * <li></li>
 * <li>客户信息查询接口</li>
 * <li>客户信息修改接口</li>
 * <li>查看已签署文档接口</li>
 * <li>下载已签署接口</li>
 * <li>文档验签接口</li>
 * <li>查询合同HASH值接口</li>
 * <li>文档签署接口（含有效期和次数）</li>
 * <li>文档临时查看/下载地址接口（含有效期和次数）</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2017年3月16日[zhouxw] 新建</li>
 * </ol>
 */
public class FddClientExtra extends FddClient {

    public FddClientExtra(String appId, String secret, String version, String url) {
        super(appId, secret, version, url);
    }

    public FddClientExtra(String appId, String secret, String version, String url, String proxyHost, String proxyFlag, String proxyPort) {
        super(appId, secret, version, url, proxyHost, proxyFlag, proxyPort);
    }

    /**
     * 调用修改用户信息接口
     *
     * @param customer_id 客户编号
     * @param email       邮箱
     * @param mobile      手机号码11位
     * @author: zhouxw
     * @date: 2017年4月20日
     */
    public String invokeInfoChange(String customer_id, String email, String mobile) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest = "";
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfInfoChange(), params);
    }

    /**
     * 调用下载文档接口（写到指定路径）
     *
     * @param path        保存地址示例：D:/pdf/uuid.pdf
     * @param contract_id 合同编号
     * @return 返回null表示下载成功；返回str表示失败，str为服务端返回的json格式报文
     * @author: zhouxw
     * @date: 2017年4月20日
     */
    public String invokeDownloadPdf(String path, String contract_id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest = "";
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPostDownload(path, super.getURLOfDownloadPdf(), params);
    }

    /**
     * 调用下载文档接口（获取地址）
     *
     * @return 合同下载地址
     * @author: zhouxw
     * @date: 2017年3月16日
     */
    public String invokeDownloadPdf(String contract_id) {
        StringBuilder sb = new StringBuilder();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();
            sb.append(super.getURLOfDownloadPdf());
            sb.append("?app_id=").append(super.getAppId());
            sb.append("&v=").append(super.getVersion());
            sb.append("&timestamp=").append(timeStamp);
            sb.append("&contract_id=").append(contract_id);
            sb.append("&msg_digest=").append(msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 查看已签署文档接口
     *
     * @param contract_id 合同编号
     * @author: liuyz
     * @date: 2017年3月16日
     */
    public String invokeViewPdfURL(String contract_id) {
        StringBuilder sb = new StringBuilder();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+contract_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            sb.append(super.getURLOfViewContract());
            sb.append("?app_id=").append(super.getAppId());
            sb.append("&v=").append(super.getVersion());
            sb.append("&timestamp=").append(timeStamp);
            sb.append("&contract_id=").append(contract_id);
            sb.append("&msg_digest=").append(msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 调用文档验签接口
     *
     * @param doc_url 文件url
     * @param file    合同文件
     * @author: liuyz
     * @date: 2017年3月16日
     */
    public String invokeContractVerify(String doc_url, File file) {
        MultipartEntity reqEntity = new MultipartEntity(); // 对请求的表单域进行填充
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ doc_url)))没有doc_url可以不包含计算
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + doc_url));
            String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            if (file != null) {
                FileBody fileBody = new FileBody(file); // 创建待处理的文件
                reqEntity.addPart("file", fileBody);
            }
            if (doc_url != null) {
                doc_url = doc_url.replaceAll(" ", "%20");
                reqEntity.addPart("doc_url", new StringBody(doc_url, Charset.forName(HttpsUtil.charset)));
            }
            reqEntity.addPart("app_id", new StringBody(super.getAppId()));
            reqEntity.addPart("v", new StringBody(super.getVersion()));
            reqEntity.addPart("timestamp", new StringBody(timeStamp));
            reqEntity.addPart("msg_digest", new StringBody(msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfContractVerify(), reqEntity);
    }

    /**
     * 调用查询合同HASH值接口
     *
     * @param contract_id 合同编号
     * @author: liuyz
     * @date: 2017年3月16日
     */
    public String invokeGetContractHash(String contract_id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest = "";
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfContractHash(), params);
    }

    /**
     * 调用文档签署接口（含有效期和次数）
     *
     * @param transaction_id 交易号
     * @param customer_id    客户编号
     * @param contract_id    合同编号
     * @param doc_title      文档标题
     * @param sign_keyword   定位关键字
     * @param validity       单位为分钟的有效时间，格式为大于0，小于10080（七天对应的分钟数）的整数
     * @param quantity       有效次数格式为大于0的整数
     * @param return_url     跳转地址
     * @param notify_url     签章结果接收地址（异步通知）
     * @author: zhouxw
     * @date: 2017年4月1日
     */
    public String invokeExtSignValidation(String transaction_id, String customer_id, String contract_id, String doc_title, String sign_keyword, String validity, String quantity, String return_url,
                                          String notify_url) {
        String timeStamp = HttpsUtil.getTimeStamp();
        StringBuilder sb = new StringBuilder(super.getURLOfExtSignValidation());
        try {
            String msgDigest = "";
            // Base64(SHA1(app_id+md5(transaction_id+timestamp+ validity+quantity)+S HA1(app_secret+ customer_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(transaction_id + timeStamp + validity + quantity) + FddEncryptTool.sha1(super.getSecret() + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            sb.append("?app_id=").append(super.getAppId());
            sb.append("&v=").append(super.getVersion());
            sb.append("&timestamp=").append(timeStamp);
            sb.append("&transaction_id=").append(transaction_id);
            sb.append("&customer_id=").append(customer_id);
            sb.append("&contract_id=").append(contract_id);
            sb.append("&doc_title=").append(URLEncoder.encode(doc_title, HttpsUtil.charset));
            sb.append("&sign_keyword=").append(URLEncoder.encode(sign_keyword, HttpsUtil.charset));
            sb.append("&validity=").append(validity);
            sb.append("&quantity=").append(quantity);
//            sb.append("&return_url=").append(URLEncoder.encode(return_url, HttpsUtil.charset));
//            sb.append("&notify_url=").append(URLEncoder.encode(notify_url, HttpsUtil.charset));
            sb.append("&return_url=").append(return_url);
            sb.append("&notify_url=").append(notify_url);
            sb.append("&msg_digest=").append(msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 文档临时查看/下载地址接口（含有效期和次数）
     *
     * @param contract_id 合同编号
     * @param validity    有效期（分钟） 大于0的整数
     * @param quantity    有效次数 大于0的整数
     * @author: zhouxw
     * @date: 2017年3月16日
     */
    public String invokeGetUrl(String contract_id, String validity, String quantity) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest = "";
            // Base64(SHA1(app_id+md5(timestamp+ validity+ quantity)+SHA1(app_secret+ contract _id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp + validity + quantity) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("validity", validity));
            params.add(new BasicNameValuePair("quantity", quantity));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfViewUrl(), params);
    }

}
