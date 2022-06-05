package org.featurehouse.mcmod.spm.blocks.plants;

import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.tick.RandomTickHelper;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantedVanillaPotatoesBlock extends PotatoBlock {
    public EnchantedVanillaPotatoesBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SPMMain.ENCHANTED_VANILLA_POTATO_ITEM;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}
