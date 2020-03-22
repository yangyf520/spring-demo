/*
 * Copyright (c) 2019.
 */

package com.example.demo.serverlet;

import com.example.demo.util.ConstantUtils;
import com.example.demo.util.SpringBeanUtils;
import org.patchca.color.RandomColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.AdaptiveRandomWordFactory;
import org.patchca.word.RandomWordFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 验证码Servlet
 * <servlet>
 *  <servlet-name>captchaServlet</servlet-name>
 *  <servlet-class>com.cccis.casualty.foundation.security.CaptchaServlet</servlet-class>
 * </servlet>
 *
 * <servlet-mapping>
 *   <servlet-name>captchaServlet</servlet-name>
 *   <url-pattern>/captcha.sca</url-pattern>
 * </servlet-mapping>
 *
 * function changeCaptchaImage() {
 *   var $captchaImg = $('#captchaImg');
 *   var randomId = $.getRandom() + new Date().getTime();
 *   var CONTEXT_PATH = window.properties.SERVER_CONTEXT_PATH;
 *   $captchaImg.attr("src", CONTEXT_PATH + "/captcha.sca?width=100&height=24&random=" + randomId);
 *   window.localStorage.setItem("captchaRandom", randomId);
 *  }
 */
public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 170940408146541629L;

    /**
     * 字体size
     */
    private static final int FONT_SIZE = ConstantUtils.getConstantValue(25);

    public static final String CAPTCHA_RANDOM_ID_ATTRIBUTE = ConstantUtils.getConstantValue("random");

    /**
     * 创建随机数图片
     * @param request 请求
     * @param response 响应
     * @throws ServletException Exception
     * @throws IOException Exception
     */
    private void createRandomNumImg(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        int width = Integer.parseInt(request.getParameter("width"));
        int height = Integer.parseInt(request.getParameter("height"));

        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        //设置验证码字符范围和长度
        RandomWordFactory randomWordFactory = new AdaptiveRandomWordFactory();
        randomWordFactory.setCharacters("absdegkmnpwx23456789");
        randomWordFactory.setMaxLength(4);
        randomWordFactory.setMinLength(4);
        cs.setWordFactory(randomWordFactory);
        cs.setColorFactory(new RandomColorFactory());
        cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
        cs.setFontFactory(new RandomFontFactory(FONT_SIZE, new String[]{"Arial"}));
        if (width != 0) {
            cs.setWidth(width);
        }
        if (height != 0) {
            cs.setHeight(height);
        }

        //客户端提供的随机ID
        String randomCaptchaId = request.getParameter(CAPTCHA_RANDOM_ID_ATTRIBUTE);

        String captchaString = EncoderHelper.getChallangeAndWriteImage(cs, "PNG", response.getOutputStream());
        CacheManager cacheManager = (CacheManager) SpringBeanUtils.getBean("cacheManager");
        Cache captchaCache = cacheManager.getCache("captchaCache");
        Objects.requireNonNull(captchaCache).put(randomCaptchaId, captchaString);
    }

    /**
     * Servlet的get请求方法.
     * @param request 请求
     * @param response 响应
     * @throws ServletException Exception
     * @throws IOException Exception
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Servlet的post请求方法.
     * @param request 请求
     * @param response 响应
     * @throws ServletException Exception
     * @throws IOException Exception
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createRandomNumImg(request, response);
    }

}
