package com.d2c.store.modules.core.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.store.api.support.OauthBean;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.PageModel;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.api.base.BaseCtrl;
import com.d2c.store.common.api.constant.PrefixConstant;
import com.d2c.store.common.sdk.fadada.FadadaClient;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.common.utils.RequestUtil;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.core.query.P2PQuery;
import com.d2c.store.modules.core.service.P2PService;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.member.service.MemberService;
import com.d2c.store.modules.security.model.UserDO;
import com.d2c.store.modules.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author BaiCai
 */
@Api(description = "P2P平台管理")
@RestController
@RequestMapping("/back/p2p")
public class P2PController extends BaseCtrl<P2PDO, P2PQuery> {

    @Autowired
    private UserService userService;
    @Autowired
    private P2PService p2PService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private FadadaClient fadadaClient;

    @ApiOperation(value = "P2P查询数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R<Page<P2PDO>> list(PageModel page, P2PQuery query) {
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        query.setId(user.getP2pId());
        Page<P2PDO> pager = (Page<P2PDO>) service.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "P2P更新数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R<P2PDO> save(@RequestBody P2PDO entity) {
        Asserts.notNull(ResultCode.REQUEST_PARAM_NULL, entity);
        UserDO user = userService.findByUsername(loginUserHolder.getUsername());
        entity.setId(user.getP2pId());
        service.updateById(entity);
        return Response.restResult(service.getById(entity.getId()), ResultCode.SUCCESS);
    }

    @ApiOperation(value = "法大大注册和认证")
    @RequestMapping(value = "/fadada/apply", method = RequestMethod.POST)
    public R<P2PDO> apply(String id) {
        P2PDO p2PDO = this.service.getById(Long.valueOf(id));
        Asserts.notNull("企业资料不全", p2PDO.getName(), p2PDO.getCreditCode(), p2PDO.getCreditCodeFile(), p2PDO.getPowerAttorneyFile(), p2PDO.getLegalName(), p2PDO.getIdentity(), p2PDO.getMobile());
        // 法人注册
        if (p2PDO.getLegalCustomerId() == null) {
            MemberDO memberDO = new MemberDO();
            memberDO.setNickname(p2PDO.getLegalName());
            memberDO.setIdentity(p2PDO.getIdentity());
            memberDO.setAccount(p2PDO.getMobile());
            String legalCustomerId = fadadaClient.registerAccount(PrefixConstant.FDD_LEGAL_ACCOUNT_PREFIX + id, "1");
            p2PDO.setLegalCustomerId(legalCustomerId);
        }
        if (p2PDO.getCustomerId() == null) {
            // 企业注册
            String customerId = fadadaClient.registerAccount(PrefixConstant.FDD_COM_ACCOUNT_PREFIX + id, "2");
            p2PDO.setCustomerId(customerId);
        }
        // 企业认证
        String evidenceNo = fadadaClient.companyDeposit(p2PDO, PrefixConstant.FDD_COM_TRANSATION_PREFIX + id, PrefixConstant.FDD_COM_APPLY_PREFIX + id);
        p2PDO.setEvidenceNo(evidenceNo);
        // 证书申请
        fadadaClient.applyClinetNumcert(p2PDO.getCustomerId(), evidenceNo);
        service.updateById(p2PDO);
        return Response.restResult(p2PDO, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "法大大签章获取")
    @RequestMapping(value = "/fadada/sign", method = RequestMethod.POST)
    public R<P2PDO> getSign(String id) {
        P2PDO p2pDO = this.service.getById(Long.valueOf(id));
        Asserts.notNull("尚未进行企业认证", p2pDO.getCustomerId(), p2pDO.getName());
        String signImg = fadadaClient.customSignature(p2pDO.getCustomerId(), p2pDO.getName());
        String signId = fadadaClient.addSignature(p2pDO.getCustomerId(), signImg);
        p2pDO.setSignId(signId);
        service.updateById(p2pDO);
        return Response.restResult(p2pDO, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "导入会员信息")
    @RequestMapping(value = "/import/member", method = RequestMethod.POST)
    public R importMember(HttpServletRequest request, String appId) throws IOException {
        P2PDO p2pDO = p2PService.getById(appId);
        Asserts.notNull("appId不正确，无法导入会员信息", p2pDO);
        File file = RequestUtil.getRequestFile(request);
        String ip = RequestUtil.getRequestIp(request);
        List<OauthBean> list = ExcelImportUtil.importExcel(file, OauthBean.class, new ImportParams());
        for (OauthBean oauthBean : list) {
            memberService.doOauth(oauthBean, p2pDO, ip);
        }
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
