package ru.clevertec.strezhik.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.clevertec.strezhik.data.User;
import ru.clevertec.strezhik.data.UserBuilder;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ParserWriterTest {

    @Test
    void CheckWriteString() throws JsonProcessingException {
        User user = UserBuilder.aUser().build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        Path path = Path.of("src", "test", "resources", "object.json");
        ParserWriter.writeString(json, path);
        String readJSON = ParserReader.readJSON(path);

        assertThat(readJSON).isNotBlank();

        new File(String.valueOf(path)).delete();
    }
}