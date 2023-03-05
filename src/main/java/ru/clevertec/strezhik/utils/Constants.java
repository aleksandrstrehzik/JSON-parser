package ru.clevertec.strezhik.utils;

public class Constants {


    public static final String NUMERIC_REGEX = "\"%s\":(([-0-9.eE]+)|(null))";
    public static final String STRING_REGEX = "\"%s\":\"([^\"]*(\"{2})?[^\"]*)*\"";
    public static final String ARRAY_REGEX = "\"%s\":\\[(\"*[A-Za-z0-9]*\"*,*)*\\]";
    public static final String OBJECT_REGEX = "\"%s\":\\{([\"A-Za-z0-9:,\\[\\]\\{\\}]*)\\}";
    public static final String BOOLEAN_REGEX = "\"%s\":(true)|(false)";
    public static final String OBJECT_ARRAY_REGEX = "\"%s\":\\[\\{.*\\}\\]";
    public static final String NUMERIC_ARRAY_REGEX = "\"%s\":\\[[0-9,]*\\]";
}
