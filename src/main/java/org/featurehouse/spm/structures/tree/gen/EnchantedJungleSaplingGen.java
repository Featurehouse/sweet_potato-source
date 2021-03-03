package org.featurehouse.spm.structures.tree.gen;

import org.featurehouse.spm.structures.tree.feature.TreeFeatures;
import net.minecraft.block.sapling.JungleSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedJungleSaplingGen extends JungleSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return TreeFeatures.JUNGLE_TREE_NO_VINE;
    }

    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
        return TreeFeatures.MEGA_JUNGLE_TREE;
    }
}
