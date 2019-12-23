package com.example.demo;

import com.example.demo.service.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTest extends BaseHttpTest {

    @Autowired
    AccountService accountService;

    @Test
    public void testCon() {

        System.out.println("连接成功：" + accountService.sqlConnect());
    }
}
