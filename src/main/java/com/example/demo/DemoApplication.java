package com.example.demo;

import com.example.demo.util.SpringContextUtil;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.Executor;

@EnableCaching // 缓存
@EnableScheduling // Scheduled
@EnableTransactionManagement // 事物
@ServletComponentScan // 理发师
@MapperScan("com.example.*.mapper") // Mapper
@EnableAsync // Async异步方法
@SpringBootApplication
public class DemoApplication extends AsyncConfigurerSupport {

    private final static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SpringContextUtil.setApplicationContext(context);
    }

    /**
     * 打印事物实现类
     *
     * @param transactionManager
     * @return
     */
    @Bean
    public Object printBean(PlatformTransactionManager transactionManager) {

        System.out.println(">>>" + transactionManager.getClass().getName());
        return new Object();
    }

    /**
     * 打印实例化Bean
     *
     * @param ctx
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            LOG.debug("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    /**
     * 异步方法任务
     * 重写AsyncConfigurerSupport的方法
     *
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

    /**
     * Tomcat配置
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        //端口号
        factory.setPort(8080);
        //编码
        factory.setUriEncoding(Charset.forName("utf-8"));
        factory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
        return factory;
    }

    /**
     * Tomcat连接池配置
     */
    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            // TODO Auto-generated method stub
            Http11NioProtocol handler = (Http11NioProtocol) connector.getProtocolHandler();
            handler.setAcceptCount(2000);//排队数
            handler.setMaxConnections(5000);//最大连接数
            handler.setMaxThreads(2000);//线程池的最大线程数
            handler.setMinSpareThreads(100);//最小线程数
            handler.setConnectionTimeout(30000);//超时时间       
        }

    }

}
