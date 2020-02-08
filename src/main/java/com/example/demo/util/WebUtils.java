package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * web工具类
 */
public final class WebUtils {
    private static final Logger LOG = LoggerFactory.getLogger(WebUtils.class);

    /**
     * constructor
     */
    private WebUtils() {

    }

    /**
     * 获取web路径
     *
     * @param servletContext ServletContext
     * @return String
     */
    public static String getWebAppPath(ServletContext servletContext) {
        String webPath = null;
        try {
            webPath = servletContext.getRealPath("/");
        }
        catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getMessage(), e);
            }
        }

        // 在weblogic上可能无法从上下文取到工程物理路径，所以改为下面的
        if (webPath == null) {
            try {
                webPath = getRelativeByResource(servletContext);
            }
            catch (Exception e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(e.getMessage(), e);
                }
            }
        }

        return webPath;
    }

    /**
     * 通过resource来取工程路径
     *
     * @param servletContext servletContext
     * @return String
     * @throws MalformedURLException Exception
     * @throws URISyntaxException    Exception
     */
    private static String getRelativeByResource(ServletContext servletContext) throws MalformedURLException, URISyntaxException {
        URL url = servletContext.getResource("/");
        String path = new File(url.toURI()).getPath();
        if (!path.endsWith("\\") && !path.endsWith("/")) {
            path += File.separator;
        }
        return path;
    }

    /**
     * 文件编码处理，根据不同的浏览器
     *
     * @param userAgent    浏览器
     * @param textToEncode 编码
     * @return contentDisposition
     * @throws UnsupportedEncodingException 使用了不支持的编码
     */
    public static String encodeContentDisposition(String userAgent, String textToEncode) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(userAgent) && userAgent.toUpperCase().contains(Constant.BROWSER_IE)) {
            textToEncode = URLEncoder.encode(textToEncode, Constant.URL_ENCODER_UTF8);
        } else {
            textToEncode = new String(textToEncode.getBytes(Constant.URL_ENCODER_UTF8), Constant.URL_ENCODER_ISO88591);
        }
        textToEncode = textToEncode.replaceAll("\\+", "%20");
        return textToEncode;
    }
}
