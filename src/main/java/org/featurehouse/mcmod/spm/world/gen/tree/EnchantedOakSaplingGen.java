package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedOakSaplingGen extends OakSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean canSpawnBees) {
        if (random.nextInt(10) == 0) {
            return canSpawnBees ? TreeFeatures.FANCY_OAK_BEES_005 : TreeFeatures.FANCY_OAK;
        } else {
            return canSpawnBees ? TreeFeatures.OAK_BEES_005 : TreeFeatures.OAK;
        }
    }
}
