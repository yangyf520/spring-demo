package com.example.demo;

import com.example.demo.util.ValidationUtil;
import com.example.demo.vo.PersonForm;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserTest extends BaseHttpTest {

    @Test
    public void getUsers() {
        get("/user/list");
    }

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
    public void formValid() {
        MultiValueMap account = new LinkedMultiValueMap();
        account.add("age", 100);
        post("/valid", account);
    }

    @Test
    public void formCheck() {
        MultiValueMap account = new LinkedMultiValueMap();
        account.add("age", 35);
        get("/check", account);
    }

    @Test
    public void transfer() {
       //post("/account/transfer", null);
    }

    @Test
    public void testHibValid() throws IOException {
        PersonForm account = new PersonForm();
        account.setName("kalakala");
        account.setAge(88);
        account.setPassWord("密码");
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(account);
        if(validResult.hasErrors()){
            String errors = validResult.getErrors();
            System.out.println(errors);
        }
    }
}
