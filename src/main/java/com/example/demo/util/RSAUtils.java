package com.example.demo.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

/**
 * RSA工具类
 *
 * @author Derrick
 */
public class RSAUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RSAUtils.class.getName());

    private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

    /**
     * 根据输入的seedKey生成公钥和私钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static HashMap<String, Object> getKeys(String seedKey) throws NoSuchAlgorithmException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        if (StringUtils.isNotEmpty(seedKey)) {
            secureRandom.setSeed(seedKey.getBytes(Charset.defaultCharset()));
        }
        keyPairGen.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }

    /**
     * 生成公钥和私钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 公钥
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOG.error("Invalid key or algorithm error", e);
        }
        return null;
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 私钥
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOG.error("Invalid key or algorithm error", e);
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param data      数据
     * @param publicKey 公钥
     * @return 公钥加密结果
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) {
        Cipher cipher;
        StringBuilder mi = new StringBuilder();
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 模长
            int keyLen = publicKey.getModulus().bitLength() / 8;
            // 加密数据长度 <= 模长-11
            String[] datas = splitString(data, keyLen - 11);
            //如果明文长度大于模长-11则要分组加密
            for (String s : datas) {
                mi.append(bcd2Str(cipher.doFinal(s.getBytes(Charset.defaultCharset()))));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidKeyException e) {
            LOG.error("encrypt by public key error", e);
        }
        return mi.toString();
    }

    /**
     * 私钥加密
     *
     * @param data       数据
     * @param privateKey 私钥
     * @return 私钥加密
     */
    public static String encryptByPrivateKey(String data, RSAPrivateKey privateKey) {
        Cipher cipher;
        StringBuilder mi = new StringBuilder();
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 模长
            int keyLen = privateKey.getModulus().bitLength() / 8;
            // 加密数据长度 <= 模长-11
            String[] datas = splitString(data, keyLen - 11);
            //如果明文长度大于模长-11则要分组加密
            for (String s : datas) {
                mi.append(bcd2Str(cipher.doFinal(s.getBytes(Charset.defaultCharset()))));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidKeyException e) {
            LOG.error("encrypt by private key error", e);
        }
        return mi.toString();
    }

    /**
     * 私钥解密
     *
     * @param data       数据
     * @param privateKey 私钥
     * @return 解密结果
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) {
        Cipher cipher;
        StringBuilder ming = new StringBuilder();
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //模长
            int keyLen = privateKey.getModulus().bitLength() / 8;
            byte[] bytes = data.getBytes(Charset.defaultCharset());
            byte[] bcd = asciiToBcd(bytes, bytes.length);
            //如果密文长度大于模长则要分组解密
            byte[][] arrays = splitArray(bcd, keyLen);
            for (byte[] arr : arrays) {
                ming.append(new String(cipher.doFinal(arr)));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidKeyException e) {
            LOG.error("decrypt by private key error", e);
        }
        return ming.toString();
    }

    /**
     * 公钥解密
     *
     * @param data     数据
     * @param publicKey 公钥
     * @return 解密结果
     */
    public static String decryptByPublicKey(String data, RSAPublicKey publicKey) {
        Cipher cipher;
        StringBuilder ming = new StringBuilder();
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            //模长
            int keyLen = publicKey.getModulus().bitLength() / 8;
            byte[] bytes = data.getBytes(Charset.defaultCharset());
            byte[] bcd = asciiToBcd(bytes, bytes.length);
            //如果密文长度大于模长则要分组解密
            byte[][] arrays = splitArray(bcd, keyLen);
            for (byte[] arr : arrays) {
                ming.append(new String(cipher.doFinal(arr)));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidKeyException e) {
            LOG.error("decrypt by public key error", e);
        }
        return ming.toString();
    }

    /**
     * ASCII码转BCD码
     */
    public static byte[] asciiToBcd(byte[] ascii, int ascLen) {
        byte[] bcd = new byte[ascLen / 2];
        int j = 0;
        for (int i = 0; i < (ascLen + 1) / 2; i++) {
            bcd[i] = ascToBcd(ascii[j++]);
            bcd[i] = (byte) (((j >= ascLen) ? 0x00 : ascToBcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte ascToBcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')) {
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else {
            if ((asc >= 'a') && (asc <= 'f')) {
                bcd = (byte) (asc - 'a' + 10);
            } else {
                bcd = (byte) (asc - 48);
            }
        }
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char[] temp = new char[bytes.length * 2];
        char val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    private static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    private static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    /**
     * @param account  用户账号
     * @param password 用户密码
     * @return 根据用户名/密码/时间戳生成的token
     */
    public static String generateToken(String account, String password, String modulus, String publicExponent) {
        DateTime dateTime = new DateTime();
        String ming = account + "," + dateTime.toString("yyyy-MM-dd HH:mm:ss.S") + "," + password;
        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, publicExponent);
        //加密后的密文
        return RSAUtils.encryptByPublicKey(ming, pubKey);
    }

    /**
     * @param token 待解析处理的token
     * @return parse过的Token
     */
    public static String parserToken(String token, String modulus, String privateExponent) {
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, privateExponent);
        return decryptByPrivateKey(token, priKey);
    }

    /**
     * @param token 令牌
     * @param modulus 模块
     * @param publicExponent 指数
     * @return 解密完的license
     */
    public static String decryptLicense(String token, String modulus, String publicExponent) {
        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, publicExponent);
        return decryptByPublicKey(token, pubKey);
    }

    /**
     * @param token token
     * @param modulus 模块
     * @param privateExponent 指数
     * @return license字符串
     */
    public static String generateLicense(String token, String modulus, String privateExponent) {
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, privateExponent);
        return encryptByPrivateKey(token, priKey);
    }
}
