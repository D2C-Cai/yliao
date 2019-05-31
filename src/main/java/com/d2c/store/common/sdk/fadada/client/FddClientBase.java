/*
  版权所有：深圳法大大网络科技有限公司
  Copyright 2015 fadada.com Inc.
  All right reserved.
 ====================================================
  文件名称: FddClientBase.java
  修订记录：
  No    日期				作者(操作:具体内容)
  1.    Dec 18, 2015			Mocuishle(创建:创建文件)
 ====================================================
  类描述：(说明未实现或其它不应生成javadoc的内容)

 */
package com.d2c.store.common.sdk.fadada.client;

import com.d2c.store.common.sdk.fadada.client.common.FddClient;
import com.d2c.store.common.sdk.fadada.client.request.ExtsignReq;
import com.d2c.store.common.sdk.fadada.client.request.SignResultQueryRequest;
import com.d2c.store.common.sdk.fadada.util.crypt.FddEncryptTool;
import com.d2c.store.common.sdk.fadada.util.crypt.MsgDigestUtil;
import com.d2c.store.common.sdk.fadada.util.http.HttpsUtil;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>概要:</h3>法大大基础接口调用类 <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>个人CA证书申请接口</li>
 * <li>文档传输接口</li>
 * <li>合同模板传输接口</li>
 * <li>合同生成接口</li>
 * <li>文档签署接口（手动签署模式）</li>
 * <li>文档签署接口（自动签署模式）</li>
 * <li>客户签署状态查询接口</li>
 * <li>归档接口</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2017年3月15日[zhoujy] 新建</li>
 * </ol>
 */
public class FddClientBase extends FddClient {

    /**
     * 概要：FddClientBase类的构造函数
     *
     * @param appId   请传入贵平台的appId
     * @param secret  请传入贵平台的secret
     * @param version 版本号，默认2.0
     * @param url     接口地址
     */
    public FddClientBase(String appId, String secret, String version, String url) {
        super(appId, secret, version, url);
    }

    public FddClientBase(String appId, String secret, String version, String url, String proxyHost, String proxyFlag, String proxyPort) {
        super(appId, secret, version, url, proxyHost, proxyFlag, proxyPort);
    }

