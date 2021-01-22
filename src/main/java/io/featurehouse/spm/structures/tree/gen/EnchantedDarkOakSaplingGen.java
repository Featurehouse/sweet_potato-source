package io.featurehouse.spm.structures.tree.gen;

import io.featurehouse.spm.structures.tree.feature.TreeFeatures;
import net.minecraft.block.sapling.DarkOakSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import org.jetbrains.annotations.Nullable;
import java.util.Random;

public class EnchantedDarkOakSaplingGen extends DarkOakSaplingGenerator {
    @Override @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return super.createTreeFeature(random, bl); // null
    }

    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
        return TreeFeatures.DARK_OAK;
    }
}
