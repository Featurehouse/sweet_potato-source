package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.JungleTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedJungleSaplingGen extends JungleTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean bl) {
        return TreeFeatures.JUNGLE_TREE_NO_VINE;
    }

    @Override
    protected Holder<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return TreeFeatures.MEGA_JUNGLE_TREE;
    }
}
