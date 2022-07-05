package org.featurehouse.mcmod.spm.world.gen.tree;

import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedOakSaplingGen extends OakTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean canSpawnBees) {
        if (random.nextInt(10) == 0) {
            return canSpawnBees ? TreeFeatures.FANCY_OAK_BEES_005 : TreeFeatures.FANCY_OAK;
        } else {
            return canSpawnBees ? TreeFeatures.OAK_BEES_005 : TreeFeatures.OAK;
        }
    }
}
