package io.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.util.RandomTickHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.Random;

public class EnchantedWheatBlock extends CropBlock {
    public EnchantedWheatBlock(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemConvertible getSeedsItem() {
        return SPMMain.ENCHANTED_WHEAT_SEEDS;
    }

    @Override
    public void randomTick(BlockState state, @Nonnull ServerWorld world, BlockPos pos, Random random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}
