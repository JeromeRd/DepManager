package com.dm.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by jrichard on 28/06/2017.
 */
public class PropertiesReader {
    public static Set<Map.Entry<Object, Object>> read() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("cfg/dm.cfg");
        prop.load(stream);
        return prop.entrySet();
    }
}
