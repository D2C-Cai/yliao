package com.d2c.store.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.store.api.base.BaseController;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.core.service.P2PService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "P2P平台业务")
@RestController
@RequestMapping("/api/p2p")
public class C_P2PController extends BaseController {

    @Autowired
    private P2PService p2PService;

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<P2PDO> select(@PathVariable String id) {
        P2PDO p2p = p2PService.getById(Long.valueOf(id));
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, p2p);
        p2p.setSecret(null);
        p2p.setCreditCode(null);
        p2p.setCreditCodeFile(null);
        p2p.setEvidenceNo(null);
        p2p.setIdentity(null);
        p2p.setLegalName(null);
        p2p.setPowerAttorneyFile(null);
        return Response.restResult(p2p, ResultCode.SUCCESS);
    }

}
