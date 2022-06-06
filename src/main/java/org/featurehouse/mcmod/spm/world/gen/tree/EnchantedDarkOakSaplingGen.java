package org.featurehouse.mcmod.spm.world.gen.tree;

import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.DarkOakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedDarkOakSaplingGen extends DarkOakTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean bl) {
        return super.getConfiguredFeature(randomSource, bl); // null
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return TreeFeatures.DARK_OAK;
    }
}
