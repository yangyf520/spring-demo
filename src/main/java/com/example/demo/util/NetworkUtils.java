package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;
import java.util.UUID;

/**
 * User: Nika Date: 13-12-18 Time: 下午3:34
 */
public final class NetworkUtils {
    private final static Logger LOG = LoggerFactory.getLogger(NetworkUtils.class);

    /**
     * 随机生成的Host名, 当系统取不到实际的Host时使用这个
     */
    private final static String RANDOM_HOST = "UnknownHost-" + UUID.randomUUID();

    /**
     * 网卡的默认前缀名称
     */
    private final static String ETHERNET_ADDR_PREFIX = "eth";

    /**
     * 默认的本机IP地址
     */
    private final static String DEFAULT_IP_ADDR = "127.0.0.1";

    private final static String CURRENT_IP_ADDRESS = getIpAddrUnderVirtualInternal();

    private NetworkUtils() {

    }

    /**
     * 取得服务器的机器名
     *
     * @return 服务器的机器名
     */
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            LOG.warn(e.getMessage(), e);
            return RANDOM_HOST;
        }
    }

    /**
     * 取得服务器的机器IP
     *
     * @return 服务器的机器名
     */
    public static String getLocalIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            LOG.warn(e.getMessage(), e);
            return RANDOM_HOST;
        }
    }

    /**
     * 获取客户端IP地址.
     *
     * @param request servlet请求
     * @return 字符串
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * 多网卡情况下，获取第一个有效的IP地址;在虚拟机环境下，建议使用该方法.
     *
     * @return 字符串
     */
    public static String getIpAddrUnderVirtual() {
        return CURRENT_IP_ADDRESS;
    }

    private static String getIpAddrUnderVirtualInternal() {
        String ipAdress = RANDOM_HOST;
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException e) {
            LOG.error("获取IP地址失败!", e);
            return ipAdress;
        }
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            if (iface.getName().startsWith(ETHERNET_ADDR_PREFIX)) {
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr != null && addr instanceof Inet4Address) {
                        ipAdress = addr.getHostAddress();
                        if (ipAdress.indexOf(DEFAULT_IP_ADDR) != -1) {
                            LOG.debug("interface name is:{}", iface.getName());
                            LOG.debug("ip address is :{}", addr.getHostAddress());
                            break;
                        }
                    }
                }
            }
        }
        return ipAdress;
    }
}
