package com.example.demo.serverlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(DemoServlet.class);
    private static final long serialVersionUID = -4263672728918819141L;

    @Override
    public void init() throws ServletException {
        logger.info("...TestServlet init() init..........");
        super.init();
    }

    @Override
    public void destroy() {
        logger.info("...TestServlet init() destory..........");
        super.destroy();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("...TestServlet doPost() start..........");
        //操作attribute
        request.setAttribute("a", "a");
        request.setAttribute("a", "b");
        request.getAttribute("a");
        request.removeAttribute("a");
        //操作session
        request.getSession().setAttribute("a", "a");
        request.getSession().getAttribute("a");
        request.getSession().invalidate();
        logger.info("...TestServlet doPost() end..........");
    }
}


