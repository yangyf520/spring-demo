package com.example.demo.util;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtils {

    private final static String BROWSER_IE = "MSIE";
    private final static String BROWSER_EDGE = "Edge";
    private final static String BROWSER_CHROME = "Chrome";
    private final static String BROWSER_FIREFOX = "Firefox";
    private final static String BROWSER_IE11 = "Trident/7.0";

    private final static String BROWSER_IE_SHOW = "Internet Explorer ";
    private final static String BROWSER_IE_11_SHOW = "Internet Explorer 11";

    private final static String OS_TIP_IN_USER_AGENT = "Windows";

    private final static String SEMICOLON = ";";
    private final static String BLANK = " ";

    /**
     *
     * @param userAgent
     * @return
     */
    public static String getClientBrowser(String userAgent) {
        if (isEmpty(userAgent)) {
            return "";
        }
        int i = 0;
        if ((i = userAgent.indexOf(BROWSER_IE)) > -1) {
            return BROWSER_IE_SHOW + userAgent.substring(i + 5, i + 6);
        }
        if ((i = userAgent.indexOf(BROWSER_EDGE)) > -1) {
            String edgeSplit = userAgent.substring(i);
            return edgeSplit.substring(0, findFirstSplit(edgeSplit, BLANK)).replace("/", " ");
        }
        if ((i = userAgent.indexOf(BROWSER_CHROME)) > -1) {
            String chromeSplit = userAgent.substring(i);
            return chromeSplit.substring(0, findFirstSplit(chromeSplit, BLANK)).replace("/", " ");
        }
        if ((i = userAgent.indexOf(BROWSER_FIREFOX)) > -1) {
            String firefoxSplit = userAgent.substring(i);
            return firefoxSplit.substring(0, findFirstSplit(firefoxSplit, BLANK)).replace("/", " ");
        }
        if (userAgent.contains(BROWSER_IE11)) {
            return BROWSER_IE_11_SHOW;
        }
        return "";
    }

    /**
     *
     * @param request
     * @return
     */
    public static String getClientBrowser(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        return getClientBrowser(request.getHeader("user-agent"));
    }

    public static String getClientOsKernel(String userAgent) {
        if (isEmpty(userAgent)) {
            return "";
        }
        int i = 0;
        if ((i = userAgent.indexOf(OS_TIP_IN_USER_AGENT)) > -1) {
            String split = userAgent.substring(i);
            return split.substring(0, findFirstSplit(split, SEMICOLON));
        }
        return "";
    }

    public static String getClientOsKernel(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        return getClientOsKernel(request.getHeader("user-agent"));
    }

    public static String getClientIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static int findFirstSplit(String str, String split) {
        if (isEmpty(str)) {
            return 0;
        }
        int i = str.indexOf(split);
        return i > -1 ? i : str.length();
    }
}
