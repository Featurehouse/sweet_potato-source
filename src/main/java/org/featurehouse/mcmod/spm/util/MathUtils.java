package org.featurehouse.mcmod.spm.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public final class MathUtils {
    public static float distance(BlockPos pos, double anotherX, double anotherY, double anotherZ) {
        return distance(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F,
                (float) anotherX, (float) anotherY, (float) anotherZ);
    }

    public static float distance(float oneX, float oneY, float oneZ, float anotherX, float anotherY, float anotherZ) {
        float f = Mth.abs(oneX - anotherX);
        float g = Mth.abs(oneY - anotherY);
        float h = Mth.abs(oneZ - anotherZ);
        return Mth.sqrt(f * f + g * g + h * h);
    }
}
