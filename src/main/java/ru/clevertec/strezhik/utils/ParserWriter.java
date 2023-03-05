package ru.clevertec.strezhik.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ParserWriter {

    public static void writeString(String object, Path path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()))) {
            bufferedWriter.write(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
