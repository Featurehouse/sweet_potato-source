package org.featurehouse.mcmod.spm.world.gen.tree;

import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.DarkOakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class EnchantedDarkOakSaplingGen extends DarkOakTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bl) {
        return super.getConfiguredFeature(random, bl); // null
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return TreeFeatures.DARK_OAK;
    }
}
