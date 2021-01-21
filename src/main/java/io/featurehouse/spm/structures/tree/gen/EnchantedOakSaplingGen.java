package io.featurehouse.spm.structures.tree.gen;

import io.featurehouse.spm.structures.tree.feature.TreeFeatures;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedOakSaplingGen extends OakSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        if (random.nextInt(10) == 0) {
            return bl ? TreeFeatures.FANCY_OAK_BEES_005 : TreeFeatures.FANCY_OAK;
        } else {
            return bl ? TreeFeatures.OAK_BEES_005 : TreeFeatures.OAK;
        }
    }
}
