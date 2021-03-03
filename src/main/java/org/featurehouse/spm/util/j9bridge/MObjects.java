package org.featurehouse.spm.util.j9bridge;

import static java.util.Objects.*;

public class MObjects {
    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }
}
