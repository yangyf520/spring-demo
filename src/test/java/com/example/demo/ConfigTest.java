package com.example.demo;

import org.junit.Test;

public class ConfigTest extends BaseHttpTest {

    @Test
    public void getHello() {
        get("/", "Greetings from Spring Boot!");
    }

    @Test
    public void getUserWithValue() {
        get("/getUserValue", "yangyf:28");
    }

    @Test
    public void getUserWithConfig() {
        get("/getUserConfig");
    }

    @Test
    public void getUserWithPro() {
        get("/getUserPro");
    }
}
