package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个日志的工具类只是给规则体里使用的, 其他地方不要使用. 其他地方请用Logger LOG = LoggerFactory.getLogger(class)的方式获取Logger
 */
public final class LogUtils {
    private static final Logger LOG = LoggerFactory.getLogger(LogUtils.class);

    private LogUtils() {

    }

    /**
     * debug方法打印.
     *
     * @param msg 信息
     */
    public static void debug(String msg) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(msg);
        }
    }

    /**
     * 格式化打印debug级别的日志信息
     *
     * @param msg  debug信息的格式
     * @param objs debug信息的参数
     */
    public static void debug(String msg, Object... objs) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(msg, objs);
        }
    }

    /**
     * 打印info级别的日志信息
     *
     * @param msg info message
     */
    public static void info(String msg) {
        if (LOG.isInfoEnabled()) {
            LOG.info(msg);
        }
    }

    /**
     * 格式化打印info级别的日志信息
     *
     * @param msg  格式
     * @param objs 参数
     */
    public static void info(String msg, Object... objs) {
        if (LOG.isInfoEnabled()) {
            LOG.info(msg, objs);
        }
    }

    /**
     * 打印trace级别的日志信息
     *
     * @param msg trace massage
     */
    public static void trace(String msg) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(msg);
        }
    }

    /**
     * 格式化打印trace级别的日志信息
     *
     * @param msg  格式
     * @param objs 参数
     */
    public static void trace(String msg, Object objs) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(msg, objs);
        }
    }

    /**
     * 打印warn级别的日志信息
     *
     * @param msg message
     */
    public static void warn(String msg) {
        if (LOG.isWarnEnabled()) {
            LOG.warn(msg);
        }
    }

    /**
     * 格式化打印warn级别的日志信息
     *
     * @param msg  格式
     * @param objs 参数
     */
    public static void warn(String msg, Object... objs) {
        if (LOG.isWarnEnabled()) {
            LOG.warn(msg, objs);
        }
    }

    /**
     * 打印error级别的日志信息
     *
     * @param msg message
     */
    public static void error(String msg) {
        if (LOG.isErrorEnabled()) {
            LOG.error(msg);
        }
    }

    /**
     * 格式化打印warn级别的日志信息
     *
     * @param msg  格式
     * @param objs 参数
     */
    public static void error(String msg, Object... objs) {
        if (LOG.isErrorEnabled()) {
            LOG.error(msg, objs);
        }
    }

    /**
     * 你可以用这个函数打印when条件中的内容
     *
     * @param obj 要打印的内容
     * @param <T> 类型参数
     * @return 要打印的内容
     */
    public static <T> T noop(T obj) {
        LOG.info("{}", obj);
        return obj;
    }
}
