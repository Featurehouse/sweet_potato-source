package io.featurehouse.spm.util.tick;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ITickable {
    void tick(World world, BlockPos pos, BlockState state);

    @Deprecated
    default <T extends BlockEntity> BlockEntityTicker<T> tickable2ticker() {
        if (this instanceof BlockEntity)
            return (world, pos, state, blockEntity1) -> this.tick(world, pos, state);
        return null;
    }

    static <T extends BlockEntity & ITickable> void iTick(World world, BlockPos pos, BlockState state, T blockEntity) {
        blockEntity.tick(world, pos, state);
    }
}
