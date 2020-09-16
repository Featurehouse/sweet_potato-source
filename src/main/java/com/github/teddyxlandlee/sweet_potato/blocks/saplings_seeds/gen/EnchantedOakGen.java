package com.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds.gen;

import com.github.teddyxlandlee.annotation.Unused_InsteadOf;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

@Unused_InsteadOf
@Deprecated
public class EnchantedOakGen extends OakSaplingGenerator {
    @Nullable
    @Deprecated
    protected ConfiguredFeature<TreeFeatureConfig, ?> oldCreateTreeFeature(Random random, boolean bl) {
        return random.nextInt(10) == 0 ?
                Feature.TREE.configure(
                    bl ? DefaultBiomeFeatures.FANCY_TREE_WITH_MORE_BEEHIVES_CONFIG :
                            DefaultBiomeFeatures.FANCY_TREE_CONFIG
                )
                :
                Feature.TREE.configure(
                    bl ? DefaultBiomeFeatures.OAK_TREE_WITH_MORE_BEEHIVES_CONFIG :
                            DefaultBiomeFeatures.OAK_TREE_CONFIG
                );
    }

}
