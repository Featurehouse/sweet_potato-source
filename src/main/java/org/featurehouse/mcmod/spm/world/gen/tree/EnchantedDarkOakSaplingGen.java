package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.DarkOakSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class EnchantedDarkOakSaplingGen extends DarkOakSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bl) {
        return super.getTreeFeature(random, bl); // null
    }

    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return TreeFeatures.DARK_OAK;
    }
}
