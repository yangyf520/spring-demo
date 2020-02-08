package com.example.demo.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 加密工具类.
 */
public final class EncryptorUtils {
    /**
     * 允许的字符
     */
    public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

    private static final Logger LOG = LoggerFactory.getLogger(EncryptorUtils.class.getName());

    private final static String DEFAULT_CHAR_SET = "utf-8";

    /**
     * 容量倍数
     */
    private static final int CAPACITY_MULTIPLE = ConstantUtils.getConstantValue(3);

    private static final int STRING_RADIX = ConstantUtils.getConstantValue(16);

    /**
     * ASCII 常量
     */
    private static final int ASC_0XFF = ConstantUtils.getConstantValue(0xff);

    private static final int ASC_0X10 = ConstantUtils.getConstantValue(0x10);

    private static final int ASC_0XF = ConstantUtils.getConstantValue(0xF);

    private static final int ASC_0XC0 = ConstantUtils.getConstantValue(0xc0);

    private static final int ASC_0X80 = ConstantUtils.getConstantValue(0x80);

    private static final int ASC_0X3F = ConstantUtils.getConstantValue(0x3f);

    private static final int ASC_0XE0 = ConstantUtils.getConstantValue(0xe0);

    private static final int ASC_0X1F = ConstantUtils.getConstantValue(0x1f);

    private static final int ASC_0X00 = ConstantUtils.getConstantValue(0x00);

    private static final int ASC_0XF0 = ConstantUtils.getConstantValue(0xf0);

    private static final int ASC_0X0F = ConstantUtils.getConstantValue(0x0f);

    private static final int ASC_0XF8 = ConstantUtils.getConstantValue(0xf8);

    private static final int ASC_0X07 = ConstantUtils.getConstantValue(0x07);

    private static final int ASC_0XFC = ConstantUtils.getConstantValue(0xfc);

    private static final int ASC_0X03 = ConstantUtils.getConstantValue(0x03);

    private static final int ASC_0X01 = ConstantUtils.getConstantValue(0x01);

    // int 常量
    private static final int NUM_1 = ConstantUtils.getConstantValue(1);

    private static final int NUM_2 = ConstantUtils.getConstantValue(2);

    private static final int NUM_3 = ConstantUtils.getConstantValue(3);

    private static final int NUM_4 = ConstantUtils.getConstantValue(4);

    private static final int NUM_5 = ConstantUtils.getConstantValue(5);

    private static final int NUM_6 = ConstantUtils.getConstantValue(6);

    private static final int NUM_10 = ConstantUtils.getConstantValue(10);

    private EncryptorUtils() {

    }

    /**
     * 将字符串进行Base64编码
     *
     * @param s 被编码的字符串
     * @return 编码后的字符串
     */
    public static String encoderBASE64(String s) {
        if (s == null) {
            return null;
        }
        try {
            return Base64.encodeBase64String(s.getBytes(DEFAULT_CHAR_SET));
        }
        catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     *
     * @param s 被解码的字符串
     * @return 解码后的字符串
     */
    public static String decoderBASE64(String s) {
        String str = null;
        try {
            if (StringUtils.isNotBlank(s)) {
                byte[] b = Base64.decodeBase64(s);
                str = new String(b, StandardCharsets.UTF_8);
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * 将URL进行编码，如果URL中的字符没有包含在ALLOWED_CHARS中就将这个字符转换成16进制的字符串每个字节前面加% 如 "汉 字AAA" 被转换成 "%E6%B1%89%20%E5%AD%97AAA"
     *
     * @param input 字符串
     * @return 转换后的结果
     */
    public static String encodeURIComponent(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }

        int l = input.length();
        StringBuilder o = new StringBuilder(l * CAPACITY_MULTIPLE);
        for (int i = 0; i < l; i++) {
            String e = input.substring(i, i + 1);
            if (!ALLOWED_CHARS.contains(e)) {
                byte[] b = e.getBytes(Charset.forName(DEFAULT_CHAR_SET));
                o.append(getHex(b));
                continue;
            }
            o.append(e);
        }
        return o.toString();
    }

    private static String getHex(byte[] buf) {
        StringBuilder o = new StringBuilder(buf.length * CAPACITY_MULTIPLE);
        for (byte b : buf) {
            int n = b & ASC_0XFF;
            o.append("%");
            if (n < ASC_0X10) {
                o.append("0");
            }
            o.append(Long.toString(n, STRING_RADIX).toUpperCase());
        }
        return o.toString();
    }

    /**
     * 将encodeURIComponent解码
     *
     * @param encodedURI encodeURIComponent编码后的字符串
     * @return 解码后的结果
     */
    public static String decodeURIComponent(String encodedURI) {
        char actualChar;

        StringBuilder buffer = new StringBuilder();

        int bytePattern, sumb = 0;

        for (int i = 0, more = -1; i < encodedURI.length(); i++) {
            actualChar = encodedURI.charAt(i);

            switch (actualChar) {
                case '%':
                    actualChar = encodedURI.charAt(++i);
                    int hb = (Character.isDigit(actualChar) ? actualChar - '0' : NUM_10 + Character.toLowerCase(actualChar) - 'a') & ASC_0XF;
                    actualChar = encodedURI.charAt(++i);
                    int lb = (Character.isDigit(actualChar) ? actualChar - '0' : NUM_10 + Character.toLowerCase(actualChar) - 'a') & ASC_0XF;
                    bytePattern = (hb << NUM_4) | lb;
                    break;

                case '+':
                    bytePattern = ' ';
                    break;

                default:
                    bytePattern = actualChar;
                    break;

            }

            if ((bytePattern & ASC_0XC0) == ASC_0X80) { // 10xxxxxx
                sumb = (sumb << NUM_6) | (bytePattern & ASC_0X3F);
                if (--more == 0) {
                    buffer.append((char) sumb);
                }
            } else if ((bytePattern & ASC_0X80) == ASC_0X00) { // 0xxxxxxx
                buffer.append((char) bytePattern);
            } else if ((bytePattern & ASC_0XE0) == ASC_0XC0) { // 110xxxxx
                sumb = bytePattern & ASC_0X1F;
                more = NUM_1;
            } else if ((bytePattern & ASC_0XF0) == ASC_0XE0) { // 1110xxxx
                sumb = bytePattern & ASC_0X0F;
                more = NUM_2;
            } else if ((bytePattern & ASC_0XF8) == ASC_0XF0) { // 11110xxx
                sumb = bytePattern & ASC_0X07;
                more = NUM_3;
            } else if ((bytePattern & ASC_0XFC) == ASC_0XF8) { // 111110xx
                sumb = bytePattern & ASC_0X03;
                more = NUM_4;
            } else { // 1111110x
                sumb = bytePattern & ASC_0X01;
                more = NUM_5;
            }
        }
        return buffer.toString();
    }
}
