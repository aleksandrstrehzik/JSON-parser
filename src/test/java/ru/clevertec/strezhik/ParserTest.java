package ru.clevertec.strezhik;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.strezhik.data.User;
import ru.clevertec.strezhik.data.UserBuilder;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class   ParserTest {

    private ObjectMapper objectMapper;
    private static String json;

    @BeforeEach
    void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        User gop = UserBuilder.aUser().withJob("Gop").build();
        json = objectMapper.writeValueAsString(gop);
    }

    @Test
    void CheckParseShouldBeEquals() throws JsonProcessingException {
        User sergio = UserBuilder.aUser().withJob("Sergio").build();
        String jsonFromCustomParser = Parser.toJSON(sergio);
        String jsonFromJackson = objectMapper.writeValueAsString(sergio);

        assertThat(jsonFromCustomParser).isEqualTo(jsonFromJackson);
    }

    @Test
    void CheckToObjectShouldBeEquals() throws JsonProcessingException {
        User userFromCustomParser = Parser.toObject(json, User.class);
        User userFromJackson = objectMapper.readValue(json, User.class);

        assertThat(userFromCustomParser).isEqualTo(userFromJackson);
    }

    @ParameterizedTest
    @MethodSource("getValuesForGetValueString")
    void CheckGetValueString(String fieldName, String pattern, String expected) {
        String actual = Parser.getValueString(json, fieldName, pattern);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getValuesForSetMethodForField")
    void CheckSetMethodForField(String fieldName, String expected) {
        String actual = Parser.setMethodForField(fieldName);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getValuesForGetValueString() {
        return Stream.of(
                arguments("name", "\"%s\":\"([^\"]*(\"{2})?[^\"]*)*\"", "\"name\":\"Fedor\""),
                arguments("job", "\"%s\":\"([^\"]*(\"{2})?[^\"]*)*\"", "\"job\":\"Gop\""));
    }

    static Stream<Arguments> getValuesForSetMethodForField() {
        return Stream.of(
                arguments("arcebuza", "setArcebuza"),
                arguments("job", "setJob"));
    }
}
