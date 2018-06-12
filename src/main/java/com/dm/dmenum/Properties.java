package com.dm.dmenum;

/**
 * Created by jrichard on 30/06/2017.
 */
public enum Properties {
    DM_WORKSPACE("dmpworkspace"), SCRIPT_WORKSPACE("scriptsworkspace"), SCRIPT_PREFIX("scriptprefix"),
    SCRIPT_EXTENSION("scriptextension");

    private String key;

    Properties(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
