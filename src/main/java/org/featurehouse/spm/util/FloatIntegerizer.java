package org.featurehouse.spm.util;

public final class FloatIntegerizer {
    private FloatIntegerizer() {}

    public static int fromFloat(float f) {
        return Float.floatToIntBits(f);
    }

    public static float toFloat(int i) {
        return Float.intBitsToFloat(i);
    }
}
