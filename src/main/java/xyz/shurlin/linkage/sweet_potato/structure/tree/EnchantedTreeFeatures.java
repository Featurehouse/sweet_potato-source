package xyz.shurlin.linkage.sweet_potato.structure.tree;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import xyz.shurlin.linkage.sweet_potato.ShurlinSPMLinkage;

import java.util.OptionalInt;

import static xyz.shurlin.linkage.sweet_potato.structure.tree.EnchantedTreeFeatures.BlockStates.ENCHANTED_PHOENIX_LEAVES;
import static xyz.shurlin.world.gen.feature.ShurlinBiomeFeatures.PHOENIX_LOG;

public final class EnchantedTreeFeatures {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> ENCHANTED_PHOENIX;

    static {
        ENCHANTED_PHOENIX = Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PHOENIX_LOG), new SimpleBlockStateProvider(ENCHANTED_PHOENIX_LEAVES), new DarkOakFoliagePlacer(0, 0, 0, 0), new DarkOakTrunkPlacer(6, 2, 1), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).ignoreVines().build());
    }

    protected static final class BlockStates {
        public static final BlockState ENCHANTED_PHOENIX_LEAVES;

        static {
            ENCHANTED_PHOENIX_LEAVES = ShurlinSPMLinkage.ENCHANTED_PHOENIX_LEAVES.getDefaultState();
        }
    }
}
