package ru.clevertec.strezhik.utils;

import ru.clevertec.strezhik.Parser;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.clevertec.strezhik.Parser.parse123;

public class ParserUtils {

    public static String parseObjectMapToString(Map<?, ?> map) {
        return "{" + map.entrySet().stream()
                .map(entry -> parse123(entry.getKey()) + ":" + parse123(entry.getValue()))
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
                .map(Parser::parse123)
                .collect(Collectors.joining(",")) + "]";
    }

    public static boolean isPrimitiveOrTheirWrappers(Class<?> type) {
        return type == Byte.class ||
                type == Short.class ||
                type == Integer.class ||
                type == Long.class ||
                type == Float.class ||
                type == Boolean.class ||
                type == Character.class ||
                type == Double.class;
    }
}
