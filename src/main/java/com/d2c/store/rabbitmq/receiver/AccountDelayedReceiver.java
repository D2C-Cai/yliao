package com.d2c.store.rabbitmq.receiver;

import cn.hutool.core.date.DateUtil;
import com.d2c.store.config.rabbitmq.RabbitmqConfig;
import com.d2c.store.modules.member.model.AccountDO;
import com.d2c.store.modules.member.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Cai
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitmqConfig.ACCOUNT_QUEUE_NAME)
public class AccountDelayedReceiver {

    @Autowired
    private AccountService accountService;

    @RabbitHandler
    public void process(String msg) {
        log.info("钱包账户ID：" + msg + " 接收时间：" + DateUtil.formatDateTime(new Date()));
        try {
            this.doSomething(msg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void doSomething(String msg) {
        // 账户清零
        AccountDO account = new AccountDO();
        account.setId(Long.valueOf(msg));
        account.setOauthAmount(BigDecimal.ZERO);
        accountService.updateById(account);
    }

}


