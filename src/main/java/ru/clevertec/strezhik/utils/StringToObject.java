package ru.clevertec.strezhik.utils;

import java.lang.reflect.InvocationTargetException;

import static ru.clevertec.strezhik.Parser.getValueString;
import static ru.clevertec.strezhik.Parser.setMethodForField;
import static ru.clevertec.strezhik.utils.Constants.STRING_REGEX;

public class StringToObject {

    public static <T> void setStringToObject(String json, Class<T> objectType, T object, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String s = getValueString(json, name, STRING_REGEX);
        if (s != null) {
            String substring = s.substring(name.length() + 4, s.length() - 1);
            objectType.getDeclaredMethod(setMethodForField(name), String.class).
                    invoke(object, substring);
        } else {
            objectType.getDeclaredMethod(setMethodForField(name), String.class).
                    invoke(object, (Object) null);
        }
    }
}
