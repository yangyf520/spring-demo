package com.example.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener使用
 *
 */
@WebListener
public class IndexListener implements ServletContextListener {
    private Logger log = LoggerFactory.getLogger(IndexListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("IndexListener contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}