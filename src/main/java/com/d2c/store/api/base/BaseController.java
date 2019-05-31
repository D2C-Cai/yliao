package com.d2c.store.api.base;

import com.d2c.store.config.security.context.LoginMemberHolder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cai
 */
public abstract class BaseController {

    @Autowired
    public HttpServletRequest request;
    @Autowired
    public LoginMemberHolder loginMemberHolder;

}
