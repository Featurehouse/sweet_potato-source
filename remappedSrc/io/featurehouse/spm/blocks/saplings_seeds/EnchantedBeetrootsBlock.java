package io.featurehouse.spm.blocks.saplings_seeds;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.util.tick.RandomTickHelper;
import net.minecraft.block.BeetrootsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class EnchantedBeetrootsBlock extends BeetrootsBlock {
    public EnchantedBeetrootsBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return SPMMain.ENCHANTED_BEETROOT_SEEDS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(6) != 0) {   // 5/6 random, instead of 2/3
                                              // is it necessary?
            // super.randomTick
            RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
        }
    }
}