    /**
     * 调用个人ca注册
     *
     * @param customer_name 名称
     * @param email         邮箱
     * @param id_card       证件号码
     * @param ident_type    证件类型
     * @param mobile        手机号
     */
    @Deprecated
    public String invokeSyncPersonAuto(String customer_name, String email, String id_card, String ident_type, String mobile) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(appid+md5(timestamp)+SHA1(appsecret)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("customer_name", customer_name));
            params.add(new BasicNameValuePair("email", email));
            String id_mobile = FddEncryptTool.encrypt(id_card + "|" + mobile, super.getSecret());
            params.add(new BasicNameValuePair("ident_type", ident_type));
            params.add(new BasicNameValuePair("id_mobile", id_mobile));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doCaPost(super.getURLOfSyncPersonAuto(), params);
    }

    /**
     * 注册账号接口
     *
     * @param open_id      用户在接入方的唯一标识 len<=64
     * @param account_type 1:个人，2:企业
     * @return
     * @date: 2018年12月23日
     */
    public String invokeregisterAccount(String open_id, String account_type) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(appid+md5(timestamp)+SHA1(appsecret+account_type+open_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId()
                    + FddEncryptTool.md5Digest(timeStamp)
                    + FddEncryptTool.sha1(super.getSecret()
                    + account_type + open_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("open_id", open_id));
            params.add(new BasicNameValuePair("account_type", account_type));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfregisterAccount(), params);
    }

    /**
     * 印章上传接口
     *
     * @param customer_id 客户编号 注册账号时返回
     * @param imgfile     签章图片file
     * @param imgUrl      签章图片下载地址
     * @return
     * @date: 2018年12月23日
     */
    public String invokeaddSignature(String customer_id, File imgfile, String imgUrl) {
        String signature_img_base64 = "";
        if (null != imgfile) {
            //file-->base64
            signature_img_base64 = FddEncryptTool.getimgBase64(imgfile);
        } else {
            //imgUrl-->base64
            signature_img_base64 = FddEncryptTool.ImageToBase64ByOnline(imgUrl);
        }
        return invokeaddSignature(customer_id, signature_img_base64);
    }

    /**
     * 印章上传接口
     *
     * @param customer_id          客户编号 注册账号时返回
     * @param signature_img_base64 签章图片base64
     * @return
     * @date: 2018年12月23日
     */
    public String invokeaddSignature(String customer_id, String signature_img_base64) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(appid+md5(timestamp)+SHA1(appsecret+customer_id+signature_img_base64)))
            String sha1 = FddEncryptTool.sha1(super.getAppId()
                    + FddEncryptTool.md5Digest(timeStamp)
                    + FddEncryptTool.sha1(super.getSecret()
                    + customer_id
                    + signature_img_base64));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("signature_img_base64", signature_img_base64));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfaddSignature(), params);
    }

    /**
     * 自定义印章接口
     *
     * @param customer_id 客户编号 注册账号时返回
     * @param content     印章展示的内容
     * @return
     * @date: 2018年12月23日
     */
    public String invokecustomSignature(String customer_id, String content) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(appid+md5(timestamp)+SHA1(appsecret+customer_id+signature_img_base64)))
            String sha1 = FddEncryptTool.sha1(super.getAppId()
                    + FddEncryptTool.md5Digest(timeStamp)
                    + FddEncryptTool.sha1(super.getSecret()
                    + content
                    + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("content", content));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfcustomSignature(), params);
    }

    /**
     * 调用文档传输接口
     *
     * @param contract_id 合同编号
     * @param doc_title   合同标题
     * @param file        合同文件,与doc_url两个只传一个
     * @param doc_url     合同文件URL（公网）地址
     * @param doc_type    合同类型（.pdf）
     */
    public String invokeUploadDocs(String contract_id, String doc_title, File file, String doc_url, String doc_type) {
        MultipartEntity reqEntity = new MultipartEntity(); // 对请求的表单域进行填充
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            if (file != null) {
                FileBody fileBody = new FileBody(file); // 创建待处理的文件
                reqEntity.addPart("file", fileBody);
            }
            if (doc_url != null) {
                doc_url = doc_url.replaceAll(" ", "%20");
                reqEntity.addPart("doc_url", new StringBody(doc_url, Charset.forName(HttpsUtil.charset)));
            }
            reqEntity.addPart("contract_id", new StringBody(contract_id, Charset.forName(HttpsUtil.charset)));
            reqEntity.addPart("doc_title", new StringBody(doc_title, Charset.forName(HttpsUtil.charset)));
            reqEntity.addPart("doc_type", new StringBody(doc_type, Charset.forName(HttpsUtil.charset)));
            reqEntity.addPart("app_id", new StringBody(super.getAppId()));
            reqEntity.addPart("v", new StringBody(super.getVersion()));
            reqEntity.addPart("timestamp", new StringBody(timeStamp));
            reqEntity.addPart("msg_digest", new StringBody(msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfUploadDocs(), reqEntity);
    }

    /**
     * 调用上传合同模板接口
     *
     * @param template_id 模板ID
     * @param file        模板文件
     * @param doc_url     模板URL
     */
    public String invokeUploadTemplate(String template_id, File file, String doc_url) {
        MultipartEntity reqEntity = new MultipartEntity(); // 对请求的表单域进行填充
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ template_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + template_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            reqEntity.addPart("template_id", new StringBody(template_id, Charset.forName(HttpsUtil.charset)));
            if (null != file) {
                FileBody fileBody = new FileBody(file); // 创建待处理的文件
                reqEntity.addPart("file", fileBody);
            }
            if (null != doc_url) {
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
        return HttpsUtil.doPost(super.getURLOfUploadTemplate(), reqEntity);
    }

    /**
     * 调用生成合同接口
     *
     * @param template_id    合同模板编号
     * @param contract_id    合同编号
     * @param doc_title      签署文档标题
     * @param font_size      字体大小 不传则为默认值9
     * @param font_type      字体类型 0-宋体；1-仿宋；2-黑体；3-楷体；4-微软雅黑
     * @param parameter_map  填充内容
     * @param dynamic_tables 动态表单
     */
    public String invokeGenerateContract(String template_id, String contract_id, String doc_title, String font_size, String font_type, String parameter_map, String dynamic_tables) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ template_id + contract_id ) +parameter_map))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + template_id + contract_id) + parameter_map);
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("template_id", template_id));
            params.add(new BasicNameValuePair("doc_title", doc_title));
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("font_size", font_size));
            params.add(new BasicNameValuePair("font_type", font_type));
            params.add(new BasicNameValuePair("parameter_map", parameter_map));
            params.add(new BasicNameValuePair("dynamic_tables", dynamic_tables));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfGenerateContract(), params);
    }

    /**
     * 调用签署接口（手动签模式）标准版+指定印章签署
     *
     * @param req
     * @param signature_id 印章编号(调新增签章接口返回)
     * @date: 2018年12月23日
     */
    public String invokeExtSign(ExtsignReq req, String signature_id) {
        StringBuilder sb = new StringBuilder();
        sb.append(invokeExtSign(req));
        if (!signature_id.equals("")) {
            sb.append("&signature_id=").append(signature_id);
        }
        return sb.toString();
    }

    /**
     * 调用签署接口（手动签模式）标准版+定位坐标
     *
     * @param req
     * @param position_type       定位类型0-关键字（默认）1-坐标
     * @param signature_positions 坐标json
     * @return
     * @date: 2018年12月23日
     */
    public String invokeExtSign(ExtsignReq req, String position_type, String signature_positions) {
        StringBuilder sb = new StringBuilder();
        sb.append(invokeExtSign(req));
        if (position_type.equals("1")) {
            try {
                sb.append("&position_type=").append(position_type);
                sb.append("&signature_positions=").append(URLEncoder.encode(signature_positions, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 调用签署接口（手动签模式）标准版+DIY签署校验方式
     *
     * @param req
     * @param sign_verify_way 签署校验方式 1 短信 3刷脸签署
     * @param verify_way_flag 签署校验方式选择人脸签署，签署失败之后是否切换到短信签署标志 0不切换 1切换这个参数不传默认0
     * @return
     * @date: 2018年12月23日
     */
    public String invokeExtSignDIY(ExtsignReq req, String sign_verify_way, String verify_way_flag) {
        StringBuilder sb = new StringBuilder();
        sb.append(invokeExtSign(req));
        if (!verify_way_flag.equals("")) {
            sb.append("&sign_verify_way=").append(sign_verify_way);
            sb.append("&verify_way_flag=").append(verify_way_flag);
        }
        return sb.toString();
    }

    /**
     * 调用签署接口（手动签模式）标准版(合规化方案使用)
     *
     * @param req
     * @param customer_mobile   手机号 用于短信校验
     * @param customer_name     姓名
     * @param customer_ident_no 身份证
     * @return
     * @date: 2018年12月23日
     */
    public String invokeExtSign(ExtsignReq req, String customer_mobile, String customer_name, String customer_ident_no) {
        StringBuilder sb = new StringBuilder();
        sb.append(invokeExtSign(req));
        if (!customer_mobile.equals("")) {
            //用于短信校验
            sb.append("&customer_mobile=").append(customer_mobile);
        } else {
            //用于刷脸验证，姓名和身份证需要同时传
            try {
                sb.append("&customer_name=").append(URLEncoder.encode(customer_name, "utf-8"));
                sb.append("&customer_ident_no=").append(customer_ident_no);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 调用签署接口（手动签模式）标准版
     *
     * @param req
     * @return
     */
    public String invokeExtSign(ExtsignReq req) {
        String timeStamp = HttpsUtil.getTimeStamp();
        StringBuilder sb = new StringBuilder(super.getURLOfExtSign());
        try {
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId()
                    + FddEncryptTool.md5Digest(req.getTransaction_id() + timeStamp)
                    + FddEncryptTool.sha1(super.getSecret() + req.getCustomer_id()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            sb.append("?app_id=").append(super.getAppId());
            sb.append("&v=").append(super.getVersion());
            sb.append("&timestamp=").append(timeStamp);
            sb.append("&transaction_id=").append(req.getTransaction_id());
            sb.append("&customer_id=").append(req.getCustomer_id());
            sb.append("&contract_id=").append(req.getContract_id());
            sb.append("&doc_title=").append(URLEncoder.encode(req.getDoc_title(), HttpsUtil.charset));
            sb.append("&keyword_strategy=").append(req.getKeyword_strategy());
            if (null != req.getSign_keyword()) {
                sb.append("&sign_keyword=").append(URLEncoder.encode(req.getSign_keyword(), HttpsUtil.charset));
            }
            sb.append("&return_url=").append(URLEncoder.encode(req.getReturn_url(), HttpsUtil.charset));
            sb.append("&notify_url=").append(URLEncoder.encode(req.getNotify_url(), HttpsUtil.charset));
            sb.append("&msg_digest=").append(msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 调用签署接口（自动签模式） -  标准版 + 指定印章编号签署
     *
     * @param req          签署接口基础参数
     * @param signature_id 印章编号(调新增签章接口返回)
     * @return
     */
    public String invokeExtSignAuto(ExtsignReq req, String signature_id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(req.getTransaction_id() + timeStamp) + FddEncryptTool.sha1(super.getSecret() + req.getCustomer_id()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("transaction_id", req.getTransaction_id()));
            params.add(new BasicNameValuePair("contract_id", req.getContract_id()));
            params.add(new BasicNameValuePair("customer_id", req.getCustomer_id()));
            params.add(new BasicNameValuePair("client_role", req.getClient_role()));
            params.add(new BasicNameValuePair("doc_title", req.getDoc_title()));
            params.add(new BasicNameValuePair("position_type", req.getPosition_type()));
            params.add(new BasicNameValuePair("notify_url", req.getNotify_url()));
            params.add(new BasicNameValuePair("signature_id", signature_id));
            if (req.getPosition_type().equals("0")) {
                params.add(new BasicNameValuePair("sign_keyword", req.getSign_keyword()));
                params.add(new BasicNameValuePair("keyword_strategy", req.getKeyword_strategy()));
            }
            if (req.getPosition_type().equals("1")) {
                params.add(new BasicNameValuePair("signature_positions", req.getSignature_positions()));
            }
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfExtSignAuto(), params);
    }

    /**
     * 调用签署接口（自动签模式） - 关键字定位 - DIY签章图片
     *
     * @param req         签署接口基础参数
     * @param handsignimg 章图片BASE64,需指定URI data scheme.
     * @return
     */
    public String invokeExtSignAutoDIY(ExtsignReq req, String handsignimg) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(req.getTransaction_id() + timeStamp) + FddEncryptTool.sha1(super.getSecret() + req.getCustomer_id()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("transaction_id", req.getTransaction_id()));
            params.add(new BasicNameValuePair("contract_id", req.getContract_id()));
            params.add(new BasicNameValuePair("customer_id", req.getCustomer_id()));
            params.add(new BasicNameValuePair("client_role", req.getClient_role()));
            params.add(new BasicNameValuePair("doc_title", req.getDoc_title()));
            params.add(new BasicNameValuePair("position_type", req.getPosition_type()));
            params.add(new BasicNameValuePair("handsignimg", handsignimg));
            params.add(new BasicNameValuePair("notify_url", req.getNotify_url()));
            if (req.getPosition_type().equals("0")) {
                params.add(new BasicNameValuePair("sign_keyword", req.getSign_keyword()));
                params.add(new BasicNameValuePair("keyword_strategy", req.getKeyword_strategy()));
            }
            if (req.getPosition_type().equals("1")) {
                params.add(new BasicNameValuePair("signature_positions", req.getSignature_positions()));
            }
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfExtSignAuto(), params);
    }

    /**
     * 调用签署接口（自动签模式）标准版
     *
     * @param req
     * @return
     */
    public String invokeExtSignAuto(ExtsignReq req) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(req.getTransaction_id() + timeStamp) + FddEncryptTool.sha1(super.getSecret() + req.getCustomer_id()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("transaction_id", req.getTransaction_id()));
            params.add(new BasicNameValuePair("contract_id", req.getContract_id()));
            params.add(new BasicNameValuePair("customer_id", req.getCustomer_id()));
            params.add(new BasicNameValuePair("client_role", req.getClient_role()));
            params.add(new BasicNameValuePair("doc_title", req.getDoc_title()));
            params.add(new BasicNameValuePair("position_type", req.getPosition_type()));
            params.add(new BasicNameValuePair("notify_url", req.getNotify_url()));
            if (req.getPosition_type().equals("0")) {
                params.add(new BasicNameValuePair("sign_keyword", req.getSign_keyword()));
                params.add(new BasicNameValuePair("keyword_strategy", req.getKeyword_strategy()));
            }
            if (req.getPosition_type().equals("1")) {
                params.add(new BasicNameValuePair("signature_positions", req.getSignature_positions()));
            }
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfExtSignAuto(), params);
    }

    /**
     * 调用客户签署状态查询接口
     *
     * @param contract_id 合同编号
     * @param customer_id 客户编号
     */
    @Deprecated
    public String invokeQuerySignStatus(String contract_id, String customer_id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id+customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfQuerySignStatus(), params);
    }

    /**
     * 查询用户签署结果接口
     *
     * @param request
     * @return
     */
    public String invokeQuerySignResult(SignResultQueryRequest request) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest = MsgDigestUtil.getCheckMsgDigest(super.getAppId(), super.getSecret(), timeStamp, request);
            params.add(new BasicNameValuePair("contract_id", request.getContractId()));
            params.add(new BasicNameValuePair("customer_id", request.getCustomerId()));
            params.add(new BasicNameValuePair("transaction_id", request.getTransactionId()));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfQuerySignResult(), params);
    }

    /**
     * 调用合同归档
     *
     * @param contract_id 合同编号
     */
    public String invokeContractFilling(String contract_id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfContractFilling(), params);
    }

}
