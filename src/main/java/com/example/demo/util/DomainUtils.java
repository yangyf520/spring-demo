package com.example.demo.util;

import javax.persistence.Entity;

/**
 * Domain相关工具类
 */
public final class DomainUtils {
    private DomainUtils() {

    }

    /**
     * 指定类是不是JPA的Domain类
     *
     * @param targetClass 指定类
     * @param <T>         类型
     * @return 是不是JPA的Domain类
     */
    public static <T> boolean isEntity(Class<T> targetClass) {
        return targetClass.isAnnotationPresent(Entity.class);
    }
}
