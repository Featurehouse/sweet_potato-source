package org.featurehouse.mcmod.spm.world.gen.tree;

import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedSpruceSaplingGen extends SpruceTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bl) {
        return TreeFeatures.SPRUCE;
    }

    @Override
    protected Holder<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return random.nextBoolean() ? TreeFeatures.MEGA_SPRUCE : TreeFeatures.MEGA_PINE;
    }
}
