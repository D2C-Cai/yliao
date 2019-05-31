package com.d2c.store.common.sdk.fadada.util.crypt;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MsgDigestUtil {

    private static final String EXCLUSION = "msg_digest";

    public static String getCheckMsgDigest(String appId, String appSecret, String timeStr, String[] parameters) {
        try {
            StringBuilder parameter = new StringBuilder();
            for (String str : parameters) {
                if (StringUtils.isNotBlank(str)) {
                    parameter.append(str);
                }
            }
            String sha1 = CryptTool.sha1(appId + FddEncryptTool.md5Digest(timeStr) + CryptTool.sha1(appSecret + parameter.toString()));
            String base64 = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();
            return base64;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCheckMsgDigest(String appId, String appSecret, String timeStr, Map<String, String> parameters) {
        try {
            String codes = sortParameters(parameters);
            String sha1 = CryptTool.sha1(appId + FddEncryptTool.md5Digest(timeStr) + CryptTool.sha1(appSecret + codes));
            String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();
            return msgDigest;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCheckMsgDigest(String appId, String appSecret, String timeStr, Object object) {
        try {
            String codes = sortParameters(object);
            String sha1 = CryptTool.sha1(appId + FddEncryptTool.md5Digest(timeStr) + CryptTool.sha1(appSecret + codes));
            String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();
            return msgDigest;
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressWarnings("unused")
    private static String sortParameters(Map<String, Object> parameters) {
        StringBuilder sb = new StringBuilder();
        SortedMap<String, Object> paramMap = new TreeMap<String, Object>(parameters);
        for (String key : paramMap.keySet()) {
            sb.append(paramMap.get(key));
        }
        return sb.toString();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static String sortParameters(Object object) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> list = new ArrayList<String>();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!EXCLUSION.equals(fieldName)) {
                list.add(fieldName);
            }
        }
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String name : list) {
            String first = name.substring(0, 1);
            String subName = name.substring(1);
            String methodName = "get" + first.toUpperCase() + subName;
            Method method = clazz.getDeclaredMethod(methodName, null);
            Object value = method.invoke(object, null);
            if (null != value) {
                sb.append(value.toString());
            }
        }
        return sb.toString();
    }

    /**
     * map集合排序，方便加密
     *
     * @param parameters
     * @return
     */
    public static String[] sortforParameters(Map<String, String> parameters) {
        String[] codes = new String[parameters.size()];
        SortedMap<String, String> paramMap = new TreeMap<String, String>(parameters);
        int index = 0;
        for (String key : paramMap.keySet()) {
            codes[index] = paramMap.get(key);
            index++;
        }
        return codes;
    }

}
