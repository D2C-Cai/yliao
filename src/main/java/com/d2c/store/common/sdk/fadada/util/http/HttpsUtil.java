/**
 * 版权所有：深圳法大大网络科技有限公司
 * Copyright 2015 fadada.com Inc.
 * All right reserved.
 * ====================================================
 * 文件名称: FddHttpUtil.java
 * 修订记录：
 * No    日期				作者(操作:具体内容)
 * 1.    Dec 18, 2015			Mocuishle(创建:创建文件)
 * ====================================================
 * 类描述：(说明未实现或其它不应生成javadoc的内容)
 */
package com.d2c.store.common.sdk.fadada.util.http;

import com.d2c.store.common.sdk.fadada.util.config.ProxyHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HttpsUtil {

    public static final String charset = "UTF-8";

    /* ===================================doPost============================================== */

    /**
     * @param url
     * @param params
     * @return 链接响应内容
     * @author: zhouxw
     * @date: 2017年3月16日
     */
    public static String doPost(String url, List<NameValuePair> params) {
        return doPost(url, params, 15000, 35000);
    }

    /**
     * 申请CA POST函数
     *
     * @param url
     * @param params
     * @return
     * @author: zhouxw
     * @date: 2017年7月10日
     */
    public static String doCaPost(String url, List<NameValuePair> params) {
        return doPost(url, params, 5000, 180000);
    }

    /**
     * @param url          地址
     * @param params       参数
     * @param connect_time 链接尝试时间
     * @param timeout      超时时间
     * @return 链接响应内容
     * @author: zhouxw
     * @date: 2017年7月10日
     */
    public static String doPost(String url, List<NameValuePair> params, int connect_time, int timeout) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            // 请求超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connect_time);
            // 读取超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            // 设置参数
            if (null != params && params.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
                httpPost.setEntity(entity);
            }
            ProxyHttpClient.getProxyHttpClient(httpClient);
            HttpResponse response = httpClient.execute(httpPost);
            if (response == null) {
                throw new RuntimeException("HttpResponse is null.");
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (null == entity) {
                    throw new RuntimeException("HttpEntity is null.");
                }
                return EntityUtils.toString(entity, charset);
            } else {
                throw new RuntimeException("connect fail. http_status:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * TODO post&sendFile
     *
     * @param url
     * @param entity
     * @return
     * @author: zhouxw
     * @date: 2017年5月5日
     */
    public static String doPost(String url, HttpEntity entity) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            // 请求超时
            // httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
            // 读取超时
            // httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
            // 设置参数
            httpPost.setEntity(entity);
            ProxyHttpClient.getProxyHttpClient(httpClient);
            HttpResponse response = httpClient.execute(httpPost);
            if (response == null) {
                throw new RuntimeException("http_response is null.");
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity respEtity = response.getEntity();
                if (null == entity) {
                    throw new RuntimeException("response http_entity is null.");
                }
                return EntityUtils.toString(respEtity, charset);
            } else {
                throw new RuntimeException("connect fail. http_status:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将返回流写文件
     *
     * @param path   保存地址示例：D:/pdf/uuid.pdf
     * @param url
     * @param params
     * @return 返回null表示下载成功；返回str表示失败，str为服务端返回的json格式报文
     * @author: zhouxw
     * @date: 2017年3月16日
     */
    public static String doPostDownload(String path, String url, List<NameValuePair> params) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            // 设置参数
            if (null != params && params.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
                httpPost.setEntity(entity);
            }
            ProxyHttpClient.getProxyHttpClient(httpClient);
            HttpResponse response = httpClient.execute(httpPost);
            if (response == null) {
                throw new RuntimeException("HttpResponse is null.");
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (null == entity) {
                    throw new RuntimeException("HttpEntity is null.");
                }
                // 下载成功返回文件流
                if (!"application/zip".equals(response.getEntity().getContentType().getValue()) && !"application/pdf".equals(response.getEntity().getContentType().getValue())) {
                    // 下载失败返回json格式报文
                    return EntityUtils.toString(entity, charset);
                }
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                BufferedOutputStream bw = null;
                try {
                    File f = new File(path); // 创建文件对象
                    if (f.exists()) { // 重复时候替换掉
                        f.delete();
                    }
                    if (!f.getParentFile().exists()) { // 创建文件路径
                        f.getParentFile().mkdirs();
                    }
                    bw = new BufferedOutputStream(new FileOutputStream(path));// 写入文件
                    bw.write(result);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                throw new RuntimeException("connect fail. http_status:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* ===================================doPost============================================== */

    /**
     * TODO 获取当前时间戳
     *
     * @return 当前时间：'yyyyMMddHHmmss'格式
     * @author: zhouxw
     * @date: 2017年3月16日
     */
    public static String getTimeStamp() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(ts);
    }

}
