package com.example.demo.util;

import org.apache.commons.beanutils.BeanMap;

import java.lang.reflect.Method;

/**
 * 将对象转换成map
 */
public class DemoBeanMap extends BeanMap {
    /**
     * 构造器
     */
    public DemoBeanMap() {
    }

    /**
     * 构造器
     *
     * @param bean Object
     */
    public DemoBeanMap(Object bean) {
        super(bean);
    }

    @Override
    public Object get(Object name) {
        Object obj = super.get(name);
        if (obj instanceof Enum) {
            return obj.toString();
        } else {
            return obj;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object put(Object name, Object value) {
        if (this.getBean() == null) {
            return super.put(name, value);
        } else {
            Method method = getWriteMethod(name);
            if (method == null) {
                return super.put(name, value);
            }

            Class<?> parameterType = method.getParameterTypes()[0];
            if (parameterType.isEnum() && value instanceof String) {
                return Enum.valueOf((Class<Enum>) parameterType, (String) value);
            } else {
                return super.put(name, value);
            }

        }
    }
}
