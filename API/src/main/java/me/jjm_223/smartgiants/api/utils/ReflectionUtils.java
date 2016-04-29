package me.jjm_223.smartgiants.api.utils;

import java.lang.reflect.AccessibleObject;

public class ReflectionUtils {
    public static void setAccessible(AccessibleObject... objects) {
        for (AccessibleObject object : objects) {
            object.setAccessible(true);
        }
    }
}
