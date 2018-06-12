package com.dm.ui.swing.component;

import com.dm.datamodel.Project;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by jrichard on 12/07/2017.
 */
public class ProjectComponentRegistry {
    private static final Map<ProjectComponent, Project> map = new WeakHashMap();

    public static void register(ProjectComponent projectComponent, Project project) {
        map.put(projectComponent, project);
    }

    public static void unregister(ProjectComponent projectComponent) {
        map.remove(projectComponent);
    }

    public static void unregister(Project project) {
        ProjectComponent projectComponent = findByValue(project);
        if (projectComponent != null) {
            map.remove(projectComponent);
        }
    }

    public static Project findByKey(ProjectComponent projectComponent) {
        return map.get(projectComponent);
    }

    public static ProjectComponent findByValue(Project project) {
        for (Map.Entry<ProjectComponent, Project> entry : map.entrySet()) {
            if (entry.getValue().equals(project)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Set<ProjectComponent> getProjectComponents() {
        return map.keySet();
    }

    public static void clear() {
        map.clear();
    }
}
