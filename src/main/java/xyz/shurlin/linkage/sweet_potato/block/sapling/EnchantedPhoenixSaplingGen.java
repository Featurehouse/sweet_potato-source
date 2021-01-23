package xyz.shurlin.linkage.sweet_potato.block.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import xyz.shurlin.linkage.sweet_potato.structure.tree.EnchantedTreeFeatures;

import java.util.Random;

public class EnchantedPhoenixSaplingGen extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return EnchantedTreeFeatures.ENCHANTED_PHOENIX;
    }
}
