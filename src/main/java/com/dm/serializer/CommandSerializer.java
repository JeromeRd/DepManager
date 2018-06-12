package com.dm.serializer;

/**
 * Created by jrichard on 05/07/2017.
 */
public class CommandSerializer {
    private static final String DELIMITER = "##";

    public static String serialize(String[] strings) {
        if (strings == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(DELIMITER);
            }
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static String[] deserializer(String s) {
        return s.split(DELIMITER);
    }
}
