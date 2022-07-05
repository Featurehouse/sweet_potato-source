package org.featurehouse.mcmod.spm.world.gen.tree;

import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.JungleTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedJungleSaplingGen extends JungleTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bl) {
        return TreeFeatures.JUNGLE_TREE_NO_VINE;
    }

    @Override
    protected Holder<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return TreeFeatures.MEGA_JUNGLE_TREE;
    }
}
