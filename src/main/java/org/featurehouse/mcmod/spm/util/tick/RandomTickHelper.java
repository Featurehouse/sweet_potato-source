package org.featurehouse.mcmod.spm.util.tick;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class RandomTickHelper {
    private static <T extends CropBlock> int getCropAge(T instance, BlockState state) {
        return state.getValue(instance.getAgeProperty());
    }

    /**
     * <h3>From: Vanilla-1.16.1:
     *             {@link net.minecraft.world.level.block.CropBlock}</h3>
     * <p>Because of its protected accessibility, teddyxlandlee
     * copies it into this helper class.</p>
     */
    private static float getAvailableMoisture(Block block, BlockGetter world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.below();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.offset(i, 0, j));
                if (blockState.is(Blocks.FARMLAND)) {
                    g = 1.0F;
                    if (blockState.getValue(FarmBlock.MOISTURE) > 0) {
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
                                              BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getRawBrightness(pos, 0) >= 9) {
            int i = getCropAge(instance, state);
            if (i < instance.getMaxAge()) {
                float f = getAvailableMoisture(instance, world, pos);
                if (random.nextInt((int) ((int)((25.0F / f) + 1) / 2.5F)) == 0) {
                    world.setBlock(pos, instance.getStateForAge(i + 1), 2);
                }
            }
        }
    }
}
