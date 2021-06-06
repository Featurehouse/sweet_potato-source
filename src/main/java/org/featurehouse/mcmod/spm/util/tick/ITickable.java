package org.featurehouse.mcmod.spm.util.tick;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface ITickable {
    void tick(World world, BlockPos pos, BlockState state);

    static <T extends BlockEntity & ITickable> void iTick(World world, BlockPos pos, BlockState state, T blockEntity) {
        blockEntity.tick(world, pos, state);
    }
}
