package com.d2c.store.common.sdk.fadada.util.http;

import com.d2c.store.common.sdk.fadada.util.config.ProxyHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;

public class ClientMultipartFormPost {

    public static String doPost(String url, Map<String, String> params, Map<String, File> files) {
        return postFromHttpClient(url, params, files, 15000, 35000);
    }

    public static String postFromHttpClient(String url, Map<String, String> params, Map<String, File> files, int connect_time, int timeout) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        MultipartEntity fileEntity = new MultipartEntity();
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
//				// 请求超时
//				 httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connect_time);
//				// 读取超时
//				 httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
//				// 设置参数
//
            if (files != null && !files.isEmpty()) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    if (null != entry.getValue()) {
                        FileBody fileBody = new FileBody(entry.getValue());
                        fileEntity.addPart(entry.getKey(), fileBody);
                    }
                }
            }
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (StringUtils.isNotBlank(entry.getValue())) {
                        StringBody contentBody = new StringBody(entry.getValue(),
                                "text/plain", Charset.forName("UTF-8"));
                        fileEntity.addPart(entry.getKey(), contentBody);
                    }
                }
            }
            httpPost.setEntity(fileEntity);
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
                return EntityUtils.toString(entity, "UTF-8");
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

}
