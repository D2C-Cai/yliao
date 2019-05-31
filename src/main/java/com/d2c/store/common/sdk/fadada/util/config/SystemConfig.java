package com.d2c.store.common.sdk.fadada.util.config;

import org.apache.commons.lang3.StringUtils;

public class SystemConfig {

    private static String proxyFlag;
    private static String proxyHost;
    private static String proxyPort;

    public SystemConfig() {
    }

    public static String getProxyFlag() {
        if (StringUtils.isNotBlank(proxyFlag)) {
            return proxyFlag;
        }
        return "";
    }

    public static void setProxyFlag(String proxyFlag) {
        SystemConfig.proxyFlag = proxyFlag;
    }

    public static String getProxyHost() {
        if (StringUtils.isNotBlank(proxyHost)) {
            return proxyHost;
        }
        return "";
    }

    public static void setProxyHost(String proxyHost) {
        SystemConfig.proxyHost = proxyHost;
    }

    public static String getProxyPort() {
        if (StringUtils.isNotBlank(proxyPort)) {
            return proxyPort;
        }
        return "";
    }

    public static void setProxyPort(String proxyPort) {
        SystemConfig.proxyPort = proxyPort;
    }

    public static void main(String[] args) {
        System.out.println(getProxyPort());
    }

}
