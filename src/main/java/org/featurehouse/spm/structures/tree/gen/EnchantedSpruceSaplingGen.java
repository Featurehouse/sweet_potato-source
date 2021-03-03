package org.featurehouse.spm.structures.tree.gen;

import org.featurehouse.spm.structures.tree.feature.TreeFeatures;
import net.minecraft.block.sapling.SpruceSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedSpruceSaplingGen extends SpruceSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return TreeFeatures.SPRUCE;
    }

    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
        return random.nextBoolean() ? TreeFeatures.MEGA_SPRUCE : TreeFeatures.MEGA_PINE;
    }
}
