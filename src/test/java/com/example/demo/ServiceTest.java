package com.example.demo;

import com.example.demo.metadata.MdPartParamVO;
import com.example.demo.metadata.MetaDataRedisService;
import com.example.demo.service.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTest extends BaseHttpTest {

    @Autowired
    MetaDataRedisService redisService;

    @Autowired
    AccountService accountService;

    @Test
    public void testCon() {

        System.out.println("连接成功：" + accountService.sqlConnect());
    }

    @Test
    public void testLoadPart() {
        MdPartParamVO param = new MdPartParamVO();
        param.setPartNo("666");
        redisService.loadPart(param);
    }
}


