package com.example.demo.util;

import net.sf.cglib.core.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * 在BeanCopy时对枚举类和String之间的拷贝做特殊处理
 */
public class EnumStringConverter implements Converter {
    private static Map<String,Class> PRIMITIVE_TYPE_MAP = new HashMap<String,Class>();
    static
    {
        PRIMITIVE_TYPE_MAP.put("int", Integer.class );
        PRIMITIVE_TYPE_MAP.put("long", Long.class );
        PRIMITIVE_TYPE_MAP.put("double", Double.class );
        PRIMITIVE_TYPE_MAP.put("float", Float.class );
        PRIMITIVE_TYPE_MAP.put("boolean", Boolean.class);
        PRIMITIVE_TYPE_MAP.put("char", Character.class );
        PRIMITIVE_TYPE_MAP.put("byte", Byte.class );
        PRIMITIVE_TYPE_MAP.put("short", Short.class );
    }

    @Override
    public Object convert(Object value, Class target, Object context) {

        if (value != null) {
            if (value.getClass().equals(target)) {
                return value;
            }else if(target.isAssignableFrom(value.getClass())) {
                return value;
            }else if(target.isPrimitive()) {
                Class wrappedClass=PRIMITIVE_TYPE_MAP.get(target.getName());
                if(value.getClass().equals(wrappedClass)){
                    return value;
                }
            }else if (value instanceof String && target.isEnum()) {
                //从String转到枚举
                return Enum.valueOf(target, (String) value);
            } else if (value instanceof Enum && String.class.equals(target)) {
                //从枚举转到String
                return value.toString();
            }
        }

        return null;
    }
}
