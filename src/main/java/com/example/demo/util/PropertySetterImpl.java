package com.example.demo.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 属性设置器的实现
 */
public class PropertySetterImpl implements PropertySetter {

    private static final Logger LOG = LoggerFactory.getLogger(PropertySetterImpl.class);

    private Method setter;

    private String name;

    private Class<?> targetType;

    private Method valueOf;

    /**
     * constructor
     *
     * @param setter Method
     * @param name   名称
     */
    public PropertySetterImpl(Method setter, String name) {
        this.setter = setter;
        this.name = name;
        this.targetType = setter.getParameterTypes()[0];
        if (this.targetType.isEnum()) {
            try {
                this.valueOf = this.targetType.getDeclaredMethod("valueOf", String.class);
            }
            catch (NoSuchMethodException e) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void setValue(Object bean, Object value) {
        try {
            if (value == null || this.targetType.isAssignableFrom(value.getClass())) {
                this.setter.invoke(bean, value);
            } else {
                if (this.valueOf != null) {
                    if (this.targetType.isEnum()) {
                        //只是针对枚举类型
                        this.setter.invoke(bean, this.valueOf.invoke(this.targetType, value.toString()));
                    } else {
                        this.setter.invoke(bean, this.valueOf.invoke(this.targetType, value));
                    }
                } else {
                    this.setter.invoke(bean, ConvertUtils.convert(value, this.targetType));
                }
            }
        }
        catch (Exception e) {
            LOG.warn("error on setting property {} to bean {} with value {}: {}", this.name, bean, value, e.getMessage());
        }
    }
}
