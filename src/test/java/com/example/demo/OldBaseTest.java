package com.example.demo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OldBaseTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        System.out.println(this.base);
    }

    public void getResponse(String path, Object... params) {
        ResponseEntity<String> response = template.getForEntity(base.toString() + path,
                String.class, params);
        System.out.println(response.getBody());
    }

    public void postResponse(String path, Object request, Object... params) {
        ResponseEntity<String> response = template.postForEntity(base.toString() + path,
                request, String.class, params);
        System.out.println(response.getBody());
    }

    public void assertResult(String path, String massge) {
        ResponseEntity<String> response = template.getForEntity(base.toString() + path,
                String.class);
        assertThat(response.getBody(), equalTo(massge));
    }
}
