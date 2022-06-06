package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AcaciaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedAcaciaSaplingGen extends AcaciaTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean bl) {
        return TreeFeatures.ACACIA;
    }
}
