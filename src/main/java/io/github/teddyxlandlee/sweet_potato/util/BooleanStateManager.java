package io.github.teddyxlandlee.sweet_potato.util;

import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public abstract class BooleanStateManager {
    //public World world;
    //public BlockPos pos;
    protected Property<Boolean> property;

    public BooleanStateManager(Property<Boolean> property) {
        this.property = property;
    }

    public abstract void run();

    protected static BlockPos[] calcPos(@Nonnull BlockPos original) {
        BlockPos downPos = original.down();
        return new BlockPos[] {
                downPos,
                downPos.east(), downPos.south(), downPos.west(), downPos.north(),
                downPos.east().north(), downPos.east().south(),
                downPos.west().north(), downPos.west().south()
        };
    }
}
