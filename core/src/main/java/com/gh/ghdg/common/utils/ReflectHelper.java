package com.gh.ghdg.common.utils;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.sysMgr.bean.Unique;
import com.gh.ghdg.common.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 反射工具类
 */
public class ReflectHelper {

    /**
     * 取 Field 的 Getter
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Method getGetter(Class clazz, String fieldName) {
        fieldName = StrUtil.upperFirst(fieldName);
        String getName = "get" + fieldName;
        String isName = "is" + fieldName;
        Class<?> cls = clazz;
        while(null != cls) {
            for(Method f : cls.getDeclaredMethods()) {
                String name = f.getName();
                if(f.getParameterCount() == 0 && (name.equals(getName) || name.equals(isName))) {
                    return f;
                }
            }
            cls = cls.getSuperclass();
        }
        throw new RuntimeException(clazz + " 不存在方法 " + getName + "/" + isName);
    }


    /**
     * 循环取 Field
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getField(Class clazz, String fieldName) {
        Class<?> cls = clazz;
        while(null != cls) {
            for(Field f : cls.getDeclaredFields()) {
                if(f.getName().equals(fieldName)) {
                    return f;
                }
            }
            cls = cls.getSuperclass();
        }
        throw new RuntimeException(clazz + " 不存在域 " + fieldName);
    }

    /**
     * 循环取 Field
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getField(Object obj, String fieldName) {
        return getField(obj.getClass(), fieldName);
    }

    /**
     * 取域值
     * @param obj
     * @param fieldName
     * @throws Exception
     */
    public static <T>T getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        boolean accessible = field.isAccessible();
        if(!accessible) {
            field.setAccessible(true);
        }
        Object value = field.get(obj);
        if(!accessible) {
            field.setAccessible(false);
        }
        return (T)value;
    }

    /**
     * 取普通字段名：非 List、Map、BaseEntity
     * @param clazz
     * @return
     */
    private final static List<String> ignoreFields = Arrays.asList("lastModifiedDate0", "sort", "filter", "limit", "page");

    public static List<String> getPlainFieldNames(Class clazz) {
        List<String> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                String name = f.getName();
                Class type = f.getType();
                if(!ignoreFields.contains(name) && !fields.contains(name)) {
                    // 不是 BaseEntity、Collection、Map
                    if(!BaseEntity.class.isAssignableFrom(type) && !Collection.class.isAssignableFrom(type) && !Map.class.isAssignableFrom(type)) {
                        fields.add(name);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 取字段名
     * @param clazz
     * @return
     */
    public static List<String> getFieldNames(Class clazz) {
        List<String> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                String name = f.getName();
                if(!ignoreFields.contains(name) && !fields.contains(name)) {
                    fields.add(name);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 取值
     * @param obj
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static <T>T getValue(Object obj, String fieldName) throws Exception {
        return (T)getGetter(obj.getClass(), fieldName).invoke(obj);
    }

    /**
     * 取值
     * @param obj
     * @param field
     * @throws Exception
     */
    public static <T>T getValue(Object obj, Field field) throws Exception {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        Object o = field.get(obj);
        field.setAccessible(access);
        return (T)o;

        // return getValue(obj, field.getName());
    }

    /**
     * 取值
     * @param obj
     * @param dotedFieldName，如 workshop.corp
     * @return
     * @throws Exception
     */
    public static Object getDotedValue(Object obj, String dotedFieldName) throws Exception {
        if(obj == null) {
            throw new RuntimeException("对象空");
        }
        for(String fieldName : StrUtil.split(dotedFieldName,'.')) {
            obj = getValue(obj, fieldName);
            if(obj == null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 设值
     * @param obj
     * @param field
     * @param value
     * @throws Exception
     */
    public static void setValue(Object obj, Field field, Object value) throws Exception {
        boolean accessible = field.isAccessible();
        if(!accessible) {
            field.setAccessible(true);
        }
        field.set(obj, value);
        if(!accessible) {
            field.setAccessible(false);
        }
    }

    /**
     * 设值
     * @param obj
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public static void setValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        setValue(obj, field, value);
    }

    /**
     * 取泛型T
     * @param obj
     * @return
     */
    public static Type getT(Object obj) {
        Class clazz = obj.getClass();
        Object c = clazz;
        while(c instanceof Class) {
            c = ((Class)c).getGenericSuperclass();
        }
        if(c instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) c).getActualTypeArguments();
            Type t = types.length > 0 && types[0] instanceof Class<?> ? (Class<?>) types[0] : null;
            if(t != null) {
                return t;
            }
        }
        return null;
    }

    /**
     * 取带Unique的字段
     * @param clazz
     * @return
     */
    public static List<Field> uniqueFields(Class clazz) {
        List<Field> uniques = new ArrayList<>();
        while (clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                Unique u = f.getAnnotation(Unique.class);
                if(u != null) {
                    uniques.add(f);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return uniques;
    }

}
