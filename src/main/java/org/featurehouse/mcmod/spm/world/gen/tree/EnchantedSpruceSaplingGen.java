package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedSpruceSaplingGen extends SpruceTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean bl) {
        return TreeFeatures.SPRUCE;
    }

    @Override
    protected Holder<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return random.nextBoolean() ? TreeFeatures.MEGA_SPRUCE : TreeFeatures.MEGA_PINE;
    }
}
