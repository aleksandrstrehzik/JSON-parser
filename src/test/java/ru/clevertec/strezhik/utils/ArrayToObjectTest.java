package ru.clevertec.strezhik.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.clevertec.strezhik.data.User;
import ru.clevertec.strezhik.data.UserBuilder;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayToObjectTest {

    @Test
    void setArrayToObject() throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String[] array = {"first", "second", "third"};
        User user = UserBuilder.aUser().withArray(array).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        user.setArray(null);
        ArrayToObject.setArrayToObject(json, User.class, user, "array", String[].class);

        assertThat(user.getArray()).isEqualTo(array);
    }
}