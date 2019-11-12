package com.example.demo;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class UserTest extends BaseHttpTest {

    @Test
    public void addUser() {
        MultiValueMap account = new LinkedMultiValueMap();
        account.add("name", "lake");
        account.add("money", 888888);
        post("/user/add", account);
    }

    @Test
    public void getUser() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");//传值，但要在url上配置相应的参数
        get("/user/1", params);
    }

    @Test
    public void transfer() {
//        post("/account/transfer", null);
    }
}
