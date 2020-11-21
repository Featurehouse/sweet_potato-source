package io.github.teddyxlandlee.sweet_potato.util;

import io.github.teddyxlandlee.sweet_potato.blocks.MagicCubeBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BooleanStateManager {
    public World world;
    public BlockPos pos;

    public BooleanStateManager(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public boolean shouldChange(boolean newOne) {
        assert this.world != null;
        return world.getBlockState(pos).get(MagicCubeBlock.ACTIVATED) != newOne;
    }

    public abstract void run();
}
