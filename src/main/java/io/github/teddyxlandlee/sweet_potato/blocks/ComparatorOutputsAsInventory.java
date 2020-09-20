package io.github.teddyxlandlee.sweet_potato.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ComparatorOutputsAsInventory extends bilibili.ywsuoyi.block.blockWithEntity {
    public ComparatorOutputsAsInventory(Settings settings) {
        super(settings);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
