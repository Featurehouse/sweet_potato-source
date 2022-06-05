package org.featurehouse.mcmod.spm.world.gen.tree;

import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.BirchTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedBirchSaplingGen extends BirchTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean canSpawnBees) {
        return canSpawnBees ? TreeFeatures.BIRCH_BEES_005 : TreeFeatures.BIRCH;
    }
}
