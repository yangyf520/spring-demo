package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class DemoFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(DemoFilter.class);

    @Override
    public void destroy() {
        logger.info("..............execute TestFilter destory()..............");
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {
        logger.info("..............execute TestFilter doFilter()..............");
        arg2.doFilter(arg0, arg1);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.info("..............execute TestFilter  init()..............");
    }
}