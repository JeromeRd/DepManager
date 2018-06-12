package com.dm.converter;

import com.dm.datamodel.Profile;
import com.dm.datamodel.ProfileProxy;
import com.dm.strategy.StrategyDispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by jrichard on 13/07/2017.
 */
public class ProfileConverter {
    private static final String GET_PREFIX = "get";
    private static final String SET_PREFIX = "set";

    public static Profile convertToProfile(Profile profile, ProfileProxy profileProxy) {
        for (Map.Entry<String, Object> entry : profileProxy.getProperties()) {
            try {
                String propertyName = entry.getKey();
                Method method = getMethod(profile.getClass(), SET_PREFIX + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1, propertyName.length()));
                Class[] parametersType = method.getParameterTypes();
                if (Caster.needToBeCasted(parametersType[0])) {
                    method.invoke(profile, Caster.cast(parametersType[0], entry.getValue()));
                } else {
                    method.invoke(profile, parametersType[0].cast(entry.getValue()));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        StrategyDispatcher.dispatch(profile, profile.getStrategy());
        return profile;
    }

    public static Method getMethod(Class clazz, String methodName) {
        do {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return null;
    }

    public static ProfileProxy convertToProxy(ProfileProxy profileProxy, Profile profile) {
        for (Map.Entry<String, Object> entry : profileProxy.getProperties()) {
            try {
                Method method = profile.getClass().getMethod(SET_PREFIX + entry.getKey());
                Class[] parametersType = method.getParameterTypes();
                if (Caster.needToBeCasted(parametersType[0])) {
                    method.invoke(profile, Caster.cast(parametersType[0], entry.getValue()));
                } else {
                    method.invoke(profile, parametersType[0].cast(entry.getValue()));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return profileProxy;
    }
}
