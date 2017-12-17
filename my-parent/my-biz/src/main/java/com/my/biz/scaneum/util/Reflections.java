
package com.my.biz.scaneum.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.my.common.utils.Dates;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.persistence.Column;
import java.lang.reflect.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Timestamp;
import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Reflections {
    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    private static Logger logger = LoggerFactory.getLogger(Reflections.class);

    public static Object invokeGetter(Object obj, String propertyName) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[]{}, new Object[]{});
    }

    public static List<Object> invokeGetter(Object obj, String[] propertyNames) {
        List<Object> list = new ArrayList<Object>(propertyNames.length);
        for (String propertyName : propertyNames) {
            Object propertyValue = null;
            if (StringUtils.contains(propertyName, ".")) {
                String[] propertyNamePaths = StringUtils.split(propertyName, ".");
                Object temp = obj;
                for (String propertyNamePath : propertyNamePaths) {
                    if (temp == null) {
                        break;
                    }
                    temp = Reflections.invokeGetter(temp, propertyNamePath);
                }
                propertyValue = temp;
            } else {
                propertyValue = Reflections.invokeGetter(obj, propertyName);
            }
            list.add(propertyValue);
        }
        return list;
    }

    public static List<String> invokeGetterToString(Object obj, String[] propertyNames) {
        List<Object> list = invokeGetter(obj, propertyNames);
        List<String> result = new ArrayList<String>(list.size());
        for (Object object : list) {
            if (object == null) {
                result.add(null);
            } else if (object instanceof Date) {
                result.add(Dates.format((Date) object));
            } else if (object instanceof Calendar) {
                result.add(Dates.format(((Calendar) object).getTime()));
            } else {
                result.add(object.toString());
            }
        }
        return result;
    }

    public static void invokeSetter(Object obj, String propertyName, Object value) {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        invokeMethodByName(obj, setterMethodName, new Object[]{value});
    }

    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    public static Object getFieldValue(final Object obj, final Field field) {
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
    }

    public static void setFieldValue(final Object obj, final Field field, final Object value) {
        makeAccessible(field);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
    }

    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    public static Class getClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn(
                    "Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    public static Class<?> getUserClass(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

    public static Set<String> getFieldNames(Object instance) {
        Class<?> pojoClass = instance.getClass();
        return getFieldNames(pojoClass);
    }

    public static Set<String> getFieldNames(Class<?> pojoClass) {
        Set<String> propertyNames = new HashSet<String>();
        Class<?> clazz = pojoClass;
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    propertyNames.add(field.getName());

                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null && !clazz.getSimpleName().equalsIgnoreCase("Object"));
        return propertyNames;
    }

    public static Set<Field> getFields(Class<?> pojoClass) {
        Set<Field> allFields = new HashSet<Field>();
        Class<?> clazz = pojoClass;
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    allFields.add(field);

                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null && !clazz.getSimpleName().equalsIgnoreCase("Object"));
        return allFields;
    }

    public static Set<String> getSimpleFieldNames(Class<?> pojoClass) {
        Set<String> propertyNames = new HashSet<String>();
        Class<?> clazz = pojoClass;
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())
                        && (field.getType().isPrimitive() || isWrapClass(field.getType())
                        || field.getType().isAssignableFrom(Timestamp.class)
                        || field.getType().isAssignableFrom(Date.class)
                        || field.getType().isAssignableFrom(String.class)
                        || field.getType().isAssignableFrom(Calendar.class))) {
                    propertyNames.add(field.getName());
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null && !clazz.getSimpleName().equalsIgnoreCase("Object"));
        return propertyNames;
    }

    /**
     * 获取指定类的数据库字段名
     * @param pojoClass
     * @return
     */
    public static List<Map<String, Object>> getColumns(Class<?> pojoClass) {
        List<Map<String, Object>> propertyNames = Lists.newArrayList();
        Set<Field> fieldsSet = getFields(pojoClass);
        Map<String, Object> temp = null;
        for (Field field : fieldsSet) {
            Column column = field.getDeclaredAnnotation(Column.class);
            if (column != null) {
                String comment = column.columnDefinition().toLowerCase().trim();
                int index = comment.indexOf("comment") + 9;
                temp = Maps.newHashMap();
                temp.put("name", field.getName());
                temp.put("displayName", comment.substring(index, comment.length() - 1));
                temp.put("type", field.getType());
                propertyNames.add(temp);
            }
        }
        return propertyNames;
    }

    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    public static Class<?>[] processParameterToParameterType(Object... parameters) {
        if (parameters == null) {
            return null;
        }
        Class<?>[] parameter = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameter[i] = parameters[i].getClass();
        }
        return parameter;
    }

    public static <T> T createObject(Class<T> clazz, Object... parameters) throws NoSuchMethodException {
        if (clazz == null) {
            throw new RuntimeException("不能'null' 创建实例。");
        }
        Class<?>[] parameterTypes = processParameterToParameterType(parameters);
        return createObject(clazz, parameterTypes, parameters);
    }

    public static <T> T createObject(Class<T> clazz, Class<?>[] parameterTypes,
                                     Object[] parameters) throws NoSuchMethodException {
        if (clazz == null) {
            throw new RuntimeException("不能 'null' 创建实例。");
        }
        Constructor<T> constructor;
        if (ArrayUtils.isEmpty(parameterTypes)) {
            constructor = clazz.getDeclaredConstructor();
            parameters = null;
        } else {
            constructor = clazz.getDeclaredConstructor(parameterTypes);
        }
        return createObject(constructor, parameters);
    }

    public static <T> T createObject(Constructor<T> constructor, Object... parameters) {
        if (!Modifier.isPublic(constructor.getModifiers())) {
            constructorSetAccessible(constructor);
        }
        try {
            return constructor.newInstance(parameters);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("向该构造方法传递了一个不正确的参数。");
        } catch (InstantiationException e) {
            throw new RuntimeException("无法为接口或者抽象类创建实例。");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("预创建的对象无法构造。");
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
    }

    public static void constructorSetAccessible(Constructor<?> constructor) {
        setAccessible(constructor);
    }

    private static void setAccessible(final AccessibleObject accessibleObject) {
        if (!accessibleObject.isAccessible()) {
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    public Object run() {
                        accessibleObject.setAccessible(true);
                        return null;
                    }
                });
            } else {
                accessibleObject.setAccessible(true);
            }
        }
    }

}
