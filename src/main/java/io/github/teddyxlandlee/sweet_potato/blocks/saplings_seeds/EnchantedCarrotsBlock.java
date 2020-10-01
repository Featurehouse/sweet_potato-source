package io.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.util.RandomTickHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarrotsBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class EnchantedCarrotsBlock extends CarrotsBlock {
    public EnchantedCarrotsBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return SPMMain.ENCHANTED_CARROT_ITEM;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}
