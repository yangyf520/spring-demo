package com.example.demo.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String bean2Json(Object obj) {
        if (obj == null || ((Object[]) obj).length == 0) {
            return "";
        }

        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            mapper.writeValue(gen, obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                gen.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sw.toString();
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        if (jsonStr == null) {
            return null;
        }

        try {
            return mapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
