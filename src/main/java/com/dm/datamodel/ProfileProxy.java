package com.dm.datamodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jrichard on 13/07/2017.
 */
public class ProfileProxy {
    private UUID profileId;
    private Map<String, Object> properties;

    public ProfileProxy(UUID profileId) {
        this.profileId = profileId;
        properties = new HashMap<>();
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Object getValue(String key) {
        return properties.get(key);
    }

    public Set<Map.Entry<String, Object>> getProperties() {
        return properties.entrySet();
    }
}
