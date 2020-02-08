package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Enum工具包.
 */
public final class EnumUtils {
    private static final Logger LOG = LoggerFactory.getLogger(EnumUtils.class);

    private EnumUtils() {

    }

    /**
     * 通过指定枚举类型的Class，指定枚举常量的name，返回指定名字的枚举常量 在ccc系统中应用时，name为枚举常量的value 如ClaimStatus.IN_BATCH_POOL的value为"00"。
     *
     * @param enumClass 枚举类型Class对象
     * @param value     枚举常量的name
     * @param <T>       枚举类型(参数变量)
     * @return 枚举常量
     */
    public static <T extends Enum<T>> T getValueOf(Class<T> enumClass, String value) {
        for (T enumValue : enumClass.getEnumConstants()) {
            if (enumValue.toString().equals(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException(value + " is not valid of enum " + enumClass);
    }

    /**
     * 修改Enum的name
     *
     * @param enumInstance 枚举常量
     * @param value        枚举常量的value
     * @param <T>          枚举的类型参数
     */
    public static <T extends Enum<T>> void changeNameTo(T enumInstance, String value) {
        try {
            Field fieldName = enumInstance.getClass().getSuperclass().getDeclaredField("name");
            fieldName.setAccessible(true);
            fieldName.set(enumInstance, value);
            fieldName.setAccessible(false);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 枚举类型的比较，包括null
     *
     * @param enum1 枚举常量1
     * @param enum2 枚举常量2
     * @return 如果相等返回true, 否则返回false
     */
    public static boolean enumEquals(Enum enum1, Enum enum2) {
        if (enum1 == null && enum2 == null) {
            return true;
        } else if (enum1 != null) {
            return enum1.equals(enum2);
        } else {
            return false;
        }
    }
}
