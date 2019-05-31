/**
 * 版权所有：深圳法大大网络科技有限公司
 * Copyright 2015 fadada.com Inc.
 * All right reserved.
 * ====================================================
 * 文件名称: FddEncryptTool.java
 * 修订记录：
 * No    日期				作者(操作:具体内容)
 * 1.    Dec 18, 2015			Mocuishle(创建:创建文件)
 * ====================================================
 * 类描述：(说明未实现或其它不应生成javadoc的内容)
 */
package com.d2c.store.common.sdk.fadada.util.crypt;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h3>概要:</h3> 法大大加密工具类 <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>提供md5\sha1\base64\3des 加密</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2015年12月18日[zhouxw] 新建</li>
 * </ol>
 */
public class FddEncryptTool {

    /* ======================================================================================== */
    /* ===================================TODO BASE64 部分====================================== */
    private static final byte[] DECODE_TABLE = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 62, 0, 0, 0, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 0, 0, 0, 0, 0, 0, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 0, 0, 0, 0, 0};
    // "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/ "
    private static final byte[] ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49,
            50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    /* ======================================================================================== */
    /* ===================================TODO MD5 部分========================================= */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    /* ======================================================================================== */
    /* ===================================TODO 3DES 部分============================================= */
    private static final String CRYPT_ALGORITHM = "DESede";
    /* ==============================TODO SHA1 部分end=========================================== */
    /* ======================================================================================== */
    /* ======================================================================================== */
    /* ==============================TODO SHA256 部分============================================ */
    private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static {
        // create encode table
        // ENCODE_TABLE = new byte[64];
        int index = 0;
        for (char c = 'A'; c <= 'Z'; c++)
            ENCODE_TABLE[index++] = (byte) c;
        for (char c = 'a'; c <= 'z'; c++)
            ENCODE_TABLE[index++] = (byte) c;
        for (char c = '0'; c <= '9'; c++)
            ENCODE_TABLE[index++] = (byte) c;
        ENCODE_TABLE[index++] = (byte) '+';
        ENCODE_TABLE[index++] = (byte) '/';
        // create decode table
        for (int i = 0; i < 64; i++)
            DECODE_TABLE[(int) ENCODE_TABLE[i]] = (byte) i;
    }

    private FddEncryptTool() {
    }

    /**
     * <b>概要：</b> base64加密 <b>作者：</b>zhouxw </br> <b>日期：</b>2015年12月17日 </br>
     *
     * @param data
     * @return
     */
    public static byte[] Base64Encode(byte[] data) {
        if (data == null)
            return null;
        int fullGroups = data.length / 3;
        int resultBytes = fullGroups * 4;
        if (data.length % 3 != 0)
            resultBytes += 4;
        byte[] result = new byte[resultBytes];
        int resultIndex = 0;
        int dataIndex = 0;
        int temp = 0;
        for (int i = 0; i < fullGroups; i++) {
            temp = (data[dataIndex++] & 0xff) << 16 | (data[dataIndex++] & 0xff) << 8 | data[dataIndex++] & 0xff;
            result[resultIndex++] = ENCODE_TABLE[(temp >> 18) & 0x3f];
            result[resultIndex++] = ENCODE_TABLE[(temp >> 12) & 0x3f];
            result[resultIndex++] = ENCODE_TABLE[(temp >> 6) & 0x3f];
            result[resultIndex++] = ENCODE_TABLE[temp & 0x3f];
        }
        temp = 0;
        while (dataIndex < data.length) {
            temp <<= 8;
            temp |= data[dataIndex++] & 0xff;
        }
        switch (data.length % 3) {
            case 1:
                temp <<= 8;
                temp <<= 8;
                result[resultIndex++] = ENCODE_TABLE[(temp >> 18) & 0x3f];
                result[resultIndex++] = ENCODE_TABLE[(temp >> 12) & 0x3f];
                result[resultIndex++] = 0x3D;
                result[resultIndex++] = 0x3D;
                break;
            case 2:
                temp <<= 8;
                result[resultIndex++] = ENCODE_TABLE[(temp >> 18) & 0x3f];
                result[resultIndex++] = ENCODE_TABLE[(temp >> 12) & 0x3f];
                result[resultIndex++] = ENCODE_TABLE[(temp >> 6) & 0x3f];
                result[resultIndex++] = 0x3D;
                break;
            default:
                break;
        }
        return result;
    }

    /* ===================================TODO BASE64 部分 end====================================== */
    /* ======================================================================================== */

    /**
     * <b>概要：</b> base64解密 <b>作者：</b>zhouxw </br> <b>日期：</b>2015年12月17日 </br>
     *
     * @param base64Data
     * @return
     */
    public static byte[] Base64Decode(byte[] base64Data) {
        if (base64Data == null)
            return null;
        if (base64Data.length == 0)
            return new byte[0];
        if (base64Data.length % 4 != 0)
            throw new IllegalArgumentException("数据不完整，长度为： " + base64Data.length);
        byte[] result = null;
        int groupCount = base64Data.length / 4;
        int lastData = base64Data.length;
        while (base64Data[lastData - 1] == 0x3D) {
            if (--lastData == 0)
                return new byte[0];
        }
        result = new byte[lastData - groupCount];
        int temp = 0;
        int resultIndex = 0;
        int dataIndex = 0;
        for (; dataIndex + 4 < base64Data.length; ) {
            temp = DECODE_TABLE[base64Data[dataIndex++]];
            temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex++]];
            temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex++]];
            temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex++]];
            result[resultIndex++] = (byte) ((temp >> 16) & 0xff);
            result[resultIndex++] = (byte) ((temp >> 8) & 0xff);
            result[resultIndex++] = (byte) (temp & 0xff);
        }
        temp = 0;
        int j = 0;
        for (; dataIndex < base64Data.length; dataIndex++, j++)
            temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex]];
        for (; j < 4; j++)
            temp <<= 6;
        result[resultIndex++] = (byte) ((temp >> 16) & 0xff);
        if (base64Data[dataIndex - 2] != '=')
            result[resultIndex++] = (byte) ((temp >> 8) & 0xff);
        if (base64Data[dataIndex - 1] != '=')
            result[resultIndex++] = (byte) (temp & 0xff);
        return result;
    }

    public static String getimgBase64(File file) {
        String imgBase64 = "";
        Base64 base64 = new Base64();
        try {
            FileInputStream io = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            io.read(bytes);
            imgBase64 = new String(base64.encode(bytes));
            io.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgBase64;
    }

    /**
     * 在线图片转换成base64字符串
     *
     * @dateTime 2018-02-23 14:43:18
     */
    public static String ImageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String endimgbase64 = encoder.encode(data.toByteArray()).replaceAll("\r|\n", "");
        return endimgbase64;
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * <b>概要：</b> 转换字节数组为16进制字符串 <b>作者：</b>zhouxw </br> <b>日期：</b>2015年12月17日 </br>
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /* ===================================TODO MD5 部分 end======================================= */
    /* ======================================================================================== */

    /* ======================================================================================== */
    /* ==============================TODO SHA1 部分============================================== */

    /**
     * <b>概要：</b> MD5 摘要计算 <b>作者：</b>zhouxw </br> <b>日期：</b>2015年12月17日 </br>
     *
     * @param src
     * @return
     * @throws Exception
     */
    public static byte[] md5Digest(byte[] src) throws Exception {
        // MD5 is 16 bit message digest
        MessageDigest alg = MessageDigest.getInstance("MD5");
        return alg.digest(src);
    }

    /**
     * <b>概要：</b> MD5 摘要计算 <b>作者：</b>zhouxw </br> <b>日期：</b>2015年12月17日 </br>
     *
     * @param src
     * @return
     * @throws Exception
     */
    public static String md5Digest(String src) throws Exception {
        return byteArrayToHexString(md5Digest((src.getBytes("UTF-8"))));
    }

    /**
     * 实现SHA-1消息摘要
     *
     * @param inStr
     * @return 成功，返回摘要，失败，返回null
     */
    public static String sha1(String inStr) {
        String outStr = null;
        try {
            outStr = FddEncryptTool.digest(inStr.getBytes("UTF-8"), "SHA-1");
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
        }
        return outStr;
    }

    /**
     * 实现SHA-256消息摘要
     *
     * @param inStr
     * @return 成功，返回摘要，失败，返回null
     */
    public static String sha256(String inStr) {
        String outStr = null;
        try {
            outStr = FddEncryptTool.digest(inStr.getBytes("UTF-8"), "SHA-256");
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
        }
        return outStr;
    }

    /**
     * 获取文件sha256值
     *
     * @param file
     * @return
     */
    public static String sha256(File file) {
        FileInputStream fis = null;
        String outputStr = "";
        try {
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            MessageDigest alg = MessageDigest.getInstance("SHA-256");
            int numRead = 0;
            while ((numRead = fis.read(b)) > 0) {
                alg.update(b, 0, numRead);
            }
            byte[] digest = alg.digest();
            outputStr = byte2hexLower(digest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return outputStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return outputStr;
        } catch (IOException e) {
            e.printStackTrace();
            return outputStr;
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStr;
    }

    public static String byte2hexLower(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    public static String digest(byte[] inputBytes, String algorithm) {
        String outputStr = null;
        try {
            MessageDigest alg = MessageDigest.getInstance(algorithm);
            alg.update(inputBytes);
            byte[] digest = alg.digest();
            outputStr = byte2hex(digest);
        } catch (NoSuchAlgorithmException ex) {
        }
        return outputStr;
    }

    /**
     * 二进制转十六进制字符串。每一个字节转为两位十六进制字符串。
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * <b>概要：</b>获取文件的sha256值 <b>作者：</b>zhouxw </br> <b>日期：</b>2016年11月17日 </br>
     *
     * @param filename 文件路径
     * @return
     */
    public static String getFileSHA256(String filename) {
        String str = "";
        try {
            str = getHash(filename, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String getHash(String fileName, String hashType) throws Exception {
        InputStream fis = new FileInputStream(fileName);
        byte[] buffer = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }

    /* ==============================TODO SHA256 部分end=========================================== */
    /* ======================================================================================== */

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[((b[i] & 0xF0) >>> 4)]);
            sb.append(hexChar[(b[i] & 0xF)]);
        }
        return sb.toString();
    }

    /**
     * <b>概要：</b> 描述该方法的功能 <b>作者：</b>zhouxw </br> <b>日期：</b>2016年11月17日 </br>
     *
     * @param value
     * @param key
     * @return
     */
    public static String encrypt(String value, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), CRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedByte = cipher.doFinal(value.getBytes("UTF-8"));
            String encodedByte = byte2hex(encryptedByte);
            return encodedByte;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <b>概要：</b> 3DES解密 <b>作者：</b>zhouxw </br> <b>日期：</b>2016年1月6日 </br>
     *
     * @param value
     * @param key
     * @return
     */
    public static String decrypt(String value, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), CRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedByte = cipher.doFinal(hex2byte(value));
            return new String(decryptedByte, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <b>概要：</b> 十六进制转二进制 <b>作者：</b>zhouxw </br> <b>日期：</b>2016年1月6日 </br>
     *
     * @param hex
     * @return
     * @throws IllegalArgumentException
     */
    public static byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }
    /* ===================================3DES 部分END========================================== */
    /* ======================================================================================== */
}
