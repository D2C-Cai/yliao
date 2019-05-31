package com.d2c.store.common.sdk.fadada;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.sdk.fadada.client.FddClientBase;
import com.d2c.store.common.sdk.fadada.client.FddClientExtra;
import com.d2c.store.common.sdk.fadada.client.auth.ApplyClientNumCert;
import com.d2c.store.common.sdk.fadada.client.auth.CompanyDeposit;
import com.d2c.store.common.sdk.fadada.client.auth.PersonDeposit;
import com.d2c.store.common.sdk.fadada.client.auth.model.CompanyDepositReq;
import com.d2c.store.common.sdk.fadada.client.auth.model.CompanyPrincipalVerifiedMsg;
import com.d2c.store.common.sdk.fadada.client.auth.model.PersonDepositReq;
import com.d2c.store.common.sdk.fadada.client.auth.model.PublicSecurityEssentialFactor;
import com.d2c.store.common.sdk.fadada.client.request.ExtsignReq;
import com.d2c.store.modules.core.model.P2PDO;
import com.d2c.store.modules.member.model.AccountDO;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.order.model.OrderDO;
import com.d2c.store.modules.order.model.OrderItemDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * 法大大业务
 */
@Component
public class FadadaClient {

    // 测试用的模板ID
    public static String TEMPLATE_ID;
    // 法大大服务器地址
    private static String HOST;
    // APPID
    private static String APP_ID;
    // APPSECRET
    private static String APP_SECRET;
    // 法大大回调地址
    private static String NOTIFY_URL;
    // 版本号
    private static String V = "2.0";
    // 图片域名
    private static String PIC_BASE = "http://s.yliao.com";
    // 图片目录
    private static String PIC_DIR = "/mnt/yliao/";
//    public static void main(String[] args) {
//        FadadaClient client = new FadadaClient();
//        client.setHOST("https://extapi.fadada.com/api2/");
//        client.setAPP_ID("001202");
//        client.setAPP_SECRET("T2iSwnSQdJOgMUjqG7LPskeb");
//        client.uploadtemplate("TEMPLATE_KS_1", new File("C:\\Users\\Lain\\Desktop\\合同模板-可溯金融.pdf"));
//    }

    /**
     * 注册账号
     *
     * @param open_id      open_id      用户在接入方的唯一标识（必填）
     * @param account_type 账号类型（必填1个人，2企业）
     * @return 返回法大大客户编号 customerId
     */
    public String registerAccount(String open_id, String account_type) {
        FddClientBase clientBase = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String result = clientBase.invokeregisterAccount(open_id, account_type);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1", response.getString("msg"));
        return response.getString("data");
    }

    /**
     * 个人实名认证
     *
     * @param memberDO 会员信息
     */
    public String personDeposit(MemberDO memberDO, String applyNum) {
        PersonDeposit personDeposit = new PersonDeposit(APP_ID, APP_SECRET, V, HOST);
        PersonDepositReq req = new PersonDepositReq();
        req.setCustomer_id(memberDO.getCustomerId()); //客户编号
        /**=======存证相关===========*/
        req.setPreservation_name("债券合同"); //存证名称
        req.setPreservation_data_provider(memberDO.getNickname());//存证数据提供方
        req.setName(memberDO.getNickname());//姓名
        /**=======证件相关===========*/
        req.setDocument_type("1");//证件类型默认是1：身份证
        req.setIdcard(memberDO.getIdentity());//证件号
        req.setMobile(memberDO.getAccount());//手机号
        req.setVerified_time(DateUtil.format(new Date(), "yyyyMMddHHmmss"));//实名时间
        req.setVerified_type("1");//实名存证类型
        PublicSecurityEssentialFactor public_security_essential_factor = new
                PublicSecurityEssentialFactor();
        //1:公安部二要素(姓名+身份证);
        public_security_essential_factor.setApply_num(applyNum);//申请编号
        req.setPublic_security_essential_factor(public_security_essential_factor);//verified_type =1 公安部二要素
        String result = personDeposit.invokePersonDeposit(req);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1", response.getString("msg"));
        return response.getString("data");
    }

