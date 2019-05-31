 
 ## P2P平台授权接口规范
 
 #### 对接准备：管理员分配 ［appId］［appSecret］
 
 #### 授权过程：请求接口地址，成功获取token后，直接将页面转入跳转地址即可完成授权
 
 #### 接口地址：https://yliao.d2c.cn/api/oauth
 
 #### 接口方式：POST（application/x-www-form-urlencoded）
 
 #### 跳转地址：https://yliao.d2c.cn/oAuth?token=

 #### 请求参数：
 
| 参数 | 类型 |描述 |
| ---- | ---- | ---- |
| appId | String | 平台ID（平台ID） |
| mobile | String |  授权账号（手机号码） |
| name | String |  真实姓名（务必真实）  |
| identity | String |  身份证号（务必真实）  |
| amount | BigDecimal |  授权金额（格式:1.23 / 单位:元） |
| sign | String |  请求签名（签名方式:MD5） |

 #### 注意事项：
 　　请贵司平台自己做好用户信息的真实性验证，有料网无法对资料的真实性进行核对，默认接收贵司平台传递的任何参数，并根据这些参数生成会员相关数据。
 
 #### 签名方式：
 　　以上字段除去sign，按照参数名的字典顺序排列，以如下形式拼接成字符串，然后加上appSecret字符串，将整个字符串MD5得到sign。
 
 ```
 [appId=1111166059554922498]
 [appSecret=1234567890]
 
 1.字典排列
 amount=30000&appId=1111166059554922498&identity=330823********2115&mobile=137****9882&name=王**
 
 2.加上appSecret
 amount=30000&appId=1111166059554922498&identity=330823********2115&mobile=137****9882&name=王**1234567890
 
 3.最后MD5
 sign=9aa9c188d2e10c73a3638dc1df495002
 ```
 
 #### JAVA签名示例代码：
 ```
    public String getSignContent(Map<String, String> params) {
        if (params == null) {
            return null;
        } else {
            params.remove("sign");
            StringBuffer content = new StringBuffer();
            List<String> keys = new ArrayList(params.keySet());
            Collections.sort(keys);
            for (int i = 0; i < keys.size(); ++i) {
                String key = String.valueOf(keys.get(i));
                String value = String.valueOf(params.get(key));
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            }
            return content.toString();
        }
    }
    
    String sign = DigestUtil.md5Hex(this.getSignContent(PARAMS_MAP) + APP_SECRET);
 ```
 #### 返回成功示例：
 ```
 {
   "code": 1,
   "msg": "操作成功",
   "data": "D2C-$2a$10$iWwxYtpyqtpp8KlFMh9tnOSYfwnvvA9cWMl6J0LO5ULHHygwJRWbK"
 }
 ```
 #### 返回失败示例：
 ```
 {
   "code": -1,
   "msg": "appId不正确，请仔细检查" / "签名不正确，请仔细检查"
 }
 ```

 ## P2P平台订单通知接口
 
 #### 对接准备：需先在有料网管理后台配置通知地址
 
 #### 请求参数：
 
| 参数 | 类型 |描述 |
| ---- | ---- | ---- |
| orderNo | String | 订单编号 |
| orderTime | String |  订单创建时间（yyyy-MM-dd HH:mm:ss） |
| orderAmount | float |  实际支付（单位:元）  |
| limitAmount | float |  授权金额（单位:元） |
| freightAmount | float |  运费价格（单位:元） |
| realName | String |  用户姓名 |
| phone | String |  手机号 |
| address | String |  收货地址 |
| totalPrice | float |  商品总价（单位:元） |
| orderDetail | String |  商品明细（JSONArray字符串 结构看下表） |
| sign | String |  签名（签名方式与授权接口相同 推荐自行校验） |

#### 商品明细 orderDetail
| 参数 | 类型 |描述 |
| ---- | ---- | ---- |
| goodsName | String | 商品名称 |
| skuNo | String |  商品编码 |
| quantity | int |  下单数量  |
| price | float |  商品单价  |

#### JAVA通知接收接口示例
```
public JSONObject callYLiao(HttpServletRequest request, HttpResponse response) {
        Map<String, String[]> map = new HashMap<String,String[]>(request.getParameterMap());
        Map<String,String> params=new HashMap<>();
        //Map<String, String[]>里的数据转为Map<String,String>
        map.forEach((k,v)->params.put(k,v[0]));
        //验证签名
        String sign=params.get("sign");
        String signContent=getSignContent(params);
        String signature = DigestUtil.md5Hex(signContent + APP_SECRET);
        if(sign.equalsIgnoreCase(signature)){
            //业务代码
        }
        //返回结果
        JSONObject resp=new JSONObject();
        resp.put("code",100);
        return resp;
    }
```

 #### 返回成功示例：
 ```
 {
   "code": 100
 }
 ```
 #### 返回失败示例：
 
 除了成功之外，其他结果均认为是失败，失败不会重新通知，失败后可以通过有料网后台对对应的订单重新发送通知。
