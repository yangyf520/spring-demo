package com.example.demo.util;

//import com.alibaba.fastjson.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPUtils {

    private static final Logger logger = LoggerFactory.getLogger(GZIPUtils.class);

    public static final String ENCODE_UTF8 = "UTF-8";

    public static byte[] compress(String str, String encoding) {
        if (Strings.isNullOrEmpty(str)) {
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(baos, 30000)) {
            gzip.write(str.getBytes(encoding));
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return baos.toByteArray();
    }

    public static byte[] compress(String str) {
        return compress(str, ENCODE_UTF8);
    }

    public static byte[] jsonAndCompress(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            compress(mapper.writeValueAsString(o));
        } catch (Exception e) {

        }

        return null;
    }
}