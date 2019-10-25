package com.example.demo;

import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseHttpTest {

    private String basePath;

    @LocalServerPort
    private int port;

    RestTemplate restTemplate;

    AsyncRestTemplate asyncRestTemplate;

    @Before
    public void setup() throws Exception {

        URL base = new URL("http://localhost:" + port);
        basePath = base.toString();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);

        restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * GET请求
     */
    public String get(String uri, Object... params) {
        String result = restTemplate.getForObject(basePath + uri, String.class, params);
        System.out.println(result);
        return result;
    }

    /**
     * POST请求
     *
     * @param uri    /请求地址/方法
     * @param params 请求参数
     * @return
     */
    public String post(String uri, MultiValueMap params) {
        String result = restTemplate.postForObject(basePath + uri, params, String.class);
        System.out.println(result);
        return result;
    }

    /**
     * DELETE请求
     *
     * @throws Exception
     */
    public void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", "xxxxx");
        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("username", "lake");
        HttpEntity formEntity = new HttpEntity(multiValueMap, headers);
        String[] urlVariables = new String[]{"admin"};
        ResponseEntity<String> result = restTemplate.exchange("/test/delete?username={username}", HttpMethod.DELETE, formEntity, String.class, urlVariables);
    }

    /**
     * 异步调用要使用AsyncRestTemplate。
     * 它是RestTemplate的扩展，提供了异步http请求处理的一种机制，
     * 通过返回ListenableFuture对象生成回调机制，以达到异步非阻塞发送http请求
     *
     * @return
     */
    public String asyncReq() {
        String url = "http://localhost:8080/jsonAsync";
        ListenableFuture<ResponseEntity<JSONObject>> future = asyncRestTemplate.getForEntity(url, JSONObject.class);
        future.addCallback(new SuccessCallback<ResponseEntity<JSONObject>>() {
            public void onSuccess(ResponseEntity<JSONObject> result) {
                System.out.println(result.getBody().toJSONString());
            }
        }, new FailureCallback() {
            public void onFailure(Throwable ex) {
                System.out.println("onFailure:" + ex);
            }
        });
        return "this is async sample";
    }

    /**
     * 设置请求头
     *
     * @throws Exception
     */
    public void putHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", "xxxxxx");
        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("username", "lake");
        HttpEntity formEntity = new HttpEntity(multiValueMap, headers);
        ResponseEntity<String> result = restTemplate.exchange("/test/putHeader", HttpMethod.PUT, formEntity, String.class);
    }

    /**
     * 获取请求头信息
     *
     * @throws Exception
     */
    public void getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", "xxxxxx");
        HttpEntity formEntity = new HttpEntity(headers);
        String[] urlVariables = new String[]{"admin"};
        ResponseEntity<String> result = restTemplate.exchange("/test/getHeader?username={username}", HttpMethod.GET, formEntity, String.class, urlVariables);
    }

    /**
     * 上传文件
     *
     * @return
     */
    public String upload() {
        Resource resource = new FileSystemResource("/home/lake/github/wopi/build.gradle");
        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("username", "lake");
        multiValueMap.add("files", resource);
        return restTemplate.postForObject("/test/upload", multiValueMap, String.class);
    }

    /**
     * 文件下载
     *
     * @throws Exception
     */
    public void download() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", "xxxxxx");
        HttpEntity formEntity = new HttpEntity(headers);
        String[] urlVariables = new String[]{"admin"};
        ResponseEntity<byte[]> response = restTemplate.exchange("/test/download?username={1}", HttpMethod.GET, formEntity, byte[].class, urlVariables);
        if (response.getStatusCode() == HttpStatus.OK) {
            File file = new File("/home/lake/github/file/test.gradle");
            if (file != null && file.exists()) {
                FileOutputStream fop = null;
                try {
                    fop = new FileOutputStream(file);
                    fop.write(response.getBody());
                    fop.flush();
                    fop.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(e);
                } finally {
                    fop.close();
                }
            }
        }
    }

}
