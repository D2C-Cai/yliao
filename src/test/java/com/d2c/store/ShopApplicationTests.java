package com.d2c.store;

import com.d2c.store.common.sdk.fadada.FadadaClient;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.member.model.AccountDO;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * VM options: -Dspring.profiles.active=dev -Des.set.netty.runtime.available.processors=false
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StoreApplication.class})
public class ShopApplicationTests {

    @Autowired
    private FadadaClient fadadaClient;

    @Test
    public void contextLoads() {
        /**
         * 测试数据
         *
         *  15CBE6F987621E865C6A194A16C6770E 客户编号
         *  20190403151238498570518 客户证书编号
         *  sign_id 4342920
         *  template01
         *  contract_01
         *  signImg "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"
         */
        MemberDO memberDO = new MemberDO();
        memberDO.setCustomerId("15CBE6F987621E865C6A194A16C6770E");
        memberDO.setNickname("路人甲");
        memberDO.setIdentity("330327199001012222");
        memberDO.setAccount("13197677777");
        List<OrderItemDO> list = new ArrayList<>();
        OrderItemDO oi1 = new OrderItemDO();
        oi1.setProductName("商品1");
        oi1.setProductPrice(new BigDecimal(1));
        oi1.setQuantity(1);
        OrderItemDO oi2 = new OrderItemDO();
        oi2.setProductName("商品2");
        oi2.setProductPrice(new BigDecimal(2));
        oi2.setQuantity(2);
        list.add(oi1);
        list.add(oi2);
        OrderDO orderDO = new OrderDO();
        orderDO.setPayAmount(new BigDecimal(5));
        orderDO.setProvince("浙江");
        orderDO.setCity("杭州");
        orderDO.setDistrict("上城区");
        orderDO.setAddress("莲花峰路12号");
        orderDO.setMobile("13197677777");
        AccountDO accountDO = new AccountDO();
        accountDO.setOauthAmount(new BigDecimal(6));
        accountDO.setDeadline(new Date());
        P2PDO p2PDO = new P2PDO();
        p2PDO.setName("杭州迪尔西");
        p2PDO.setCreditCode("测试111111111111111111111");
        p2PDO.setAddress("莲花峰路");
        p2PDO.setMobile("123456789");
        p2PDO.setCustomerId("DB8C8351459F2DEE51A1DEEDB221BBDC2");
        //generateContract("template01","contract_01","测试合同",list,orderDO,accountDO,memberDO,p2PDO);
        fadadaClient.extSignAuto("15CBE6F987621E865C6A194A16C6770E", "transation111", "contract_01", "测试合同");
        p2PDO.setCreditCodeFile("https://img1.360buyimg.com/imgb/s250x250_jfs/t27610/66/981425374/258674/92bbf014/5bbf1420N3362755f.jpg");
        p2PDO.setPowerAttorneyFile("https://img1.360buyimg.com/imgb/s250x250_jfs/t27610/66/981425374/258674/92bbf014/5bbf1420N3362755f.jpg");
        p2PDO.setLegalName("lin");
        p2PDO.setIdentity("330327198905051111");
        fadadaClient.companyDeposit(p2PDO, "12", "12");
    }

}

