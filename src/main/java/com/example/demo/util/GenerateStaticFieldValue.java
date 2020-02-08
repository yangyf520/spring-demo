package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * TODO: 这里需要写注释
 */
public final  class GenerateStaticFieldValue {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateStaticFieldValue.class.getName());

    //存储可以动态配置的属性
    private static final Properties STATIC_CONSTANT_VALUE_PROPERTIES=new Properties();
    static {
        if (STATIC_CONSTANT_VALUE_PROPERTIES.isEmpty()) {
            try {
                Resource in = new ClassPathResource("constant-config-value.properties");
                STATIC_CONSTANT_VALUE_PROPERTIES.load(in.getInputStream());
            } catch (IOException e) {
                LOG.error("constant-config-value.properties load error", e);
            }
        }
    }

    public static String getValue(String propertyName,String defaultValue){
        String result=(String)STATIC_CONSTANT_VALUE_PROPERTIES.get(propertyName);
        if(result==null){
            result= defaultValue;
        }
        return result;
    }

    public static Hashtable<Object,Object> getAllProperties(){
        return STATIC_CONSTANT_VALUE_PROPERTIES;
    }
}
