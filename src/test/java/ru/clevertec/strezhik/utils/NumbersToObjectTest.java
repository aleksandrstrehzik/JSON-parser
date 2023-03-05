package ru.clevertec.strezhik.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.strezhik.data.User;
import ru.clevertec.strezhik.data.UserBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.*;
import static org.assertj.core.api.Assertions.assertThat;

class NumbersToObjectTest {

    @ParameterizedTest
    @MethodSource("forStringToNumber")
    void stringToNumber(String value, Class<?> type, Object expected) {
        Object o = NumbersToObject.stringToNumber(value, type);

        assertThat(o).isEqualTo(type.cast(expected));
    }

    @Test
    void setPrimitiveOrTheirWrapperToObject() throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        User testObject = UserBuilder.aUser().withInteger(13).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testObject);
        testObject.setInteger(90082);
        NumbersToObject.setPrimitiveOrTheirWrapperToObject(json, User.class, testObject, "integer", Integer.class);

        assertThat(testObject.getInteger()).isEqualTo(13);
    }

    static Stream<Arguments> forStringToNumber() {
        return Stream.of(
                arguments("10987890987890", Long.class, Long.parseLong("10987890987890")),
                arguments("1", Integer.class, 1, Integer.parseInt("1")),
                arguments("89.098", Double.class, Double.parseDouble("89.098")));
    }
}