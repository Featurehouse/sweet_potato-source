package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class EnchantedOakSaplingGen extends OakSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean canSpawnBees) {
        if (random.nextInt(10) == 0) {
            return canSpawnBees ? TreeFeatures.FANCY_OAK_BEES_005 : TreeFeatures.FANCY_OAK;
        } else {
            return canSpawnBees ? TreeFeatures.OAK_BEES_005 : TreeFeatures.OAK;
        }
    }
}
