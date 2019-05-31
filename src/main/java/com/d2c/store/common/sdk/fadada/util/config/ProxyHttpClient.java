package com.d2c.store.common.sdk.fadada.util.config;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;

public class ProxyHttpClient {

    public static HttpClient getProxyHttpClient(HttpClient httpClient) {
        String proxyHost = SystemConfig.getProxyHost();
        String proxyPort = SystemConfig.getProxyPort();
        String proxyFlag = SystemConfig.getProxyFlag();
        if (proxyFlag != null && "ON".equalsIgnoreCase(proxyFlag)) {
            HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort), "http");
            httpClient.getParams().setParameter("http.route.default-proxy", proxy);
        }
        return httpClient;
    }

}
