package ru.clevertec.strezhik.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static ru.clevertec.strezhik.Parser.getValueString;
import static ru.clevertec.strezhik.Parser.setMethodForField;
import static ru.clevertec.strezhik.utils.Constants.BOOLEAN_REGEX;
import static ru.clevertec.strezhik.utils.Constants.NUMERIC_REGEX;

public class NumbersToObject {

    public static <T> T stringToNumber(String numberString, Class<T> type) {
        Number number = null;

        if (type == Byte.class || type == byte.class) number = Byte.parseByte(numberString);
        else if (type == Short.class || type == short.class) number = Short.parseShort(numberString);
        else if (type == Integer.class || type == int.class) number = Integer.parseInt(numberString);
        else if (type == Long.class || type == long.class) number = Long.parseLong(numberString);
        else if (type == Float.class || type == float.class) number = Float.parseFloat(numberString);
        else if (type == Double.class || type == double.class) number = Double.parseDouble(numberString);
        else if (type == BigDecimal.class) number = new BigDecimal(numberString);
        else if (type == BigInteger.class) number = new BigInteger(numberString);

        return (T) number;
    }

    public static <T> void setPrimitiveOrTheirWrapperToObject(String json, Class<T> objectType, T object, String name, Class<?> type) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (type == boolean.class || type == Boolean.class) {
            String valueString = getValueString(json, name, BOOLEAN_REGEX);
            if (valueString != null) {
                String substring = valueString.substring(name.length() + 3);
                objectType.getDeclaredMethod(setMethodForField(name), type).
                        invoke(object, Boolean.parseBoolean(substring));
            } else {
                objectType.getDeclaredMethod(setMethodForField(name), type).
                        invoke(object, (Object) null);
            }
        } else {
            String valueString = getValueString(json, name, NUMERIC_REGEX);
            if (valueString != null) {
                String number = valueString.substring(name.length() + 3);
                objectType.getDeclaredMethod(setMethodForField(name), type).
                        invoke(object, stringToNumber(number, type));
            } else {
                objectType.getDeclaredMethod(setMethodForField(name), type).
                        invoke(object, (Object) null);
            }
        }
    }

}
