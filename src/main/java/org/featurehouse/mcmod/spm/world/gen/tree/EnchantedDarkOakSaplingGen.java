package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.DarkOakSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import org.jetbrains.annotations.Nullable;
import java.util.Random;

public class EnchantedDarkOakSaplingGen extends DarkOakSaplingGenerator {
    @Override @Nullable
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bl) {
        return super.getTreeFeature(random, bl); // null
    }

    @Override
    protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
        return TreeFeatures.DARK_OAK;
    }
}
