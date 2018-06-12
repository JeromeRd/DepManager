package com.dm.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jrichard on 28/06/2017.
 */
public class EnvironmentVariableManager {
    static Map<String, String> environmentVariables = new HashMap<>();

    static public void load(Map<String, String> envs) {
        environmentVariables.putAll(envs);
    }

    static public String get(String envVarName) {
        return environmentVariables.get(envVarName);
    }
}
