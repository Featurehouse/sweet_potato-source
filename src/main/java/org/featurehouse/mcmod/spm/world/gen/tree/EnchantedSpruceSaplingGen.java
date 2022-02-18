package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.SpruceSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class EnchantedSpruceSaplingGen extends SpruceSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bl) {
        return TreeFeatures.SPRUCE;
    }

    @Override
    protected RegistryEntry<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return random.nextBoolean() ? TreeFeatures.MEGA_SPRUCE : TreeFeatures.MEGA_PINE;
    }
}
