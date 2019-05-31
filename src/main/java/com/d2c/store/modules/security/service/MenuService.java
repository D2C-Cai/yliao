package com.d2c.store.modules.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.store.modules.security.model.MenuDO;

import java.util.List;

/**
 * @author BaiCai
 */
public interface MenuService extends IService<MenuDO> {

    List<MenuDO> findByRoleId(List<Long> roleIds);

}
