package com.d2c.store.modules.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.store.api.support.OauthBean;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.member.model.MemberDO;

import java.util.Date;

/**
 * @author BaiCai
 */
public interface MemberService extends IService<MemberDO> {

    MemberDO findLogin(String account, Long p2pId);

    MemberDO doOauth(OauthBean oauthBean, P2PDO p2pDO, String loginIp);

    MemberDO doLogin(MemberDO member, String loginIp, String accessToken, Date accessExpired);

    boolean doLogout(String account);

    boolean updatePassword(String account, String password);

}
