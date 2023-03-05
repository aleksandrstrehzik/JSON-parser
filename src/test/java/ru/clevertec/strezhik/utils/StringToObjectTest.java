package ru.clevertec.strezhik.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.clevertec.strezhik.data.User;
import ru.clevertec.strezhik.data.UserBuilder;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class StringToObjectTest {

    @Test
    void CheckSetStringToObject() throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        User doctor = UserBuilder.aUser().withJob("Doctor").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(doctor);
        doctor.setJob(null);
        StringToObject.setStringToObject(json, User.class, doctor, "job");

        assertThat(doctor.getJob()).isEqualTo("Doctor");
    }
}