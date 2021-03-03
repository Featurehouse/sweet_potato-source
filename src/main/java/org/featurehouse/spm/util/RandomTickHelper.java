package org.featurehouse.spm.util;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.Random;

public final class RandomTickHelper {
    private static <T extends CropBlock> int getCropAge(T instance, BlockState state) {
        return state.get(instance.getAgeProperty());
    }

    /**
     * <h1>From: Vanilla-1.16.1:net.minecraft.block.CropBlock</h1>
     * <p>Because of its protected accessibility, teddyxlandlee
     * copies it into this helper class.</p>
     */
    private static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND)) {
                    g = 1.0F;
                    if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }

        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = block == world.getBlockState(blockPos4).getBlock() || block == world.getBlockState(blockPos5).getBlock();
        boolean bl2 = block == world.getBlockState(blockPos2).getBlock() || block == world.getBlockState(blockPos3).getBlock();
        if (bl && bl2) {
            f /= 2.0F;
        } else {
            boolean bl3 = block == world.getBlockState(blockPos4.north()).getBlock() || block == world.getBlockState(blockPos5.north()).getBlock() || block == world.getBlockState(blockPos5.south()).getBlock() || block == world.getBlockState(blockPos4.south()).getBlock();
            if (bl3) {
                f /= 2.0F;
            }
        }

        return f;
    }

    private RandomTickHelper() {}

    public static <T extends CropBlock> void enchantedCropRandomTick(T instance,
                                              BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = getCropAge(instance, state);
            if (i < instance.getMaxAge()) {
                float f = getAvailableMoisture(instance, world, pos);
                if (random.nextInt((int) ((int)((25.0F / f) + 1) / 2.5F)) == 0) {
                    world.setBlockState(pos, instance.withAge(i + 1), 2);
                }
            }
        }
    }
}
