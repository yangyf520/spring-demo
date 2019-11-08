package com.example.demo;

import com.example.demo.dao.RedisDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisTest extends BaseHttpTest {

    public static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    RedisDao redisDao;

    @Test
    public void testRedis() {
        redisDao.setKey("name", "forezp");
        redisDao.setKey("age", "11");
        logger.info(redisDao.getValue("name"));
        logger.info(redisDao.getValue("age"));
    }
}
