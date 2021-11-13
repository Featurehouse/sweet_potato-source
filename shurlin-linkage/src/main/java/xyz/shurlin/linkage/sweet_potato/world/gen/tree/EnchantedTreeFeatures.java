package xyz.shurlin.linkage.sweet_potato.world.gen.tree;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import xyz.shurlin.block.Blocks;
import xyz.shurlin.linkage.sweet_potato.ShurlinSPMLinkage;

import java.util.OptionalInt;

import static xyz.shurlin.linkage.sweet_potato.world.gen.tree.EnchantedTreeFeatures.Constants.*;

/**
 * @see xyz.shurlin.world.gen.feature.ShurlinConfiguredFeatures
 */
public final class EnchantedTreeFeatures {
    public static final ConfiguredFeature<TreeFeatureConfig, ?>
            ENCHANTED_PHOENIX, ENCHANTED_PEAR;

    static {
        ENCHANTED_PEAR = register("pear_tree", Feature.TREE.configure(Configs.PEAR_TREE_CONFIG));
        ENCHANTED_PHOENIX = register("phoenix_tree", Feature.TREE.configure(Configs.PHOENIX_TREE_CONFIG));
    }

    static final class Configs {
        private static final TreeFeatureConfig PEAR_TREE_CONFIG;
        private static final TreeFeatureConfig PHOENIX_TREE_CONFIG;

        static {
            PEAR_TREE_CONFIG = new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(PEAR_LOG),
                    new StraightTrunkPlacer(5, 2, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(ENCHANTED_PEAR_LEAVES, 9).add(ENCHANTED_PEAR_RIPE_LEAVES, 1)),
                    new SimpleBlockStateProvider(ENCHANTED_PEAR_SAPLING),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .build();
            PHOENIX_TREE_CONFIG = new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(PHOENIX_LOG),
                    new DarkOakTrunkPlacer(6, 2, 1),
                    new SimpleBlockStateProvider(ENCHANTED_PHOENIX_LEAVES),
                    new SimpleBlockStateProvider(ENCHANTED_PHOENIX_SAPLING),
                    new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                    new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))
                    .build();
        }
    }

    static final class Constants {
        static final BlockState PEAR_LOG, PHOENIX_LOG;

        public static final BlockState ENCHANTED_PHOENIX_LEAVES;
        public static final BlockState ENCHANTED_PEAR_LEAVES;
        public static final BlockState ENCHANTED_PEAR_RIPE_LEAVES;
        public static final BlockState ENCHANTED_PEAR_SAPLING;
        public static final BlockState ENCHANTED_PHOENIX_SAPLING;

        static {
            PEAR_LOG = Blocks.PEAR_LOG.getDefaultState();
            PHOENIX_LOG = Blocks.PHOENIX_LOG.getDefaultState();

            ENCHANTED_PHOENIX_LEAVES = ShurlinSPMLinkage.ENCHANTED_PHOENIX_LEAVES.getDefaultState();
            ENCHANTED_PEAR_LEAVES = ShurlinSPMLinkage.ENCHANTED_PEAR_LEAVES.getDefaultState();
            ENCHANTED_PEAR_RIPE_LEAVES = ShurlinSPMLinkage.ENCHANTED_PEAR_RIPE_LEAVES.getDefaultState();
            ENCHANTED_PEAR_SAPLING = ShurlinSPMLinkage.ENCHANTED_PEAR_SAPLING.getDefaultState();
            ENCHANTED_PHOENIX_SAPLING = ShurlinSPMLinkage.ENCHANTED_PHOENIX_SAPLING.getDefaultState();
        }
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return register(RegistryHelper.id(id), configuredFeature);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(Identifier id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }
}
