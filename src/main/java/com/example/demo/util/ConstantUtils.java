package com.example.demo.util;

/**
 * 常量工具类
 */
public final class ConstantUtils {
    private ConstantUtils() {

    }

    /**
     * 传入值，返回一个值，通过这种方式获取常量，可以避免增量发布时，引用常量的地方指向错误
     *
     * @param value 传入的作为常量的值
     * @param <T>   类型参数
     * @return 常量值
     */
    public static <T> T getConstantValue(T value) {
        return value;
    }
}
