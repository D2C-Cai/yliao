package com.d2c.store.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.store.api.base.BaseController;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.PageModel;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.modules.member.model.AddressDO;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.member.query.AddressQuery;
import com.d2c.store.modules.member.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cai
 */
@Api(description = "地址业务")
@RestController
@RequestMapping("/api/address")
public class C_AddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<Page<AddressDO>> list(PageModel page) {
        AddressQuery query = new AddressQuery();
        query.setMemberId(loginMemberHolder.getLoginId());
        page.setDesc("defaults", "create_date");
        Page<AddressDO> pager = (Page<AddressDO>) addressService.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<AddressDO> select(@PathVariable Long id) {
        AddressQuery query = new AddressQuery();
        query.setId(id);
        query.setMemberId(loginMemberHolder.getLoginId());
        AddressDO address = addressService.getOne(QueryUtil.buildWrapper(query));
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, address);
        return Response.restResult(address, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<AddressDO> insert(@RequestBody AddressDO address) {
        MemberDO member = loginMemberHolder.getLoginMember();
        address.setMemberId(member.getId());
        address.setMemberAccount(member.getAccount());
        addressService.save(address);
        return Response.restResult(address, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<AddressDO> update(@RequestBody AddressDO address) {
        MemberDO member = loginMemberHolder.getLoginMember();
        Asserts.eq(address.getMemberId(), member.getId(), "您不是本人");
        if (address.getDefaults() == 1) {
            AddressQuery query = new AddressQuery();
            query.setMemberId(member.getId());
            AddressDO entity = new AddressDO();
            entity.setDefaults(0);
            addressService.update(entity, QueryUtil.buildWrapper(query));
        }
        addressService.updateById(address);
        return Response.restResult(addressService.getById(address.getId()), ResultCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public R delete(@PathVariable Long id) {
        AddressDO address = addressService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, address);
        MemberDO member = loginMemberHolder.getLoginMember();
        Asserts.eq(address.getMemberId(), member.getId(), "您不是本人");
        addressService.removeById(id);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
