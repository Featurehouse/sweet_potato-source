package io.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.util.RandomTickHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.PotatoesBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class EnchantedVanillaPotatoesBlock extends PotatoesBlock {
    public EnchantedVanillaPotatoesBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return SPMMain.ENCHANTED_VANILLA_POTATO_ITEM;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}
