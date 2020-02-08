package com.example.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http的请求工具
 * 目前仅为如下场景提供支持，如需处理其他情况，需要扩展
 * 1.请求：param传参
 * 2.返回：字符串
 * CLIENT_MAX_TOTAL：定义连接池的大小，请根据实际情况设定。
 */
public class HttpClientUtils {
    private static Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);

    private final static int CLIENT_MAX_TOTAL = 5;
    private final static int TIMEOUT_CONNECTION = 1000 * 10;

    private final static CloseableHttpClient httpClient = getClient();

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, Map<String, String> param) {
        HttpPost httpPost = new HttpPost(url);
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        if (param != null && param.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = param.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.defaultCharset()));

        try {
            return httpClient.execute(httpPost, new CccResponseHandler());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return "";
    }

    /**
     * 发送get请求
     * @param url
     * @return
     */
    public static String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);

        try {
            return httpClient.execute(httpGet, new CccResponseHandler());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "";
    }

    private static class CccResponseHandler implements ResponseHandler<String> {
        @Override
        public String handleResponse(HttpResponse response) throws IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : "";
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }

    private static CloseableHttpClient getClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(CLIENT_MAX_TOTAL);
        cm.setDefaultMaxPerRoute(CLIENT_MAX_TOTAL);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }
}
