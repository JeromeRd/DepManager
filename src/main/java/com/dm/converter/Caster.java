package com.dm.converter;

import com.dm.strategy.ProjectStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 24/07/2017.
 */
public class Caster {
    private static List<Class> toBeCasted = new ArrayList<>();

    static {
        toBeCasted.add(ProjectStrategy.class);
    }

    public static boolean needToBeCasted(Class clazz) {
        return toBeCasted.contains(clazz);
    }

    public static Object cast(Class clazz, Object o) {
        if (clazz.isAssignableFrom(ProjectStrategy.class) && o instanceof String) {
            return ProjectStrategy.findByLabel((String) o);
        } else if (clazz.isAssignableFrom(String.class) && o instanceof ProjectStrategy) {
            return ((ProjectStrategy) o).getLabel();
        }
        return null;
    }
}
