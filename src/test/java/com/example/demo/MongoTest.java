package com.example.demo;

import org.junit.Test;

public class MongoTest extends BaseHttpTest {

    @Test
    public void mongoAdd() {
        post("/mongo/add", null);
    }
}
