package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.Unmarshaller.Listener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * JaxbUnmarshallerListener
 */
public class JaxbUnmarshallerListener extends Listener {

    private static final int START_POINT = ConstantUtils.getConstantValue(254);

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterUnmarshal(Object target, Object parent) {
        processFieldLength(target);
    }

    /**
     * 字段长度截取
     *
     * @param target 目标对象
     */
    public void processFieldLength(Object target) {
        Field[] fields = FieldUtils.getAllFields(target.getClass());
        for (Field field : fields) {
            //只处理String类型
            if (!field.getType().equals(String.class)) {
                continue;
            }
            //字符串trim
            XmlFieldTrim trimAnnotation = field.getAnnotation(XmlFieldTrim.class);
            if (trimAnnotation != null) {
                try {
                    Method getMethod = target.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
                    String val = (String) getMethod.invoke(target);
                    if (val != null) {
                        val = val.trim();
                        Method setMethod = target.getClass().getMethod("set" + StringUtils.capitalize(field.getName()), String.class);
                        setMethod.invoke(target, val);
                    }
                } catch (Exception e) {
                    log.error("Annotation XmlFieldTrim Error!", e);
                }
            }

            FieldLength fieldLengthAnnotation = field.getAnnotation(FieldLength.class);
            if (fieldLengthAnnotation != null) {
                try {
                    Method getMethod = target.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
                    String val = (String) getMethod.invoke(target);
                    if (val != null) {
                        val = val.trim();
                        int length = fieldLengthAnnotation.value();
                        int cutLen = getCutLenByByte(val, length);
                        if (cutLen < val.length()) {
                            Method setMethod = target.getClass().getMethod("set" + StringUtils.capitalize(field.getName()), String.class);
                            setMethod.invoke(target, val.substring(0, cutLen));
                        }
                    }
                } catch (Exception e) {
                    log.error("Annotation FieldLength Error!", e);
                }
            }
        }
    }

    /**
     * 返回字符串要截取的位置
     *
     * @param s         要截取的字符串
     * @param byteCount 要截取的字节长度(中文当做两个字节)
     */
    private int getCutLenByByte(String s, int byteCount) {
        int strLen = s.length();
        if (strLen <= byteCount / 2) {
            return strLen;
        }
        int pos = 0; // 截取的位置
        char[] cs = s.toCharArray();
        int i = 0;
        while (pos < strLen) {
            if (cs[pos] > START_POINT) {
                i += 2;
            } else {
                i++;
            }
            if (i > byteCount) {
                break;
            }
            pos++;
        }
        return pos;
    }
}
