package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.BirchSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class EnchantedBirchSaplingGen extends BirchSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean canSpawnBees) {
        return canSpawnBees ? TreeFeatures.BIRCH_BEES_005 : TreeFeatures.BIRCH;
    }
}
