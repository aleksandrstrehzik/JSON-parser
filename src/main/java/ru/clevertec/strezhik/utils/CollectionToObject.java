package ru.clevertec.strezhik.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.clevertec.strezhik.Parser.getValueString;
import static ru.clevertec.strezhik.Parser.setMethodForField;
import static ru.clevertec.strezhik.utils.Constants.ARRAY_REGEX;

public class CollectionToObject {

    public static <T> void setCollectionToObject(String json, Class<T> objectType, T object, String name, Class<?> type) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String valueString = getValueString(json, name, ARRAY_REGEX);
        String substring = valueString.substring(name.length() + 4, valueString.length() - 1);
        List<String> collect = Arrays.stream(substring.split(","))
                .collect(Collectors.toList());
        objectType.getDeclaredMethod(setMethodForField(name), type).
                invoke(object, collect);
    }

    public static boolean IsCollection(Class<?> type) {
        do {
            Class<?> aClass = Arrays.stream(type.getInterfaces())
                    .filter(inter -> inter == Collection.class)
                    .findFirst()
                    .orElse(null);
            if (aClass != null) return true;
            type = type.getSuperclass();
        } while (type != null);
        return false;
    }
}
