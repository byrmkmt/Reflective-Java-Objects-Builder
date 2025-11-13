package org.reflections;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.*;
import java.util.*;

public class Driver {

    public static void main(String[] args) {
        try {
            ChildObject o = new ChildObject();
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("*********************");
            System.out.println("Before Initialisation");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
            System.out.println("*********************");
            o = (ChildObject) recursionCreation(o,ChildObject.class);
            System.out.println("After Initialisation");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // CREATE CLASS OBJECT AND SUPER CLASS OBJECTS
    private static Object recursionCreation(Object o, Class<?> c) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (c.getSuperclass() != null) {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                Class<?> type = field.getType();
                if (!type.isPrimitive() && !type.isEnum()) {
                    if(isNullObject(field.getName(), o)){
                        if (type.equals(List.class)) {
                            createObject(field.getName(), o, new ArrayList<>(), List.class);
                        } else if (type.equals(Map.class)) {
                            createObject(field.getName(), o, new HashMap<>(), Map.class);
                        }
                        else {
                            Class<?> actual = getActualClass(field.getName(), o);
                            Object instance = newInstance(actual);
                            if(instance != null) {
                                if(notJavaObject(instance) && !actual.isEnum()){
                                    instance =  recursionCreation(instance,actual);
                                }
                                createObject(field.getName(), o, instance, actual);
                            }
                        }
                    }
                }
            }
            if(notJavaObject(o)){
                o = recursionCreation(o, c.getSuperclass());
            }
        }
        return o;
    }


    private static boolean isNullObject(String field, Object o) throws NoSuchMethodException {
        Object  result;
        Method getter;
        try {
            getter = o.getClass().getMethod(fieldToGetter(field));
        }  catch (NoSuchMethodException e) {
            getter = o.getClass().getMethod(fieldToIsGetter(field));
        }

        Class<?> actual = getActualClass(field, o);
        if (List.class.isAssignableFrom(actual) || Map.class.isAssignableFrom(actual)) {
            try {
                result = getter.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                result = getter.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return result == null;
    }

    private static Object newInstance(final Class<?> actualType) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if(actualType == null) {
            return null;
        }
        if (List.class.isAssignableFrom(actualType)) {
            return new ArrayList<>();
        } else if (Map.class.isAssignableFrom(actualType)) {
            return new HashMap<>();
        } else if (Integer.class.isAssignableFrom(actualType)){
            return 0;
        } else if (Long.class.isAssignableFrom(actualType)){
            return 0L;
        } else if(Double.class.isAssignableFrom(actualType)) {
            return 0.0d;
        } else if(Float.class.isAssignableFrom(actualType)) {
            return 0.0f;
        } else if(Byte.class.isAssignableFrom(actualType)) {
            return 0;
        } else if(Short.class.isAssignableFrom(actualType)) {
            return 0;
        } else if(Boolean.class.isAssignableFrom(actualType)) {
            return false;
        }
        else {
            return actualType.getDeclaredConstructor().newInstance();
        }
    }

    private static void createObject(String field, Object o, Object newValue, Class<?> parameter){
        try {
            Method setter = o.getClass().getMethod(fieldToSetter(field), parameter);
            setter.invoke(o,newValue);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getActualClass(String field, Object o) throws NoSuchMethodException {
        Method getter;
        try {
            getter = o.getClass().getMethod(fieldToGetter(field));
        }  catch (NoSuchMethodException e) {
            getter = o.getClass().getMethod(fieldToIsGetter(field));
        }
        Type propertyType = getter.getGenericReturnType();
        if (propertyType instanceof ParameterizedType) {
            ParameterizedType genericReturnType = (ParameterizedType) propertyType;
            return (Class<?>) genericReturnType.getActualTypeArguments()[0];
        } else if (propertyType instanceof Class) {
            return (Class<?>) propertyType;
        }
        return null;
    }

    private static String fieldToGetter(String name) {
        if(Character.isUpperCase(name.charAt(1))) {
            return "get" + name;
        }
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String fieldToIsGetter(String name) {
        return "is" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String fieldToSetter(String name) {
        if(Character.isUpperCase(name.charAt(1))) {
            return "set" + name;
        }
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static boolean notJavaObject(Object check) {
        return !check.getClass().getName().startsWith("java.lang") && !check.getClass().getName().startsWith("java.time");
    }

}
