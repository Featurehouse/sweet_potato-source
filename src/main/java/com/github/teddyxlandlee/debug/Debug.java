package com.github.teddyxlandlee.debug;

import net.minecraft.text.StringRenderable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Debug {
    private static final Logger logger = LogManager.getLogger();
    private static final String prefix = "--- Debug Message from Sweet Potato Mod ---\n";
    private static final String suffix = "\n--------------------END--------------------\n";

    public static void debug(Class<?> cls, String text) {
        String full = prefix + "From Class %s\nDetail: %s" + suffix;
        //logger.debug(full, cls.getPackageName() + ":" + cls.getSimpleName(), text);
        System.out.printf(full, cls.getPackageName() + ":" + cls.getSimpleName(), text);
    }

    public static void debug(Object object, String text) {
        debug(object.getClass(), text);
    }

    public static void debug(Class<?> cls, StringRenderable text) {
        debug(cls, text.getString());
    }

    public static void debug(Object object, StringRenderable text) {
        debug(object.getClass(), text.getString());
    }

    public static void debug(Class<?> cls, PartType partType, String caller, String detail) {
        String partName = partType.asString();
        String full = prefix + "From: %s\nFrom %s :%s\nDetail: %s" + suffix;
        System.out.printf(full, cls.getPackageName() + ":" + cls.getSimpleName(), partName, caller, detail);
    }
}
