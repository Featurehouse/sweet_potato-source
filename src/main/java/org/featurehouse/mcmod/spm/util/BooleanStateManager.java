package org.featurehouse.mcmod.spm.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

public abstract class BooleanStateManager implements Runnable {
    protected Property<Boolean> property;

    public BooleanStateManager(Property<Boolean> property) {
        this.property = property;
    }

    public abstract void run();

    protected static BlockPos[] calcPos(@NotNull BlockPos original) {
        BlockPos downPos = original.below();
        return new BlockPos[] {
                downPos,
                downPos.east(), downPos.south(), downPos.west(), downPos.north(),
                downPos.east().north(), downPos.east().south(),
                downPos.west().north(), downPos.west().south()
        };
    }
}
