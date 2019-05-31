package com.d2c.store.config.security.context;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.utils.SpringUtil;
import com.d2c.store.config.security.constant.SecurityConstant;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Cai
 */
@Controller
public class LoginMemberHolder {

    @Autowired
    private HttpServletRequest request;

    public MemberDO getLoginMember() {
        String accessToken = request.getHeader(SecurityConstant.ACCESS_TOKEN);
        if (StrUtil.isBlank(accessToken)) throw new ApiException(ResultCode.LOGIN_EXPIRED);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(accessToken.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();
            String account = claims.getSubject();
            Long p2pId = Long.valueOf(claims.get(SecurityConstant.AUTHORITIES).toString());
            // Redis获取用户session
            MemberDO member = SpringUtil.getBean(MemberService.class).findLogin(account, p2pId);
            Asserts.notNull(ResultCode.LOGIN_EXPIRED, member);
            Asserts.notNull(ResultCode.ACCESS_DENIED, member.getAccountInfo());
            // 验证token是否一致
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(accessToken, member.getAccessToken())) {
                throw new ApiException(ResultCode.LOGIN_EXPIRED);
            }
            // 验证token是否过期
            if (new Date().after(member.getAccessExpired())) {
                throw new ApiException(ResultCode.LOGIN_EXPIRED);
            }
            // 平台ID不符合，无权访问
            Asserts.eq(member.getAccountInfo().getP2pId(), p2pId, ResultCode.ACCESS_DENIED);
            return member;
        } catch (Exception e) {
            throw new ApiException(ResultCode.LOGIN_EXPIRED);
        }
    }

    public Long getLoginId() {
        return this.getLoginMember().getId();
    }

    public String getLoginAccount() {
        return this.getLoginMember().getAccount();
    }

    public Long getP2pId() {
        return this.getLoginMember().getAccountInfo().getP2pId();
    }

}
