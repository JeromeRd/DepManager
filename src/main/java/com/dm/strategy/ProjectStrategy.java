package com.dm.strategy;

/**
 * Created by jrichard on 04/07/2017.
 */
public enum ProjectStrategy {
    SIMPLE_STRATEGY("Simple project");

    private String label;

    ProjectStrategy(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ProjectStrategy findByLabel(String label) {
        for (ProjectStrategy projectStrategy : ProjectStrategy.values()) {
            if (projectStrategy.getLabel().equals(label)) {
                return projectStrategy;
            }
        }
        return null;
    }
}
