package io.github.teddyxlandlee.debug;

import java.util.Objects;

public final class NullPointerHelper {
    private NullPointerHelper() {}

    public static <T> T requiresNonnull(T obj, Class<?> orElseDebugClass, PartType partType, String caller, String detail) {
        return Objects.requireNonNullElseGet(obj, () -> {
            Debug.debug(orElseDebugClass, partType, caller, detail);
            return null;
        });
    }
}
