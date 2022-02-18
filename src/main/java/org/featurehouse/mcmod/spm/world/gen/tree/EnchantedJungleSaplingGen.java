package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.JungleSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class EnchantedJungleSaplingGen extends JungleSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bl) {
        return TreeFeatures.JUNGLE_TREE_NO_VINE;
    }

    @Override
    protected RegistryEntry<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return TreeFeatures.MEGA_JUNGLE_TREE;
    }
}
