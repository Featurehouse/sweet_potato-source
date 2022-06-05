package org.featurehouse.mcmod.spm.util.tick;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface ITickable {
    void tick(Level world, BlockPos pos, BlockState state);

    static <T extends BlockEntity & ITickable> void iTick(Level world, BlockPos pos, BlockState state, T blockEntity) {
        blockEntity.tick(world, pos, state);
    }
}
