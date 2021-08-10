package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.JungleSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedJungleSaplingGen extends JungleSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bl) {
        return TreeFeatures.JUNGLE_TREE_NO_VINE;
    }

    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getLargeTreeFeature(Random random) {
        return TreeFeatures.MEGA_JUNGLE_TREE;
    }
}
