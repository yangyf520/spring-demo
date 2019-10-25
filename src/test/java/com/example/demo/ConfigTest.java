package com.example.demo;

import org.junit.Test;

public class ConfigTest extends BaseTest {

    @Test
    public void getHello() {
        assertResult("/", "Greetings from Spring Boot!");
    }

    @Test
    public void getUserWithValue() {
        assertResult("/getUserValue", "yangyf:28");
    }

    @Test
    public void getUserWithConfig() {
        getResponse("/getUserConfig");
    }

    @Test
    public void getUserWithPro() {
        getResponse("/getUserPro");
    }
}
