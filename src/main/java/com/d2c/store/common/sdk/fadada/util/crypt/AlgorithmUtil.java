package com.d2c.store.common.sdk.fadada.util.crypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AlgorithmUtil {

    public final static String ENCODING = "UTF-8";
    public final static int BUFFER_SIZE = 4096;
    public final static String ENCRYPT_ALGORITHM = "AES";

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 生成密钥
     * 自动生成base64 编码后的AES128位密钥
     *
     * @return
     * @throws Exception
     */
    public static String getAESKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ENCRYPT_ALGORITHM);
        kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256  
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return parseByte2HexStr(b);
    }

    /**
     * 自定义字符串密钥生成
     *
     * @param appkey
     * @return
     * @throws Exception
     */
    public static String getIndentifyAESKey(String appkey) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ENCRYPT_ALGORITHM);
        // kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
        //SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以生成的秘钥就一样。
        kg.init(128, new SecureRandom(appkey.getBytes()));
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return parseByte2HexStr(b);
    }

    /**
     * AES 加密
     *
     * @param base64Key base64编码后的 AES key
     * @param text      待加密的字符串
     * @return 加密后的byte[] 数组
     * @throws Exception
     */
    public static byte[] getAESEncode(String base64Key, String text) throws Exception {
        byte[] key = parseHexStr2Byte(base64Key);
        SecretKeySpec sKeySpec = new SecretKeySpec(key, ENCRYPT_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] bjiamihou = cipher.doFinal(text.getBytes(ENCODING));
        return bjiamihou;
    }

    /**
     * AES 加密
     *
     * @param base64Key 加密秘钥
     * @param buffer    待加密byte数组
     * @return 加密后的byte[] 数组
     * @throws Exception
     */
    public static byte[] getAESEncode(String base64Key, byte[] buffer) throws Exception {
        byte[] key = parseHexStr2Byte(base64Key);
        SecretKeySpec sKeySpec = new SecretKeySpec(key, ENCRYPT_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] bjiamihou = cipher.doFinal(buffer);
        return bjiamihou;
    }

    /**
     * AES解密
     *
     * @param base64Key base64编码后的 AES key
     * @param text      待解密的字符串
     * @return 解密后的byte[] 数组
     * @throws Exception
     */
    public static byte[] getAESDecode(String base64Key, byte[] text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] key = parseHexStr2Byte(base64Key);
        SecretKeySpec sKeySpec = new SecretKeySpec(key, ENCRYPT_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
        byte[] bjiemihou = cipher.doFinal(text);
        return bjiemihou;
    }

    /**
     * 将InputStream转换成byte数组
     *
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return outStream.toByteArray();
    }

    public static String getEecodeStr(String appkey, String encode) throws Exception {
        String hexKey = AlgorithmUtil.getIndentifyAESKey(appkey);
        byte[] aesEncode = AlgorithmUtil.getAESEncode(hexKey, CryptTool.base64Decode(encode));
        return new String(aesEncode, "utf-8");
    }

}
