package org.featurehouse.spm.structures.tree.gen;

import org.featurehouse.spm.structures.tree.feature.TreeFeatures;
import net.minecraft.block.sapling.AcaciaSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class EnchantedAcaciaSaplingGen extends AcaciaSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return TreeFeatures.ACACIA;
    }
}