    /**
     * 企业信息实名存证
     *
     * @param p2PDO          p2p企业
     * @param transaction_id 业务号
     * @param apply_num      申请号
     */
    public String companyDeposit(P2PDO p2PDO, String transaction_id, String apply_num) {
        CompanyDeposit companyDeposit = new CompanyDeposit(APP_ID, APP_SECRET, V, HOST);
        CompanyDepositReq req = new CompanyDepositReq();
        req.setCustomer_id(p2PDO.getCustomerId());
        /**=======存证相关===========*/
        req.setPreservation_name("债券合同");//存证名称
        req.setPreservation_data_provider(p2PDO.getCompanyName());//存证数据提供方
        req.setCompany_name(p2PDO.getCompanyName());//企业名称
        /**=======证件相关===========*/
        req.setDocument_type("1"); //证件类型1:三证合一2：旧版营业执照
        req.setCredit_code(p2PDO.getCreditCode()); //统一社会信用代码document_type =1 时必填
        String codefile = getLocalFilePath(p2PDO.getCreditCodeFile());
        req.setCredit_code_file(new File(codefile)); //统一社会信用代码电子版
        req.setVerified_time(DateUtil.format(new Date(), "yyyyMMddHHmmss"));//实名时间
        /**=======认证方式相关===========*/
        req.setVerified_mode("1");//实名认证方式1:授权委托书2:银行对公打款
        //verifiedMode =1 必填
        String powerAttorneyfile = getLocalFilePath(p2PDO.getCreditCodeFile());
        req.setPower_attorney_file(new File(powerAttorneyfile));//调资源维护接口返回verifiedMode =1 必填
        req.setCompany_principal_type("1");//企业负责人身份:1.法人，2 代理人
        /**=======法人信息===========*/
        req.setLegal_name(p2PDO.getLegalName());//法人姓名
        req.setLegal_idcard(p2PDO.getIdentity());//法人身份证号
        req.setTransaction_id(transaction_id);//交易号
        /**=======企业负责人实名存证信息===========*/
        CompanyPrincipalVerifiedMsg msg = new CompanyPrincipalVerifiedMsg();
        msg.setCustomer_id(p2PDO.getCustomerId());//企业负责人客户编号
        //存证描述相关
        msg.setPreservation_name("债权企业存证_" + p2PDO.getName());//存证名称
        msg.setPreservation_data_provider(p2PDO.getName());//存证数据提供方
        //负责人信息相关
        msg.setDocument_type("1");//证件类型默认是1：身份证
        msg.setName(p2PDO.getLegalName());//姓名
        msg.setIdcard(p2PDO.getIdentity());//证件号
        msg.setMobile(p2PDO.getMobile());//手机号
        PublicSecurityEssentialFactor public_security_essential_factor = new
                PublicSecurityEssentialFactor();
        //1:公安部二要素(姓名+身份证);
        public_security_essential_factor.setApply_num(apply_num);//申请编号
        //存证类型相关
        msg.setVerified_time(DateUtil.format(new Date(), "yyyyMMddHHmmss"));//实名时间
        msg.setVerified_type("1");
        msg.setPublic_security_essential_factor(public_security_essential_factor);//verified_type =1公安部二要素
        req.setCompany_principal_verified_msg(msg);//企业负责人身份:1.法人，2 代理人
        String result = companyDeposit.invokeCompanyDeposit(req);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1", response.getString("msg"));
        return response.getString("data");
    }

    /**
     * 编号证书申请
     *
     * @param customer_id 客户编号
     * @param evidence_no 存证编号
     */
    public void applyClinetNumcert(String customer_id, String evidence_no) {
        ApplyClientNumCert applyClientNumCert =
                new ApplyClientNumCert(APP_ID, APP_SECRET, V, HOST);
        String result
                = applyClientNumCert.invokeapplyClinetNumcert(customer_id, evidence_no);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1", response.getString("msg"));
    }

    /**
     * 自定义印章
     *
     * @param customer_id 客户编号
     * @param content     印章展示的内容
     */
    public String customSignature(String customer_id, String content) {
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String result = base.invokecustomSignature(customer_id, content);
        System.out.println(result);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1", response.getString("msg"));
        return response.getJSONObject("data").getString("signature_img_base64");
    }

    /**
     * 印章上传
     *
     * @param customer_id          客户编号
     * @param signature_img_base64 印章图片base64(由自定义印章获取)
     */
    public String addSignature(String customer_id, String signature_img_base64) {
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String result = base.invokeaddSignature(customer_id, signature_img_base64);
        System.out.println(result);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1", response.getString("msg"));
        return response.getJSONObject("data").getString("signature_id");
    }

    /**
     * 模板上传
     *
     * @param template_id 模板ID
     * @param file        模板文件
     */
    public void uploadtemplate(String template_id, File file) {
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String doc_url = null;//模板公网下载地址
        String result = base.invokeUploadTemplate(template_id, file, doc_url);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1", response.getString("msg"));
    }

