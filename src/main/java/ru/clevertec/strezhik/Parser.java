package ru.clevertec.strezhik;

import lombok.SneakyThrows;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

import static ru.clevertec.strezhik.utils.ParserUtils.*;

public class Parser {

    @SneakyThrows
    public static String parse123(Object object) {
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
        } else if (aClass.getSuperclass().getSuperclass() == AbstractCollection.class) {
            Object toArray = aClass.getSuperclass().getSuperclass().getMethod("toArray").invoke(object);
            value = getNotPrimitiveArray((Object[]) toArray);
        } else if (aClass.getSuperclass() == AbstractMap.class) {
            value = parseObjectMapToString((Map<?, ?>) object);
        } else {
            Field[] declaredFields = aClass.getDeclaredFields();
            Map<String, String> map = new LinkedHashMap<>();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                map.put("\"" + declaredField.getName() + "\"",
                        Parser.parse123(declaredField.get(object)));
            }
            value = parseFinalMapToString(map);
        }
        return value;
    }
}
