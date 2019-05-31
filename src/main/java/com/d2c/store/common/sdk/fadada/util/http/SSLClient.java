/**
 * 版权所有：深圳法大大网络科技有限公司
 * Copyright 2015 fadada.com Inc.
 * All right reserved.
 * ====================================================
 * 文件名称: SSLClient.java
 * 修订记录：
 * No    日期				作者(操作:具体内容)
 * 1.    Jun 2, 2015			Administrator(创建:创建文件)
 * ====================================================
 * 类描述：(说明未实现或其它不应生成javadoc的内容)
 */
package com.d2c.store.common.sdk.fadada.util.http;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <h3>概要:</h3>
 * 用于进行Https请求的HttpClient
 * <br>
 * <h3>功能:</h3>
 * <h3>履历:</h3>
 * <ol>
 * <li>2015年12月17日[zhouxw] 新建</li>
 * </ol>
 */
public class SSLClient extends DefaultHttpClient {

    public SSLClient() throws Exception {
        super();
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }

}

