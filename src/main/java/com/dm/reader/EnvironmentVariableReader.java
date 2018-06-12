package com.dm.reader;


import com.dm.common.EnvironmentVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jrichard on 28/06/2017.
 */
public class EnvironmentVariableReader {

    static List<String> environmentVariableKeys = new ArrayList<String>() {{
        add(EnvironmentVariables.GIT_DIRECTORY);
    }};

    public static Map<String, String> read() {
        Map<String, String> result = new HashMap<String, String>();
        Map<String, String> env = System.getenv();
        for(String envKey : environmentVariableKeys) {
            System.out.println("Retrieve environment variables :");
            if (env.containsKey(envKey)) {
                String value = env.get(envKey);
                System.out.format("%s=%s%n", envKey, value);
                result.put(envKey, value);
            }
        }
        return result;
    }
}
