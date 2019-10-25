package com.example.demo.controller;

import com.example.demo.config.ConfigBean;
import com.example.demo.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableConfigurationProperties({ConfigBean.class, UserConfig.class})
@RestController
public class ConfigController {

    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    @Value("${my.name}")
    private String name;

    @Value("${my.age}")
    private int age;

    @Autowired
    @SuppressWarnings("all")
    ConfigBean configBean;

    @Autowired
    @SuppressWarnings("all")
    UserConfig userConfig;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(value = "/getUserValue")
    public String getUserWithValue() {
        return name + ":" + age;
    }

    @GetMapping(value = "/getUserConfig")
    public String getUserWithConfig() {
        return configBean.getNumber() + " | " + configBean.getMax() + " | " + configBean.getGreeting();
    }

    @GetMapping(value = "/getUserPro")
    public String getUserWithPro() {
        return userConfig.getName() + " | " + userConfig.getAge();
    }
}
