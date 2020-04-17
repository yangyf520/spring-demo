package com.example.demo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.filter.DemoFilter;
import com.example.demo.listener.DemoListener;
import com.example.demo.serverlet.DemoServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

/**
 * 配置理发师
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加类型转换器和格式化器
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new USLocalDateFormatter());
    }

    /**
     * 跨域支持
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }

    /**
     * 添加静态资源--过滤swagger-api (开源的在线API文档)
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //过滤swagger
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");

        registry.addResourceHandler("/swagger/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger*");

        registry.addResourceHandler("/v2/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/v2/api-docs/");

    }

    /**
     * 配置消息转换器--这里我用的是alibaba 开源的 fastjson
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //5.将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new ReqInterceptor()).addPathPatterns("/**");
//    }

//    @Bean
//    public FilterRegistrationBean getDemoFilter(){
//        DemoFilter demoFilter=new DemoFilter();
//        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
//        registrationBean.setFilter(demoFilter);
//        List<String> urlPatterns=new ArrayList<String>();
//        urlPatterns.add("/*");//拦截路径，可以添加多个
//        registrationBean.setUrlPatterns(urlPatterns);
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
//    @Bean
//    public ServletRegistrationBean getDemoServlet(){
//        DemoServlet demoServlet=new DemoServlet();
//        ServletRegistrationBean registrationBean=new ServletRegistrationBean();
//        registrationBean.setServlet(demoServlet);
//        List<String> urlMappings=new ArrayList<String>();
//        //访问，可以添加多个
//        urlMappings.add("/demoservlet");
//        registrationBean.setUrlMappings(urlMappings);
//        registrationBean.setLoadOnStartup(1);
//        return registrationBean;
//    }
//    @Bean
//    public ServletListenerRegistrationBean<EventListener> getDemoListener(){
//        ServletListenerRegistrationBean<EventListener> registrationBean
//                =new ServletListenerRegistrationBean<>();
//        registrationBean.setListener(new DemoListener());
//        // registrationBean.setOrder(1);
//        return registrationBean;
//    }

}

/**
 * ReqInterceptor
 */
//class ReqInterceptor extends HandlerInterceptorAdapter {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("Interceptor preHandler method is running !");
//        return super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("Interceptor postHandler method is running !");
//        super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("Interceptor afterCompletion method is running !");
//        super.afterCompletion(request, response, handler, ex);
//    }
//
//    @Override
//    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("Interceptor afterConcurrentHandlingStarted method is running !");
//        super.afterConcurrentHandlingStarted(request, response, handler);
//    }
//}

class USLocalDateFormatter implements Formatter<LocalDate> {
    public static final String US_PATTERN = "MM/dd/yyyy";
    public static final String NORMAL_PATTERN = "dd/MM/yyyy";

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return LocalDate.parse(s, DateTimeFormatter.ofPattern(getPattern(locale)));
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return DateTimeFormatter.ofPattern(getPattern(locale)).format(localDate);
    }

    //根据地域不同，用户输入方式不同
    public static String getPattern(Locale locale) {
        return isUnitedStates(locale) ? US_PATTERN : NORMAL_PATTERN;
    }

    //判断是否在美国
    private static boolean isUnitedStates(Locale locale) {
        return Locale.US.getCountry().equals(locale.getCountry());
    }

}