    /**
     * 模板填充
     *
     * @param contract_id 合同编号
     * @param doc_title   合同标题
     * @param list        订单明细列表
     * @param orderDO     订单
     * @param accountDO   钱包账号
     * @param memberDO    会员账号
     * @param p2PDO       P2P平台
     */
    public JSONObject generateContract(String template_id, String contract_id, String doc_title, List<OrderItemDO> list
            , OrderDO orderDO, AccountDO accountDO, MemberDO memberDO, P2PDO p2PDO) {
        if (template_id == null) {
            template_id = TEMPLATE_ID;
        }
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String font_size = "10";//字体大小
        String font_type = "0";//字体类型
        String paramter = getparamter(contract_id, orderDO, accountDO, memberDO, p2PDO);//填充内容
        String dynamic_tables = getdynamic_tables(list);//动态表格
        String result = base.invokeGenerateContract(TEMPLATE_ID, contract_id, doc_title,
                font_size, font_type, paramter, dynamic_tables);
        JSONObject response = JSON.parseObject(result);
        if ("2002".equals(response.getString("code"))) {
            return null;
        }
        Asserts.eq(response.getString("code"), "1000", response.getString("msg"));
        return response;
    }

    private String getdynamic_tables(List<OrderItemDO> list) {
        JSONArray dynamic_tables = new JSONArray();
        JSONObject dynamic2 = new JSONObject();
        dynamic2.put("insertWay", 1);
        dynamic2.put("keyword", "具体实物信息见下表");
        dynamic2.put("pageBegin", "2");
        dynamic2.put("cellHeight", "16.0");//行高
        dynamic2.put("colWidthPercent", new int[]{4, 4, 4});//各列宽度比利
        //dynamic2.put("theFirstHeader", "附二");//表头标题
        dynamic2.put("cellHorizontalAlignment", "1");//水平对齐方式 (0：居左；1：居中；2：居右)
        dynamic2.put("cellVerticalAlignment", "5"); //垂直对齐方式 (4：居上；5：居中；6：居下)
        dynamic2.put("headers", new String[]{"货品名称", "货品单价", "货品数量"});
        String[][] dates = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            OrderItemDO oi = list.get(i);
            String[] row = new String[]{oi.getProductName(), oi.getProductPrice().toString(), oi.getQuantity().toString()};
            dates[i] = row;
        }
        dynamic2.put("datas", dates);
        dynamic2.put("headersAlignment", "1");
        dynamic2.put("tableWidthPercentage", 80);
        dynamic_tables.add(dynamic2);
        System.out.println(dynamic_tables.toString());
        return dynamic_tables.toString();
    }

    private String getparamter(String contract_id, OrderDO orderDO, AccountDO accountDO, MemberDO memberDO, P2PDO p2PDO) {
        JSONObject paramter = new JSONObject();
        paramter.put("order_id", contract_id);
        paramter.put("sign_time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        paramter.put("down_time", DateUtil.format(accountDO.getDeadline(), "yyyy-MM-dd HH:mm:ss"));//合同结束日期
        //甲方
        paramter.put("part_a", memberDO.getNickname());
        paramter.put("id_card_a", memberDO.getIdentity());
        paramter.put("address_a", orderDO.getProvince() + orderDO.getCity() + orderDO.getDistrict() + orderDO.getAddress());
        paramter.put("tel_a", orderDO.getMobile());
        //乙方
        paramter.put("part_b", p2PDO.getCompanyName());
        paramter.put("credit_code_b", p2PDO.getCreditCode());
        paramter.put("address_b", p2PDO.getAddress());
        paramter.put("tel_b", p2PDO.getMobile());
        //合同金额
        paramter.put("xf_money", accountDO.getOauthAmount());//剩余出借本金小写
        paramter.put("xf_max_money", Convert.digitToChinese(accountDO.getOauthAmount()));//剩余出借本金大写
        paramter.put("price", orderDO.getPayAmount());//出借本金小写
        paramter.put("max_price", Convert.digitToChinese(orderDO.getPayAmount()));//出借本金大写
        return paramter.toString();
    }
    //===================================合同业务==================================

    /**
     * 自动签署
     *
     * @param customer_id    客户编号
     * @param transaction_id 交易号
     * @param contract_id    合同编号
     * @param doc_title      文档标题
     */
    public void extSignAuto(String customer_id, String transaction_id, String contract_id, String doc_title) {
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        ExtsignReq req = new ExtsignReq();
        req.setCustomer_id(customer_id);//客户编号
        req.setTransaction_id(transaction_id);//交易号
        req.setContract_id(contract_id);//合同编号
        req.setClient_role("1");//客户角色
        req.setSign_keyword("章2");//定位关键字
        req.setKeyword_strategy("2");
        req.setDoc_title(doc_title);//文档标题
        req.setNotify_url("");//签署结果回调地址
        String result = base.invokeExtSignAuto(req);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1000", response.getString("msg"));
    }

    /**
     * 手动签署
     *
     * @param customer_id       客户编号
     * @param transaction_id    交易号
     * @param contract_id       合同编号
     * @param doc_title         文档标题
     * @param customer_mobile   客户手机号
     * @param customer_name     客户名称
     * @param customer_ident_no 客户身份证
     * @param returnUrl         页面跳转地址
     */
    public String extsign(String customer_id, String transaction_id, String contract_id, String doc_title, String customer_mobile, String customer_name, String customer_ident_no, String returnUrl) {
        // JAVA----短信校验
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        ExtsignReq req = new ExtsignReq();
        req.setCustomer_id(customer_id);//客户编号
        req.setTransaction_id(transaction_id);//交易号
        req.setContract_id(contract_id);//合同编号
        req.setDoc_title(doc_title);//文档标题
        req.setSign_keyword("章1");
        req.setKeyword_strategy("2");
        req.setReturn_url(returnUrl);//页面跳转URL（签署结果同步通知）
        req.setNotify_url(NOTIFY_URL);//签约结果异步通知
        //短信校验该参数必填
        String sign_url = base.invokeExtSign(req, customer_mobile, customer_name, customer_ident_no);
        return sign_url;
        // sign_url 是组装好的地址，请重定向到这个地址呈现签署页面给用户
        // 例如：HttpServletResponse().sendRedirect(sign_url);
        // 输出签署页面
    }

    /**
     * 合同查看
     *
     * @param contract_id 合同编号
     */
    public String viewContract(String contract_id) {
        FddClientExtra extra = new FddClientExtra(APP_ID, APP_SECRET, V, HOST);
        String view_url = extra.invokeViewPdfURL(contract_id);
        // 此时view_url 为查看链接，请开发者自行跳转
        return view_url;
    }

    /**
     * 合同下载
     *
     * @param contract_id 合同编号
     */
    public void downloadPdf(String contract_id) {
        FddClientExtra extra = new FddClientExtra(APP_ID, APP_SECRET, V, HOST);
        String download_url = extra.invokeDownloadPdf(contract_id);
        // 此时download_url 为组装好的下载链接，请开发者自行跳转
    }

    /**
     * 合同归档
     *
     * @param contract_id 合同编号
     */
    public void contractFilling(String contract_id) {
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String result = base.invokeContractFilling(contract_id);
        JSONObject response = JSON.parseObject(result);
        Asserts.eq(response.getString("code"), "1000", response.getString("msg"));
    }

    /**
     * 网络图片存为文件（法大大业务存储企业凭证，并上传给发大大）
     *
     * @param urlString
     * @return
     */
    public String getLocalFilePath(String urlString) {
        String filename = PIC_DIR + urlString.substring(urlString.lastIndexOf("/"));
        try {
            File file = new File(filename);
            if (file.exists()) {
                return filename;
            } else {
                if (!FileUtil.exist(PIC_DIR)) {
                    FileUtil.mkdir(PIC_DIR);
                }
                file.createNewFile();
            }
            URL url = new URL(PIC_BASE + urlString);// 构造URL
            BufferedImage img = ImageIO.read(url);
            ImageIO.write(img, "jpg", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    @Value("${store.fadada.host}")
    public void setHOST(String HOST) {
        FadadaClient.HOST = HOST;
    }

    @Value("${store.fadada.app-id}")
    public void setAPP_ID(String APP_ID) {
        FadadaClient.APP_ID = APP_ID;
    }

    @Value("${store.fadada.app-secret}")
    public void setAPP_SECRET(String APP_SECRET) {
        FadadaClient.APP_SECRET = APP_SECRET;
    }

    @Value("${store.fadada.notify-url}")
    public void setNotifyUrl(String notifyUrl) {
        FadadaClient.NOTIFY_URL = notifyUrl;
    }

    @Value("${store.fadada.template-id}")
    public void setTEMPLATE_ID(String TEMPLATE_ID) {
        FadadaClient.TEMPLATE_ID = TEMPLATE_ID;
    }

}
