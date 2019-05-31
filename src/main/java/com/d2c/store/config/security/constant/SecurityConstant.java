package com.d2c.store.config.security.constant;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author BaiCai
 */
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_PREFIX = "D2C-";
    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = DigestUtil.md5Hex("store");
    /**
     * token参数头
     */
    String ACCESS_TOKEN = "accessToken";
    /**
     * author参数头
     */
    String AUTHORITIES = "authorities";

}
