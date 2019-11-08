package com.example.demo;

import com.example.demo.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class MongoTest extends BaseHttpTest {

    @Autowired
    ObjectMapper jonMapper;

    @Test
    public void mongoAdd() {
        post("/mongo/add", null);
    }

    @Test
    public void mongoGetUser() {
        MultiValueMap params = new LinkedMultiValueMap();
        params.add("name", "Alice");
        post("/mongo/getUser", params);
    }

    @Test
    public void deleteUser() throws IOException {

        MultiValueMap params = new LinkedMultiValueMap();
        params.add("name", "Alice");
        String user =  post("/mongo/getUser", params);

        Customer customer = jonMapper.readValue(user, Customer.class);
        params = new LinkedMultiValueMap();
        params.add("id", customer.getId());
        post("/mongo/deleteUser", params);
    }
}
