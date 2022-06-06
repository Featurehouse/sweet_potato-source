package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.BirchTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedBirchSaplingGen extends BirchTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean canSpawnBees) {
        return canSpawnBees ? TreeFeatures.BIRCH_BEES_005 : TreeFeatures.BIRCH;
    }
}
