package com.example.demo.util;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;

/**
 * Created by 92992 on 2018/12/9.
 */
public class DesUtils {

    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static final String DES_KEY = Utils.getDayByDate(new Date()) + "webihkcn";

    //private static Logger logger = LoggerFactory.getLogger(DesUtils.class);

    /**
     * DES算法，加密
     *
     * @param data
     *            待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     *             异常
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(DES_KEY,data);
    }

    /**
     * DES算法，加密
     *
     * @param data
     *            待加密字符串
     * @param key
     *            加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     *             异常
     */
    public static String encrypt(String key, String data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes("utf-8"));

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("********".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

            byte[] bytes = cipher.doFinal(data.getBytes());
            new Base64();
            return new String(Base64.encodeBase64Chunked(bytes));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * DES算法，解密
     *
     * @param data
     *            待解密字符串
     * @param key
     *            解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception
     *             异常
     */
    public static String decrypt(String key, String data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes("utf-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("********".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            new Base64();
            return new String(cipher.doFinal(Base64.decodeBase64(data)));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    public static String decrypt(String data) throws Exception {
        return decrypt(DES_KEY,data);
    }


    /**
     * 使用Base64加密
     *
     * @param inputText
     * @return
     * @throws EncoderException
     */
    public static String base64Encode(String inputText) throws EncoderException {
        int currDay = Utils.getDayByDate(new Date());
        String key = "ihkapp_web00";
        if(currDay < 10)
            key = key + "0" + currDay;
        else
            key = key + currDay;
        String encodeStr = key + inputText;
        new Base64();
        String result = new String(Base64.encodeBase64Chunked(encodeStr.getBytes()));
        return result;
    }


}
