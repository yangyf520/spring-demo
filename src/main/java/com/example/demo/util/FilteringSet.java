package com.example.demo.util;

import com.google.common.collect.Iterators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 一个Set包装原始Set, 过滤掉一些一些数据, 添加删除元素时都会向原始数据同步 注意: 性能不好, 不要用在大量集合的情况, 使用是尽量把size(), isEmpty()等解决缓存
 *
 * @param <T> 泛型
 */
public class FilteringSet<T> implements Set<T> {
    private static final Logger LOG = LoggerFactory.getLogger(FilteringSet.class);

    private Set<T> targetSet;

    private Filter<T> filter;

    /**
     * 构造函数.
     *
     * @param targetSet 要包装的set
     * @param filter    过滤器, 使用过滤器以后, 这个set看上去是targetSet的子集
     */
    public FilteringSet(Set<T> targetSet, Filter<T> filter) {
        this.targetSet = targetSet;
        this.filter = filter;
        /*
         * if (targetSet.size() > Constant.ONE_HUNDRED) { LOG.warn(
         * "the size of target set is {}, too big, don't use this class, it may involve performance issue"); }
         */
    }

    /**
     * 获取长度.
     *
     * @return size
     */
    @Override
    public int size() {
        int count = 0;
        for (T t : targetSet) {
            if (!filter.shouldRemove(t)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 判断是否为null.
     *
     * @return 如果为空 返回true，否则返回false
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 判断是否包含一个对象.
     *
     * @param o 任意的Object
     * @return boolean 如果包含返回true,否则返回false
     */
    @Override
    public boolean contains(Object o) {
        for (T t : targetSet) {
            if (!filter.shouldRemove(t) && t.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 遍历方法.
     *
     * @return 返回一个指定过滤规则的Iterator
     */
    @Override
    public Iterator<T> iterator() {
        final Iterator<T> targetIter = targetSet.iterator();
        return Iterators.filter(targetIter, t -> !filter.shouldRemove(t));
    }

    /**
     * 转换为数组
     *
     * @return 返回过滤后的集合的数组对象
     */
    @Override
    public Object[] toArray() {
        Set<T> set = getFilteredData();
        return set.toArray();
    }

    /**
     * @return
     */
    /**
     * 返回过滤后的集合的数组对象
     *
     * @param a   用于存储集合元素的数组
     * @param <K> 类型参数
     * @return 返回过滤后的集合的数组对象
     */
    @Override
    public <K> K[] toArray(K[] a) {
        Set<T> set = getFilteredData();
        return set.toArray(a);
    }

    /**
     * 添加元素
     *
     * @param t 被添加的元素
     * @return 是否集合包含了元素 t
     */
    @Override
    public boolean add(T t) {
        return targetSet.add(t);
    }

    /**
     * 删除元素
     *
     * @param o 元素
     * @return 是否集合包含了元素 t
     */
    @Override
    public boolean remove(Object o) {
        return targetSet.remove(o);
    }

    /**
     * 判断一个集合否包含另一个集合
     *
     * @param c 子集和
     * @return 如果包含返回true, 否则false
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            boolean exist = false;
            for (T t : targetSet) {
                if (!filter.shouldRemove(t) && o.equals(t)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将指定集合的所有元素添加到当前集合
     *
     * @param c 指定的集合
     * @return 是否添加成功
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return targetSet.addAll(c);
    }

    /**
     * 在当前集合中保留指定的集合元素
     *
     * @param c 被保留的元素集合
     * @return 返回的集合中删除了不需要保留的元素
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return targetSet.retainAll(c);
    }

    /**
     * 在当前集合中删除指定的元素
     *
     * @param c 需要删除的元素
     * @return 元素删除后的结果
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return targetSet.removeAll(c);
    }

    @Override
    public void clear() {
        targetSet.clear();
    }

    /**
     * 获取过滤后的结果
     *
     * @return
     */
    private Set<T> getFilteredData() {
        Set<T> set = new HashSet<T>();
        for (T t : targetSet) {
            if (!filter.shouldRemove(t)) {
                set.add(t);
            }
        }
        return set;
    }

    /**
     * interface 判定哪些元素需要过滤
     *
     * @param <T> 类型参数，需要被过滤的元素的类型
     */
    public interface Filter<T> {
        /**
         * 判定哪些元素需要过滤
         *
         * @param t 集合中的元素
         * @return boolean true需要过滤掉，false不用过滤
         */
        boolean shouldRemove(T t);
    }
}
