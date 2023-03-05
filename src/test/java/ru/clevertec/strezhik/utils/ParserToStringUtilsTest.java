package ru.clevertec.strezhik.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.strezhik.data.User;
import ru.clevertec.strezhik.data.UserBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ParserToStringUtilsTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void parseObjectMapToString() throws JsonProcessingException {
        Map<String, User> map = new HashMap<>();
        User user1 = UserBuilder.aUser().build();
        User user2 = UserBuilder.aUser().build();
        map.put("1", user1);
        map.put("2", user2);

        String actual = ParserToStringUtils.parseObjectMapToString(map);
        String expected = objectMapper.writeValueAsString(map);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void CheckGetPrimitiveArray() throws JsonProcessingException {
        int[] array = {1, 3, 4, 5, 100};
        String actual = ParserToStringUtils.getPrimitiveArray(array);
        String expected = objectMapper.writeValueAsString(array);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void CheckGetNotPrimitiveArray() throws JsonProcessingException {
        User user1 = UserBuilder.aUser().build();
        User user2 = UserBuilder.aUser().build();

        Object[] array = {user1, user2};

        String actual = ParserToStringUtils.getNotPrimitiveArray(array);
        String expected = objectMapper.writeValueAsString(array);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    void CheckIsPrimitiveOrTheirWrappersShouldReturnTrue(Class<?> type) {
        boolean actual = ParserToStringUtils.isPrimitiveOrTheirWrappers(type);

        assertThat(actual).isTrue();
    }

    @Test
    void CheckIsPrimitiveOrTheirWrappersShouldReturnFalse() {
        boolean actual = ParserToStringUtils.isPrimitiveOrTheirWrappers(String.class);

        assertThat(actual).isFalse();
    }

    static Stream<Class<?>> getClasses() {
        return Stream.of(
                Byte.class, byte.class,
                Short.class, short.class,
                Integer.class, int.class,
                Long.class,long.class,
                Float.class, float.class,
                Boolean.class, boolean.class,
                Character.class, char.class,
                Double.class, double.class);
    }
}