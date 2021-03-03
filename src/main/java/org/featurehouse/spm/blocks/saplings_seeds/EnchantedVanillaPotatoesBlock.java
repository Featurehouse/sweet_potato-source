package org.featurehouse.spm.blocks.saplings_seeds;

import org.featurehouse.spm.SPMMain;
import org.featurehouse.spm.util.RandomTickHelper;
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
