package ru.clevertec.strezhik.utils;

import java.io.*;
import java.nio.file.Path;

public class ParserReader {

    public static String readJSON(Path path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String s = bufferedReader.readLine();
            return s;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
