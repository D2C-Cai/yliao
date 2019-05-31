package com.d2c.store.modules.member.service.impl;

import com.d2c.store.common.api.base.BaseService;
import com.d2c.store.modules.member.mapper.AccountMapper;
import com.d2c.store.modules.member.model.AccountDO;
import com.d2c.store.modules.member.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class AccountServiceImpl extends BaseService<AccountMapper, AccountDO> implements AccountService {

}
