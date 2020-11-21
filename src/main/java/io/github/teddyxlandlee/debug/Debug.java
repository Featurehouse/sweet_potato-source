package io.github.teddyxlandlee.debug;

public final class Debug {
    //private static final Logger logger = LogManager.getLogger();
    private static final String prefix = "--- Debug Message from Sweet Potato Mod ---\n";
    private static final String suffix = "\n--------------------END--------------------\n";

    private Debug() {}

    @Deprecated
    public static void debug(Class<?> cls, String text) {
        String full = prefix + "From Class %s\nDetail: %s" + suffix;
        //logger.debug(full, cls.getPackageName() + ":" + cls.getSimpleName(), text);
        System.out.printf(full, cls.getTypeName(), text);
    }
}
