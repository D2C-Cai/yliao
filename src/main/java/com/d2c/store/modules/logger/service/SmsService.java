package com.d2c.store.modules.logger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d2c.store.modules.logger.model.SmsDO;

/**
 * @author BaiCai
 */
public interface SmsService extends IService<SmsDO> {

    boolean doSend(String mobile, String ip);

    boolean doCheck(String mobile, String code);

}
