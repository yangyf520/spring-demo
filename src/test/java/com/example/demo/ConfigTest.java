package com.example.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public class ConfigTest extends BaseHttpTest {

    @Value("${my.greeting}")
    private String user;

    @Test
    public void getHello() {
        get("/Hello,SpringBoot!");
    }

    @Test
    public void getUserWithValue() {
        get("/getUserValue", "json:10");
    }

    @Test
    public void getUserWithConfig() {
        get("/getUserConfig");
    }

    @Test
    public void getUserWithPro() {
        get("/getUserPro");
    }

    @Test
    public void testProperty() {
        System.out.println("user: "+user);
    }
}
