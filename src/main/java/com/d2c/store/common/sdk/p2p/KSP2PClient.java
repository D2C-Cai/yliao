package com.d2c.store.common.sdk.p2p;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * p2p平台ks
 */
@Component
public class KSP2PClient {

    public static String SECRET_KEY;
    public static String NOTICE_URL;
    @Autowired
    private KSP2PClient kSP2PClient;
//        public static void main(String[] args) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderNo","Q测试数据订单编号");
//        map.put("orderTime", "2019-04-18 15:47:01");
//        //obj.put("orderTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        map.put("orderAmount",new BigDecimal(1000).toString());
//        map.put("limitAmount","1000");
//        map.put("freightAmount",new BigDecimal(10).toString());
//        map.put("realName","林林林");
//        map.put("phone","13333333333");
//        map.put("address","浙江省杭州市莲花峰路");
//        map.put("totalPrice",new BigDecimal(1000).toString());
//        JSONArray list=new JSONArray();
//        JSONObject item=new JSONObject();
//        item.put("goodsName","测试商品");
//        item.put("skuNo","n123456");
//        item.put("quantity","1");
//        item.put("price","1000");
//        list.add(item);
//        map.put("orderDetail",list);
//        KSP2PClient client=new KSP2PClient();
//        client.setSECRET_KEY("8db72ea1c6b2c86a813733171393ad11");
//        String sign=client.getSignToken(map);
//        map.put("sign",sign);
//        System.out.println(map);
//        String response=client.sendPost("https://www.kesudai.com.cn/gt/notifyUserOrder",map);
//        System.out.println(UnicodeUtil.toString(response));
//    }

    /**
     * 生成签名
     *
     * @param map
     * @return
     */
    public String getSignToken(Map<String, Object> map) {
        String result = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, Comparator.comparing(Map.Entry::getKey));
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder("");
            for (Map.Entry<String, Object> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    Object val = item.getValue();
                    // orderDetail要特殊处理下
                    if ("orderDetail".equals(key)) {
                        JSONArray orderDetail = (JSONArray) val;
                        for (int i = 0; i < orderDetail.size(); i++) {
                            JSONObject obj = orderDetail.getJSONObject(i);
                            for (String itemKey : obj.keySet()) {
                                String valueWord1 = URLEncoder.encode("[" + i + "][" + itemKey + "]", "utf-8");
                                String valueWord2 = URLEncoder.encode(obj.getString(itemKey), "utf-8");
                                sb.append(item.getKey() + valueWord1 + "=" + valueWord2 + "&");
                            }
                        }
                        continue;
                    }
                    String valueWord = URLEncoder.encode(val.toString(), "utf-8");
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + valueWord + "&");
                    }
                }
            }
            sb.append("secretKey=" + KSP2PClient.SECRET_KEY);
            result = sb.toString();
            // 进行MD5加密
            result = SecureUtil.md5(result);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * 整合签名发送
     *
     * @param obj
     * @return
     */
    public int send(Map obj) {
        String sign = kSP2PClient.getSignToken(obj);
        obj.put("sign", sign);
        String response = kSP2PClient.sendPost(KSP2PClient.NOTICE_URL, obj);
        JSONObject result = JSONObject.parseObject(response);
        if ("100".equals(result.getString("code"))) {
            return 1;
        }
        return -1;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param param
     * @return
     */
    public String sendPost(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        paramList.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
                    } else if (entry.getValue() instanceof JSONArray) {
                        JSONArray array = (JSONArray) entry.getValue();
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            for (String itemKey : obj.keySet()) {
                                paramList.add(new BasicNameValuePair(entry.getKey() + "[" + i + "][" + itemKey + "]", obj.getString(itemKey)));
                            }
                        }
                    }
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    @Value("${store.p2p.ks.notice-url}")
    public void setNOTICE_URL(String NOTICE_URL) {
        KSP2PClient.NOTICE_URL = NOTICE_URL;
    }

    @Value("${store.p2p.ks.secret-key}")
    public void setSECRET_KEY(String SECRET_KEY) {
        KSP2PClient.SECRET_KEY = SECRET_KEY;
    }

}
