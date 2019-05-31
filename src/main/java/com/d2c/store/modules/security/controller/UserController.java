package com.d2c.store.modules.security.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.PageModel;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.api.base.extension.BaseExcelCtrl;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.common.utils.RequestUtil;
import com.d2c.store.config.security.context.LoginUserHolder;
import com.d2c.store.modules.security.model.UserDO;
import com.d2c.store.modules.security.query.UserQuery;
import com.d2c.store.modules.security.query.UserRoleQuery;
import com.d2c.store.modules.security.service.UserRoleService;
import com.d2c.store.modules.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author BaiCai
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/back/user")
public class UserController extends BaseExcelCtrl<UserDO, UserQuery> {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private LoginUserHolder loginUserHolder;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @ApiOperation(value = "用户注册")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R<UserDO> insert(@RequestBody UserDO user) {
        Asserts.notNull(ResultCode.REQUEST_PARAM_NULL, user.getUsername(), user.getPassword(), user.getStatus());
        UserDO old = userService.findByUsername(user.getUsername());
        Asserts.isNull("该账号已存在", old);
        user.setRegisterIp(RequestUtil.getRequestIp(request));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return super.insert(user);
    }

    @Override
    @ApiOperation(value = "用户更新")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<UserDO> update(@RequestBody UserDO user) {
        Asserts.notNull(ResultCode.REQUEST_PARAM_NULL, user);
        UserDO old = userService.getById(user.getId());
        UserDO entity = new UserDO();
        entity.setId(user.getId());
        entity.setStatus(user.getStatus());
        entity.setP2pId(user.getP2pId());
        entity.setSupplierId(user.getSupplierId());
        if (StrUtil.isNotBlank(user.getPassword())) {
            entity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        redisTemplate.delete("USER::session:" + old.getUsername());
        return super.update(entity);
    }

    @Override
    @ApiOperation(value = "通过ID获取数据")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public R<UserDO> select(@PathVariable Long id) {
        return super.select(id);
    }

    @Override
    @ApiOperation(value = "通过ID删除数据")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(Long[] ids) {
        UserRoleQuery query = new UserRoleQuery();
        query.setUserIds(ids);
        userRoleService.remove(QueryUtil.buildWrapper(query));
        return super.delete(ids);
    }

    @Override
    @ApiOperation(value = "分页查询数据")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/select/page", method = RequestMethod.POST)
    public R<Page<UserDO>> selectPage(PageModel page, UserQuery query) {
        return super.selectPage(page, query);
    }

    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/reset/password", method = RequestMethod.POST)
    public R update(String password) {
        UserDetails user = loginUserHolder.getLoginUser();
        UserDO old = userService.findByUsername(user.getUsername());
        UserDO entity = new UserDO();
        entity.setId(old.getId());
        entity.setPassword(new BCryptPasswordEncoder().encode(password));
        entity.setAccessToken("");
        redisTemplate.delete("USER::session:" + old.getUsername());
        super.update(entity);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}
