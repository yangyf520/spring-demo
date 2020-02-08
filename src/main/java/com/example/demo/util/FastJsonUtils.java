package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

/**
 * JsonUtil转化处理
 */
public final class FastJsonUtils {
    private FastJsonUtils() {

    }

    /**
     * 将对象转换为json字符串.
     *
     * @param obj 被转换的对象
     * @param <T> 类型参数
     * @return json字符串
     */
    public static <T> String convertObject2JSONString(T obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 通过指定序列化参数，将对象转换为json字符串
     *
     * @param obj      被转换的对象
     * @param features 序列化参数
     * @param <T>      类型参数
     * @return json字符串
     */
    public static <T> String convert2FormatJSONString(T obj, SerializerFeature... features) {
        return JSON.toJSONString(obj, features);
    }

    /**
     * 将json串转换为类型为className的对象. 处理如下json：{'field1':1,'field2':'a'}
     *
     * @param <T>        类型参数
     * @param jsonString json字符串
     * @param className  对象的Class类型对象
     * @return 对象
     */
    public static <T> T convertJSONString2Object(String jsonString, Class<T> className) {
        return JSON.parseObject(jsonString, className);
    }

    /**
     * 将json串转换为类型为className的对象集合. 处理如下json：[{'field1':1,'field2':'a'},{'field1':2,'field2':'b'}]
     *
     * @param <T>        类型参数
     * @param jsonString json字符串
     * @param className  Class类型对象
     * @return 转换成的list
     */
    public static <T> List<T> convertJSONString2Collection(String jsonString, Class<T> className) {
        return JSON.parseArray(jsonString, className);
    }

    /**
     * JSONObject转Map<String,String>.
     *
     * @param jsonObject jsonObject
     * @return map
     */
    public static Map<String, String> convertJSONObject2Map(JSONObject jsonObject) {
        if (jsonObject != null) {
            Map<String, String> result = new HashMap<String, String>();
            Iterator<String> iterator = jsonObject.keySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = jsonObject.getString(key);
                result.put(key, value);
            }
            return result;
        }
        return null;
    }

    /**
     * JSONObject转Map<String,Object>.
     *
     * @param jsonObject jsonObject
     * @return map
     */
    public static Map<String, Object> convertJSONObject2ObjectMap(JSONObject jsonObject) {
        if (jsonObject != null) {
            Map<String, Object> result = new HashMap<>();
            Set<Map.Entry<String, Object>> jsonObjectEntries = jsonObject.entrySet();
            for (Map.Entry<String, Object> jsonObjectEntry : jsonObjectEntries) {
                result.put(jsonObjectEntry.getKey(), jsonObjectEntry.getValue());
            }
            return result;
        }
        return null;
    }

    /**
     * 将包含JsonObject/JsonArray的Map<String, Object>转换为不包含JsonObject/JsonArray的Map的Map<String, Object>
     * @param map
     * @return
     */
    public static Map<String, Object> convertMap2ObjectMap(Map<String, Object> map) {
        if (map != null) {
            Map<String, Object> result = new HashMap<>();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof JSONObject) {
                    value = convertMap2ObjectMap((JSONObject) value);
                }
                if (value instanceof JSONArray) {
                    value = convertList2ObjectList((JSONArray) value);
                }
                result.put(key, value);
            }
            return result;
        }
        return null;
    }

    /**
     * jsonArray转List<Map<String, String>>.
     * 将包含JsonObject/jsonArray的List<Object>转换为不包含JsonObject/jsonArray的List<Object>
     *
     * @param list
     * @return 转换后的list
     */
    public static List<Object> convertList2ObjectList(List<Object> list) {
        List<Object> result = new ArrayList<Object>();
        for (Object object : list) {
            if (object instanceof JSONArray) {
                object = convertList2ObjectList((JSONArray) object);
            }
            if (object instanceof JSONObject) {
                object = convertMap2ObjectMap((JSONObject) object);
            }
            result.add(object);
        }
        return result;
    }

    /**
     * jsonStr转Map<String,Object>.
     *
     * @param jsonStr
     * @return map
     */
    public static Map<String, Object> convertJSONString2ObjectMap(String jsonStr) {
        return JSON.parseObject(jsonStr, new TypeReference<HashMap<String, Object>>() {});
    }
    /**
     * jsonArray转List<Map<String, String>>.
     *
     * @param jsonArray json数组
     * @return 转换后的list
     */
    public static List<Map<String, String>> convertJSONArray2ListMap(JSONArray jsonArray) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (int i = 0, len = jsonArray.size(); i < len; i++) {
            result.add(convertJSONObject2Map(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Map<String, String>转字符串.
     *
     * @param map Map<String, String>对象
     * @return json字符串
     */
    public static String convertMapToJSONString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            stringBuilder.append("\"");
            stringBuilder.append(entry.getKey());
            stringBuilder.append("\"");
            stringBuilder.append(":");
            stringBuilder.append("\"");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\"");
            stringBuilder.append(",");
        }
        String result = stringBuilder.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result + "}";
    }
}
