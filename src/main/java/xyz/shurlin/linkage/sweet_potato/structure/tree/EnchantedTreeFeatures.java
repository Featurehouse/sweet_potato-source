package xyz.shurlin.linkage.sweet_potato.structure.tree;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import xyz.shurlin.linkage.sweet_potato.ShurlinSPMLinkage;

import java.util.OptionalInt;

import static xyz.shurlin.linkage.sweet_potato.structure.tree.EnchantedTreeFeatures.BlockStates.ENCHANTED_PHOENIX_LEAVES;
import static xyz.shurlin.world.gen.feature.ShurlinBiomeFeatures.PEAR_LOG;
import static xyz.shurlin.world.gen.feature.ShurlinBiomeFeatures.PHOENIX_LOG;

public final class EnchantedTreeFeatures {
    public static final ConfiguredFeature<TreeFeatureConfig, ?>
            ENCHANTED_PHOENIX, ENCHANTED_PEAR;

    static {
        ENCHANTED_PHOENIX = Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PHOENIX_LOG), new SimpleBlockStateProvider(ENCHANTED_PHOENIX_LEAVES), new DarkOakFoliagePlacer(0, 0, 0, 0), new DarkOakTrunkPlacer(6, 2, 1), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).ignoreVines().build());
        ENCHANTED_PEAR = Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PEAR_LOG), BlockStates.Providers.ENCHANTED_PEAR_LEAVES_PROVIDER, new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).build());
    }

    protected static final class BlockStates {
        public static final BlockState ENCHANTED_PHOENIX_LEAVES;
        public static final BlockState ENCHANTED_PEAR_LEAVES;
        public static final BlockState ENCHANTED_PEAR_RIPE_LEAVES;

        static {
            ENCHANTED_PHOENIX_LEAVES = ShurlinSPMLinkage.ENCHANTED_PHOENIX_LEAVES.getDefaultState();
            ENCHANTED_PEAR_LEAVES = ShurlinSPMLinkage.ENCHANTED_PEAR_LEAVES.getDefaultState();
            ENCHANTED_PEAR_RIPE_LEAVES = ShurlinSPMLinkage.ENCHANTED_PEAR_RIPE_LEAVES.getDefaultState();
        }

        protected static final class Providers {
            public static final BlockStateProvider ENCHANTED_PEAR_LEAVES_PROVIDER = new WeightedBlockStateProvider().addState(ENCHANTED_PEAR_LEAVES, 9).addState(ENCHANTED_PEAR_RIPE_LEAVES, 1);
        }
    }
}
