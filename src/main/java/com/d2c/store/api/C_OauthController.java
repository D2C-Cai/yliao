package com.d2c.store.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.store.api.base.BaseController;
import com.d2c.store.api.support.OauthBean;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.utils.RequestUtil;
import com.d2c.store.config.security.constant.SecurityConstant;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.core.service.P2PService;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.member.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Cai
 */
@Api(description = "授权业务")
@RestController
@RequestMapping("/api/oauth")
public class C_OauthController extends BaseController {

    @Autowired
    private P2PService p2PService;
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "授权入口")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public R<String> oauth(OauthBean oauthBean) {
        P2PDO p2pDO = p2PService.getById(oauthBean.getAppId());
        Asserts.notNull("appId不正确，请仔细检查", p2pDO);
        String sign = oauthBean.getSign();
        String content = this.getSignContent((Map<String, String>) JSONObject.toJSON(oauthBean));
        String signature = DigestUtil.md5Hex(content + p2pDO.getSecret());
        Asserts.eq(sign, signature, "签名不正确，请仔细检查");
        MemberDO member = memberService.doOauth(oauthBean, p2pDO, RequestUtil.getRequestIp(request));
        Date accessExpired = DateUtil.offsetDay(new Date(), 7).toJdkDate();
        String accessToken = SecurityConstant.TOKEN_PREFIX + Jwts.builder()
                .setSubject(member.getAccount())
                .claim(SecurityConstant.AUTHORITIES, member.getAccountInfo().getP2pId())
                .setExpiration(accessExpired)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();
        member = memberService.doLogin(member, RequestUtil.getRequestIp(request), accessToken, accessExpired);
        return Response.restResult(accessToken, ResultCode.SUCCESS);
    }

    private String getSignContent(Map<String, String> params) {
        if (params == null) {
            return null;
        } else {
            params.remove("sign");
            StringBuffer content = new StringBuffer();
            List<String> keys = new ArrayList(params.keySet());
            Collections.sort(keys);
            for (int i = 0; i < keys.size(); ++i) {
                String key = String.valueOf(keys.get(i));
                String value = String.valueOf(params.get(key));
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            }
            return content.toString();
        }
    }

}
