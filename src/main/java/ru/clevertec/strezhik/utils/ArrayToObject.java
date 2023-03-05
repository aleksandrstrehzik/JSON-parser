package ru.clevertec.strezhik.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static ru.clevertec.strezhik.Parser.*;
import static ru.clevertec.strezhik.utils.Constants.*;

public class ArrayToObject {

    public static <T> void setArrayToObject(String json, Class<T> objectType, T object, String name, Class<?> type) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (type == String[].class) {
            String valueString = getValueString(json, name, ARRAY_REGEX);
            String substring = valueString.substring(name.length() + 4, valueString.length() - 1);
            String[] split = substring.split(",");
            objectType.getDeclaredMethod(setMethodForField(name), type).
                    invoke(object, (Object) split);
        } else {
            String valueString = getValueString(json, name, NUMERIC_ARRAY_REGEX);
            if (valueString != null) {
                String substring = valueString.substring(name.length() + 4, valueString.length() - 1);
                String[] split = substring.split(",");
                int[] ints = Arrays.stream(split)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                objectType.getDeclaredMethod(setMethodForField(name), type).
                        invoke(object, ints);
            } else {
                String value = getValueString(json, name, OBJECT_ARRAY_REGEX);
                String substring = value.substring(name.length() + 4, value.length() - 1);
                String[] split = substring.split("},");
                Object[] objects = Arrays.stream(split)
                        .map(s -> toObject(s, type))
                        .toArray();
                objectType.getDeclaredMethod(setMethodForField(name), type).
                        invoke(object, objects);
            }
        }
    }
}
