package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.block.sapling.AcaciaSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class EnchantedAcaciaSaplingGen extends AcaciaSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bl) {
        return TreeFeatures.ACACIA;
    }
}
