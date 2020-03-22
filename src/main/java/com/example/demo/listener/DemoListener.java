package com.example.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DemoListener implements
        HttpSessionListener, ServletRequestListener, ServletRequestAttributeListener {
    private Logger logger = LoggerFactory.getLogger(DemoListener.class);

    //sessionListener start!
    public void sessionCreated(HttpSessionEvent arg0) {
        logger.info(".......TestListener sessionCreated().......");
    }

    public void sessionDestroyed(HttpSessionEvent arg0) {
        logger.info(".......TestListener sessionDestroyed().......");
    }
    //sessionListener end!

    //requestListener start!
    public void requestInitialized(ServletRequestEvent arg0) {
        logger.info("......TestListener requestInitialized()......");
    }

    public void requestDestroyed(ServletRequestEvent arg0) {
        logger.info("......TestListener requestDestroyed()......");
    }
    //requestListener end!

    //attributeListener start!
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        logger.info("......TestListener attributeAdded()......");
    }

    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        logger.info("......TestListener attributeRemoved()......");
    }

    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        logger.info("......TestListener attributeReplaced()......");
    }
    //attributeListener end!
}