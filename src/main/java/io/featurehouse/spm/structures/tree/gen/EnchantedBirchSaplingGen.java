package io.featurehouse.spm.structures.tree.gen;

import io.featurehouse.spm.structures.tree.feature.TreeFeatures;
import net.minecraft.block.sapling.BirchSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedBirchSaplingGen extends BirchSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean canSpawnBees) {
        return canSpawnBees ? TreeFeatures.BIRCH_BEES_005 : TreeFeatures.BIRCH;
    }
}
