package io.featurehouse.spm.blocks.saplings_seeds;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.util.RandomTickHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.NotNull;
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
    public void randomTick(BlockState state, @NotNull ServerWorld world, BlockPos pos, Random random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}
