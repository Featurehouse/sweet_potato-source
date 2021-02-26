package io.featurehouse.spm.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public final class MathUtils {
    public static float distance(BlockPos pos, double anotherX, double anotherY, double anotherZ) {
        return distance(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F,
                (float) anotherX, (float) anotherY, (float) anotherZ);
    }

    public static float distance(float oneX, float oneY, float oneZ, float anotherX, float anotherY, float anotherZ) {
        float f = MathHelper.abs(oneX - anotherX);
        float g = MathHelper.abs(oneY - anotherY);
        float h = MathHelper.abs(oneZ - anotherZ);
        return MathHelper.sqrt(f * f + g * g + h * h);
    }
}
