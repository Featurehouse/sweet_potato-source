package xyz.shurlin.linkage.sweet_potato.block.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import xyz.shurlin.linkage.sweet_potato.world.gen.tree.EnchantedTreeFeatures;

import java.util.Random;

public class EnchantedPearSaplingGen extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bl) {
        return EnchantedTreeFeatures.ENCHANTED_PEAR;
    }
}
