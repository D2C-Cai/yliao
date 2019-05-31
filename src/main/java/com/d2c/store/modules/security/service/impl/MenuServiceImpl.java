package com.d2c.store.modules.security.service.impl;

import com.d2c.store.common.api.base.BaseService;
import com.d2c.store.modules.security.mapper.MenuMapper;
import com.d2c.store.modules.security.model.MenuDO;
import com.d2c.store.modules.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BaiCai
 */
@Service
public class MenuServiceImpl extends BaseService<MenuMapper, MenuDO> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuDO> findByRoleId(List<Long> roleIds) {
        return menuMapper.findByRoleId(roleIds);
    }

}
