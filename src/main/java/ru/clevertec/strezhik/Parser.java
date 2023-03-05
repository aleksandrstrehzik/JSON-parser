package ru.clevertec.strezhik;

import lombok.SneakyThrows;

import java.lang.reflect.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.clevertec.strezhik.utils.ArrayToObject.setArrayToObject;
import static ru.clevertec.strezhik.utils.CollectionToObject.IsCollection;
import static ru.clevertec.strezhik.utils.CollectionToObject.setCollectionToObject;
import static ru.clevertec.strezhik.utils.Constants.OBJECT_REGEX;
import static ru.clevertec.strezhik.utils.NumbersToObject.setPrimitiveOrTheirWrapperToObject;
import static ru.clevertec.strezhik.utils.ParserToStringUtils.*;
import static ru.clevertec.strezhik.utils.StringToObject.setStringToObject;

public class Parser {

    @SneakyThrows
    public static String toJSON(Object object) {
        if (object == null) {
            return null;
        }
        String value;
        Class<?> aClass = object.getClass();
        if (aClass == String.class) {
            value = "\"" + object + "\"";
        } else if (isPrimitiveOrTheirWrappers(aClass)) {
            value = String.valueOf(object);
        } else if (aClass.isArray()) {
            try {
                value = getNotPrimitiveArray((Object[]) object);
            } catch (ClassCastException e) {
                value = getPrimitiveArray(object);
            }
        } else if (IsCollection(aClass)) {
            Object toArray = aClass.getDeclaredMethod("toArray").invoke(object);
            value = getNotPrimitiveArray((Object[]) toArray);
        } else if (aClass.getSuperclass() == AbstractMap.class) {
            value = parseObjectMapToString((Map<?, ?>) object);
        } else {
            Field[] declaredFields = aClass.getDeclaredFields();
            Map<String, String> map = new LinkedHashMap<>();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                map.put("\"" + declaredField.getName() + "\"",
                        Parser.toJSON(declaredField.get(object)));
            }
            value = parseFinalMapToString(map);
        }
        return value;
    }

    @SneakyThrows
    public static  <T> T toObject(String json, Class<T> objectType) {
        T object = objectType.getConstructor().newInstance();

        Field[] declaredFields = objectType.getDeclaredFields();
        json = json.substring(1, json.length() - 1);
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            Class<?> type = declaredField.getType();
            if (type == String.class) {
                setStringToObject(json, objectType, object, name);
            } else if (isPrimitiveOrTheirWrappers(type)) {
                setPrimitiveOrTheirWrapperToObject(json, objectType, object, name, type);
            } else if (type.isArray()) {
                setArrayToObject(json, objectType, object, name, type);
            } else if (IsCollection(type)) {
                setCollectionToObject(json, objectType, object, name, type);
            }
             else {
                nestedObjectRecursion(json, objectType, object, declaredField, name, type);
            }
        }
        return object;
    }

    private static <T> void nestedObjectRecursion(String json, Class<T> objectType, T object, Field declaredField, String name, Class<?> type) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String valueString = getValueString(json, name, OBJECT_REGEX);
        String substring = valueString.substring(name.length() + 3);
        Object o = toObject(substring, declaredField.getType());
        objectType.getDeclaredMethod(setMethodForField(name), type)
                .invoke(object, o);
    }

    public static String getValueString(String json, String name, String stringPattern) {
        String format = String.format(stringPattern, name);
        Matcher matcher = Pattern.compile(format).matcher(json);
        return matcher.results()
                .map(MatchResult::group)
                .findFirst()
                .orElse(null);
    }

    public static String setMethodForField(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
