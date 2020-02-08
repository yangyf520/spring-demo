package com.example.demo.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合类的工具类
 */
public final class CollectionUtils {


    private static final Logger LOG = LoggerFactory.getLogger(CollectionUtils.class);
    /**
     * 私有构造函数,防止误用
     */
    private CollectionUtils() {
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合类型的对象
     * @param <T>        类型参数
     * @return 如果集合对象为空或者集合的size为0就返回true, 否则返回false
     */
    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断是否为一个非空的数组
     *
     * @param array 传入的数组
     * @param <T>   数组的类型参数
     * @return 如果数组为null或者数组的长度为0就返回true, 否则返回false
     */
    public static <T> boolean isNotNullOrEmptyArray(T[] array) {
        return !(array == null || array.length == 0);
    }

    /**
     * 合并所有集合
     *
     * @param lists List类型的集合
     * @param <T>   集合的参数类型
     * @return 合并后的集合
     */
    public static <T> List<T> mergeAll(List<T>... lists) {
        List<T> mergedList = new ArrayList<T>();
        for (List<T> list : lists) {
            if (list != null && !list.isEmpty()) {
                mergedList.addAll(list.stream().filter(obj -> obj != null).collect(Collectors.toList()));
            }
        }
        return mergedList;
    }

    /**
     * 用数组创建一个ArrayList
     *
     * @param <T>  元素的类型
     * @param objs 元素
     * @return ArrayList
     */
    public static <T> List asList(T... objs) {
        if (objs == null) {
            return Collections.EMPTY_LIST;
        }
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, objs);
        return list;
    }

    /**
     * 创建简单Map的快捷方法
     *
     * @param key   键
     * @param value 值
     * @param <K>   键的类型
     * @param <V>   值的类型
     * @return Map
     */
    public static <K, V> Map<K, V> makeMap(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    /**
     * 创建简单Map的快捷方法
     *
     * @param key1   键1
     * @param value1 值1
     * @param key2   键2
     * @param value2 值2
     * @param <K>    键的类型
     * @param <V>    值的类型
     * @return Map
     */
    public static <K, V> Map<K, V> makeMap(K key1, V value1, K key2, V value2) {
        Map<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    /**
     * 数组包含
     *
     * @param array 数组
     * @param val   被包含值
     * @param <T>   类型
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T val) {
        for (T t : array) {
            if (val.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用分隔符将字符串数组连接起来，构成一个字符串
     *
     * @param words     字符串数组
     * @param seperator 分隔符
     * @return 返回用分隔符连起来的字符串
     */
    public static String join(Object[] words, String seperator) {
        StringBuilder sb = new StringBuilder();
        if (words != null) {
            for (int i = 0; i < words.length; i++) {
                sb.append(words[i]);
                if (i < words.length - 1) {
                    sb.append(seperator);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将集合中的元素用分隔符连起来，构成一个字符串
     *
     * @param collection 集合
     * @param seperator  分隔符
     * @return 返回用分隔符连起来的字符串
     */
    public static String join(Collection<?> collection, String seperator) {
        Object[] objs = new Object[collection.size()];
        collection.toArray(objs);
        return join(objs, seperator);
    }

    /**
     * 根据集合中对象属性值查找元素
     *
     * @param collection    给定的集合
     * @param propertyName  集合中的元素的属性名
     * @param propertyValue 集合中元素属性名对应的属性值
     * @param <T>           集合的类型参数
     * @return 返回匹配的第一个元素
     */
    @SuppressWarnings("unchecked")
    public static <T> T find(Collection<T> collection, String propertyName, Object propertyValue) {
        List<T> list = org.apache.commons.collections4.ListUtils.select(collection, t -> {
            try {
                return PropertyUtils.getProperty(t, propertyName).equals(propertyValue);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                LOG.error("get object by property value error", e);
            }
            return false;
        });
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据集合中对象属性值查找（多个属性值）
     *
     * @param collection       给定的集合
     * @param propertyValueMap 属性名称-值 map
     * @param <T>              集合的类型参数
     * @return 匹配的第一个元素
     */
    @SuppressWarnings("unchecked")
    public static <T> T find(Collection<T> collection, Map<String, Object> propertyValueMap) {
        Predicate[] predicates = new BeanPropertyValueEqualsPredicate[propertyValueMap.size()];
        int index = 0;
        Set<Map.Entry<String, Object>> propertyValueEntrySet = propertyValueMap.entrySet();
        for (Map.Entry<String, Object> propertyValueEntry : propertyValueEntrySet) {
            predicates[index] = new BeanPropertyValueEqualsPredicate(propertyValueEntry.getKey(), propertyValueEntry.getValue());
            index++;
        }
        Predicate allPredicate = new AllPredicate(predicates);
        return (T) org.apache.commons.collections.CollectionUtils.find(collection, allPredicate);
    }

    /**
     * 返回由指定属性名字的值构成的ArrayList集合 在集合中取每一个对象，将对象指定属性名的值拿出来，重新构成一个ArrayList
     *
     * @param collection   给定的集合
     * @param propertyName 集合中元素的某一个属性名
     * @param <T>          给定集合的类型参数
     * @param <PropType>   属性的类型
     * @return 返回新的集合ArrayList
     */
    public static <T, PropType> Collection<PropType> collect(Collection<T> collection, String propertyName) {
        List<PropType> list = Lists.newArrayList();
        try {
            for(T t:collection){
                PropType p = (PropType) PropertyUtils.getProperty(t,propertyName);
                list.add(p);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("get properties list error", e);
        }
        return list;
    }

    /**
     * 复制一个数组
     *
     * @param sourceArray 要复制的目标数组
     * @param <T>         要复制的数组泛型类型
     * @return 新的数组
     */
    public static <T> T[] copy(T[] sourceArray) {
        if (sourceArray == null) {
            return null;
        }
        return Arrays.copyOf(sourceArray, sourceArray.length);
    }
    /**
     * 比较两个列表
     * @param list1
     * @param list2
     * @return
     */
    public static boolean equalsList(List<String> list1, List<String> list2){
        if(list1.size() != list2.size()){
            return false;
        }else{
            for(int i = 0; i < list1.size(); i++){
                if(list1.get(i) == null ? list2.get(i) != null : !list1.get(i).equals(list2.get(i))){
                    return false;
                }
            }
        }

        return true;
    }
    /**
     * 根据属性名过滤列表
     *
     * @param collection   Collection<T>
     * @param propertyName String
     * @param value        Object
     * @param <T>          Object
     * @return value
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> findByPropertyValue(Collection<T> collection, final String propertyName, final Object value) {
        return (List<T>) org.apache.commons.collections.CollectionUtils.select(collection, object -> {
            Object obj = BeanUtils.getProperty(object, propertyName);
            return value.equals(obj);
        });
    }

    public static <E> ArrayList<E> newArrayList(){
        return Lists.newArrayList();
    }


    public static <E> Set<E> newSet(){
        return Sets.newHashSet();
    }

    public static <K, V> Map<K, V> newMap(){
        return Maps.newHashMap();
    }
}
