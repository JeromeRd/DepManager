package com.dm.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jrichard on 28/06/2017.
 */
public class PropertiesManager {
    private static Map<String, String> properties = new HashMap<>();

    public static void load(Set<Map.Entry<Object, Object>> entrySet) {
        for (Map.Entry<Object, Object> entry : entrySet) {
            System.out.println("Property added " + entry.getKey() + "->" + entry.getValue());
            properties.put((String)entry.getKey(), (String)entry.getValue());
        }
    }

    public static String getProperty(String key) {
        return properties.get(key);
    }
}
