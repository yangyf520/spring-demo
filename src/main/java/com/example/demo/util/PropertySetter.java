package com.example.demo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性设置器
 */
public interface PropertySetter {
    /**
     * 空Setter, 什么也不做
     */
    PropertySetter EMPTY = new PropertySetter() {
        private final Logger log = LoggerFactory.getLogger(PropertySetter.class);

        @Override
        public void setValue(Object bean, Object value) {
            if (log.isTraceEnabled()) {
                log.trace("setValue method");
            }
        }
    };

    /**
     * 向bean中设置属性值
     *
     * @param bean  目标对象
     * @param value 属性值
     */
    void setValue(Object bean, Object value);
}
