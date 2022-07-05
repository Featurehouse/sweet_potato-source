package org.featurehouse.mcmod.spm.world.gen.tree;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.*;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;

import java.util.List;
import java.util.OptionalInt;

import static org.featurehouse.mcmod.spm.world.gen.tree.TreeFeatures.Constants.*;

/* (not javadoc)
* Most of the code are from {@code ConfiguredFeatures}
* and {@code TreeConfiguredFeatures}.<br />
* Don't ask us for the algorithm CUZ WE DON'T KNOW EITHER.
*/

/**
 * @see net.minecraft.world.level.block.grower.OakTreeGrower
 * @see net.minecraft.data.worldgen.features.TreeFeatures#FANCY_OAK
 */
public final class TreeFeatures {
    private TreeFeatures() {
    }

    private static <FC extends TreeConfiguration> Holder<ConfiguredFeature<?, ?>> register(String id, FC featureConfig) {
        //return BuiltinRegistries.method_40360(BuiltinRegistries.CONFIGURED_FEATURE, RegistryHelper.id(id), configuredFeature);
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, RegistryHelper.id(id), new ConfiguredFeature<>(Feature.TREE, featureConfig));
    }

    public static final Holder<ConfiguredFeature<?, ?>>
            FANCY_OAK, FANCY_OAK_BEES_005, OAK, OAK_BEES_005,
            SPRUCE, MEGA_SPRUCE, MEGA_PINE,
            BIRCH, BIRCH_BEES_005,
            JUNGLE_TREE_NO_VINE, MEGA_JUNGLE_TREE,
            ACACIA, DARK_OAK;

    static {
        FANCY_OAK = register("fancy_oak", (largeOak().build()));
        FANCY_OAK_BEES_005 = register("fancy_oak_bees_005",
                (largeOak().decorators(List.of(MORE_BEEHIVES_TREES)).build()));
        OAK = register("oak", (oak().build()));
        OAK_BEES_005 = register("oak_bees_005",
                (oak().decorators(List.of(MORE_BEEHIVES_TREES)).build()));
        SPRUCE = register("spruce",
                ((new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(SPRUCE_LOG),
                        new StraightTrunkPlacer(5, 2, 1),
                        BlockStateProvider.simple(ENCHANTED_SPRUCE_LEAVES),
                        new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)),
                        new TwoLayersFeatureSize(2, 0, 2)))
                        .ignoreVines().build()));
        MEGA_SPRUCE = register("mega_spruce",
                ((new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(SPRUCE_LOG),
                        new GiantTrunkPlacer(13, 2, 14),
                        BlockStateProvider.simple(ENCHANTED_SPRUCE_LEAVES),
                        new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
                        new TwoLayersFeatureSize(1, 1, 2)))
                        .decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(PODZOL))))
                        .build()));
        MEGA_PINE = register("mega_pine",
                ((new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(SPRUCE_LOG),
                        new GiantTrunkPlacer(13, 2, 14),
                        BlockStateProvider.simple(ENCHANTED_SPRUCE_LEAVES),
                        new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)),
                        new TwoLayersFeatureSize(1, 1, 2)))
                        .decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(PODZOL))))
                        .build()));
        BIRCH = register("birch", (birch().build()));
        BIRCH_BEES_005 = register("birch_bees_005",
                (birch().decorators(List.of(MORE_BEEHIVES_TREES)).build()));
        JUNGLE_TREE_NO_VINE = register("jungle_tree_no_vine",
                (jungle().ignoreVines().build()));
        MEGA_JUNGLE_TREE = register("mega_jungle_tree",
                ((new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(JUNGLE_LOG),
                        new MegaJungleTrunkPlacer(10, 2, 19),
                        BlockStateProvider.simple(ENCHANTED_JUNGLE_LEAVES),
                        new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                        new TwoLayersFeatureSize(1, 1, 2)))
                        .decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, LeaveVineDecorator.INSTANCE))
                        .build()));
        ACACIA = register("acacia",
                ((new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ACACIA_LOG),
                        new ForkingTrunkPlacer(5, 2, 2),
                        BlockStateProvider.simple(ENCHANTED_ACACIA_LEAVES),
                        new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)))
                        .ignoreVines().build()));
        DARK_OAK = register("dark_oak",
                ((new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DARK_OAK_LOG),
                        new DarkOakTrunkPlacer(6, 2, 1),
                        BlockStateProvider.simple(ENCHANTED_DARK_OAK_LEAVES),
                        new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())))
                        .ignoreVines().build()));
    }

    static final class Constants {
        public static final BlockState OAK_LOG;
        public static final BlockState ENCHANTED_OAK_LEAVES;
        public static final BlockState ENCHANTED_OAK_SAPLING;

        public static final BlockState SPRUCE_LOG;
        public static final BlockState ENCHANTED_SPRUCE_LEAVES;
        public static final BlockState ENCHANTED_SPRUCE_SAPLING;
        public static final BlockState PODZOL;

        public static final BlockState BIRCH_LOG;
        public static final BlockState ENCHANTED_BIRCH_LEAVES;
        public static final BlockState ENCHANTED_BIRCH_SAPLING;

        public static final BlockState JUNGLE_LOG;
        public static final BlockState ENCHANTED_JUNGLE_LEAVES;
        public static final BlockState ENCHANTED_JUNGLE_SAPLING;

        public static final BlockState ACACIA_LOG;
        public static final BlockState ENCHANTED_ACACIA_LEAVES;
        public static final BlockState ENCHANTED_ACACIA_SAPLING;

        public static final BlockState DARK_OAK_LOG;
        public static final BlockState ENCHANTED_DARK_OAK_LEAVES;
        public static final BlockState ENCHANTED_DARK_OAK_SAPLING;

        static {
            OAK_LOG = Blocks.OAK_LOG.defaultBlockState();
            ENCHANTED_OAK_LEAVES = SPMMain.ENCHANTED_OAK_LEAVES.defaultBlockState();
            ENCHANTED_OAK_SAPLING = SPMMain.ENCHANTED_OAK_SAPLING.defaultBlockState();

            SPRUCE_LOG = Blocks.SPRUCE_LOG.defaultBlockState();
            ENCHANTED_SPRUCE_LEAVES = SPMMain.ENCHANTED_SPRUCE_LEAVES.defaultBlockState();
            ENCHANTED_SPRUCE_SAPLING = SPMMain.ENCHANTED_SPRUCE_SAPLING.defaultBlockState();
            PODZOL = Blocks.PODZOL.defaultBlockState();

            BIRCH_LOG = Blocks.BIRCH_LOG.defaultBlockState();
            ENCHANTED_BIRCH_LEAVES = SPMMain.ENCHANTED_BIRCH_LEAVES.defaultBlockState();
            ENCHANTED_BIRCH_SAPLING = SPMMain.ENCHANTED_BIRCH_SAPLING.defaultBlockState();

            JUNGLE_LOG = Blocks.JUNGLE_LOG.defaultBlockState();
            ENCHANTED_JUNGLE_LEAVES = SPMMain.ENCHANTED_JUNGLE_LEAVES.defaultBlockState();
            ENCHANTED_JUNGLE_SAPLING = SPMMain.ENCHANTED_JUNGLE_SAPLING.defaultBlockState();

            ACACIA_LOG = Blocks.ACACIA_LOG.defaultBlockState();
            ENCHANTED_ACACIA_LEAVES = SPMMain.ENCHANTED_ACACIA_LEAVES.defaultBlockState();
            ENCHANTED_ACACIA_SAPLING = SPMMain.ENCHANTED_ACACIA_SAPLING.defaultBlockState();

            DARK_OAK_LOG = Blocks.DARK_OAK_LOG.defaultBlockState();
            ENCHANTED_DARK_OAK_LEAVES = SPMMain.ENCHANTED_DARK_OAK_LEAVES.defaultBlockState();
            ENCHANTED_DARK_OAK_SAPLING = SPMMain.ENCHANTED_DARK_OAK_SAPLING.defaultBlockState();
        }

        public static final BeehiveDecorator MORE_BEEHIVES_TREES = new BeehiveDecorator(0.05F);
    }

    private static TreeConfiguration.TreeConfigurationBuilder largeOak() {
        return (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new FancyTrunkPlacer(3, 11, 0),
                BlockStateProvider.simple(ENCHANTED_OAK_LEAVES),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                .ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder tree(Block trunkBlock, BlockState foliageBlock, int baseHeight, int firstRandomHeight) {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(trunkBlock),
                new StraightTrunkPlacer(baseHeight, firstRandomHeight, 0),
                BlockStateProvider.simple(foliageBlock),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder oak() {
        return tree(Blocks.OAK_LOG, ENCHANTED_OAK_LEAVES, 4, 2)
                .ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder birch() {
        return tree(Blocks.BIRCH_LOG, ENCHANTED_BIRCH_LEAVES, 5, 2)
                .ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder jungle() {
        return tree(Blocks.JUNGLE_LOG, ENCHANTED_JUNGLE_LEAVES, 4, 8);
    }
}