package com.example.demo.util;

/**
 * 异常相关的工具类
 */
public final class ExceptionUtils {

    private ExceptionUtils() {

    }

    /**
     * 判断一个异常是否是由另一个异常引起的
     *
     * @param e   要判断的异常
     * @param clz 另一个异常的类
     * @return true/false
     */
    public static boolean causeBy(Throwable e, Class<? extends Throwable> clz) {
        if (e.getClass().equals(clz)) {
            return true;
        } else {
            Throwable t = e.getCause();
            return t != null && causeBy(t, clz);
        }
    }

    /**
     * 取得一个异常的引起原因
     *
     * @param e   异常
     * @param clz 另一个异常类
     * @return 异常
     */
    public static Throwable getCauseBy(Throwable e, Class<? extends Throwable> clz) {
        if (e.getClass().equals(clz)) {
            return e;
        } else {
            Throwable t = e.getCause();
            if (t != null) {
                return getCauseBy(t, clz);
            } else {
                return null;
            }
        }
    }

    /**
     * 取得根异常
     *
     * @param e 异常
     * @return 跟异常
     */
    public static Throwable getRootCause(Throwable e) {
        if (e.getCause() == null) {
            return e;
        }
        return org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(e);
    }
}
