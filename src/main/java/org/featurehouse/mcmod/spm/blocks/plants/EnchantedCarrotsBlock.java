package org.featurehouse.mcmod.spm.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.tick.RandomTickHelper;

public class EnchantedCarrotsBlock extends CarrotBlock {
    public EnchantedCarrotsBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SPMMain.ENCHANTED_CARROT_ITEM;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}
