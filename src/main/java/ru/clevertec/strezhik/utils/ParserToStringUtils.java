package ru.clevertec.strezhik.utils;

import ru.clevertec.strezhik.Parser;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.clevertec.strezhik.Parser.toJSON;

public class ParserToStringUtils {

    public static String parseObjectMapToString(Map<?, ?> map) {
        return "{" + map.entrySet().stream()
                .map(entry -> toJSON(entry.getKey()) + ":" + toJSON(entry.getValue()))
                .collect(Collectors.joining(",")) + "}";
    }

    public static String getPrimitiveArray(Object object) {
        String valueText = Arrays.deepToString(new Object[]{object});
        String substring = valueText.substring(1, valueText.length() - 1);
        return substring.replace(" ", "");
    }

    public static String parseFinalMapToString(Map<String, String> map) {
        String value;
        value = "{" + map.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(",")) + "}";
        return value;
    }

    public static String getNotPrimitiveArray(Object[] object) {
        return "[" + Arrays.stream(object)
                .map(Parser::toJSON)
                .collect(Collectors.joining(",")) + "]";
    }

    public static boolean isPrimitiveOrTheirWrappers(Class<?> type) {
        return type == Byte.class ||
                type == byte.class ||
                type == Short.class ||
                type == short.class ||
                type == Integer.class ||
                type == int.class ||
                type == Long.class ||
                type == long.class ||
                type == Float.class ||
                type == float.class ||
                type == Boolean.class ||
                type == boolean.class ||
                type == Character.class ||
                type == char.class ||
                type == Double.class ||
                type == double.class;
    }
}
