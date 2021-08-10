package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.AcaciaSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedAcaciaSaplingGen extends AcaciaSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bl) {
        return TreeFeatures.ACACIA;
    }
}
