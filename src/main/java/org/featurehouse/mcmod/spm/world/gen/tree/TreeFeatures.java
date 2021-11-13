package org.featurehouse.mcmod.spm.world.gen.tree;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.*;
import org.featurehouse.mcmod.spm.SPMMain;

import java.util.List;
import java.util.OptionalInt;

import static org.featurehouse.mcmod.spm.world.gen.tree.TreeFeatures.Constants.*;

/* (not javadoc)
* Most of the code are from {@code ConfiguredFeatures}.
* Don't you dare asking us for the algorithm CUZ WE DON'T KNOW EITHER.
*/

/**
 * @see net.minecraft.block.sapling.OakSaplingGenerator
 * @see ConfiguredFeatures#FANCY_OAK
 */
public final class TreeFeatures {
    private TreeFeatures() {
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(SPMMain.MODID, id), configuredFeature);
    }

    public static final ConfiguredFeature<TreeFeatureConfig, ?>
            FANCY_OAK, FANCY_OAK_BEES_005, OAK, OAK_BEES_005,
            SPRUCE, MEGA_SPRUCE, MEGA_PINE,
            BIRCH, BIRCH_BEES_005,
            JUNGLE_TREE_NO_VINE, MEGA_JUNGLE_TREE,
            ACACIA, DARK_OAK;

    static {
        FANCY_OAK = register("fancy_oak", Feature.TREE.configure(buildLargeOakTree().build()));
        FANCY_OAK_BEES_005 = register("fancy_oak_bees_005",
                Feature.TREE.configure(buildLargeOakTree()
                        .decorators(List.of(MORE_BEEHIVES_TREES)).build()));
        OAK = register("oak", Feature.TREE.configure(buildOakTree().build()));
        OAK_BEES_005 = register("oak_bees_005",
                Feature.TREE.configure(buildOakTree()
                        .decorators(List.of(MORE_BEEHIVES_TREES)).build()));
        SPRUCE = register("spruce",
                Feature.TREE.configure((new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(SPRUCE_LOG),
                        new StraightTrunkPlacer(5, 2, 1),
                        BlockStateProvider.of(ENCHANTED_SPRUCE_LEAVES),
                        new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(1, 2)),
                        new TwoLayersFeatureSize(2, 0, 2)))
                        .ignoreVines().build()));
        MEGA_SPRUCE = register("mega_spruce",
                Feature.TREE.configure((new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(SPRUCE_LOG),
                        new GiantTrunkPlacer(13, 2, 14),
                        BlockStateProvider.of(ENCHANTED_SPRUCE_LEAVES),
                        new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)),
                        new TwoLayersFeatureSize(1, 1, 2)))
                        .decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(PODZOL))))
                        .build()));
        MEGA_PINE = register("mega_pine",
                Feature.TREE.configure((new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(SPRUCE_LOG),
                        new GiantTrunkPlacer(13, 2, 14),
                        BlockStateProvider.of(ENCHANTED_SPRUCE_LEAVES),
                        new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(3, 7)),
                        new TwoLayersFeatureSize(1, 1, 2)))
                        .decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(PODZOL))))
                        .build()));
        BIRCH = register("birch", Feature.TREE.configure(buildBirchTree().build()));
        BIRCH_BEES_005 = register("birch_bees_005",
                Feature.TREE.configure(buildBirchTree().decorators(List.of(MORE_BEEHIVES_TREES)).build()));
        JUNGLE_TREE_NO_VINE = register("jungle_tree_no_vine",
                Feature.TREE.configure(buildJungleTree().ignoreVines().build()));
        MEGA_JUNGLE_TREE = register("mega_jungle_tree",
                Feature.TREE.configure((new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(JUNGLE_LOG),
                        new MegaJungleTrunkPlacer(10, 2, 19),
                        BlockStateProvider.of(ENCHANTED_JUNGLE_LEAVES),
                        new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
                        new TwoLayersFeatureSize(1, 1, 2)))
                        .decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE))
                        .build()));
        ACACIA = register("acacia",
                Feature.TREE.configure((new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(ACACIA_LOG),
                        new ForkingTrunkPlacer(5, 2, 2),
                        BlockStateProvider.of(ENCHANTED_ACACIA_LEAVES),
                        new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 0, 2)))
                        .ignoreVines().build()));
        DARK_OAK = register("dark_oak",
                Feature.TREE.configure((new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(DARK_OAK_LOG),
                        new DarkOakTrunkPlacer(6, 2, 1),
                        BlockStateProvider.of(ENCHANTED_DARK_OAK_LEAVES),
                        new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
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
            OAK_LOG = Blocks.OAK_LOG.getDefaultState();
            ENCHANTED_OAK_LEAVES = SPMMain.ENCHANTED_OAK_LEAVES.getDefaultState();
            ENCHANTED_OAK_SAPLING = SPMMain.ENCHANTED_OAK_SAPLING.getDefaultState();

            SPRUCE_LOG = Blocks.SPRUCE_LOG.getDefaultState();
            ENCHANTED_SPRUCE_LEAVES = SPMMain.ENCHANTED_SPRUCE_LEAVES.getDefaultState();
            ENCHANTED_SPRUCE_SAPLING = SPMMain.ENCHANTED_SPRUCE_SAPLING.getDefaultState();
            PODZOL = Blocks.PODZOL.getDefaultState();

            BIRCH_LOG = Blocks.BIRCH_LOG.getDefaultState();
            ENCHANTED_BIRCH_LEAVES = SPMMain.ENCHANTED_BIRCH_LEAVES.getDefaultState();
            ENCHANTED_BIRCH_SAPLING = SPMMain.ENCHANTED_BIRCH_SAPLING.getDefaultState();

            JUNGLE_LOG = Blocks.JUNGLE_LOG.getDefaultState();
            ENCHANTED_JUNGLE_LEAVES = SPMMain.ENCHANTED_JUNGLE_LEAVES.getDefaultState();
            ENCHANTED_JUNGLE_SAPLING = SPMMain.ENCHANTED_JUNGLE_SAPLING.getDefaultState();

            ACACIA_LOG = Blocks.ACACIA_LOG.getDefaultState();
            ENCHANTED_ACACIA_LEAVES = SPMMain.ENCHANTED_ACACIA_LEAVES.getDefaultState();
            ENCHANTED_ACACIA_SAPLING = SPMMain.ENCHANTED_ACACIA_SAPLING.getDefaultState();

            DARK_OAK_LOG = Blocks.DARK_OAK_LOG.getDefaultState();
            ENCHANTED_DARK_OAK_LEAVES = SPMMain.ENCHANTED_DARK_OAK_LEAVES.getDefaultState();
            ENCHANTED_DARK_OAK_SAPLING = SPMMain.ENCHANTED_DARK_OAK_SAPLING.getDefaultState();
        }

        public static final BeehiveTreeDecorator MORE_BEEHIVES_TREES = new BeehiveTreeDecorator(0.05F);
    }

    private static TreeFeatureConfig.Builder buildLargeOakTree() {
        return (new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.OAK_LOG),
                new LargeOakTrunkPlacer(3, 11, 0),
                BlockStateProvider.of(ENCHANTED_OAK_LEAVES),
                new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                .ignoreVines();
    }

    private static TreeFeatureConfig.Builder buildTree(Block trunkBlock, BlockState foliageBlock, int baseHeight, int firstRandomHeight) {
        return new TreeFeatureConfig.Builder(
                BlockStateProvider.of(trunkBlock),
                new StraightTrunkPlacer(baseHeight, firstRandomHeight, 0),
                BlockStateProvider.of(foliageBlock),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeFeatureConfig.Builder buildOakTree() {
        return buildTree(Blocks.OAK_LOG, ENCHANTED_OAK_LEAVES, 4, 2)
                .ignoreVines();
    }

    private static TreeFeatureConfig.Builder buildBirchTree() {
        return buildTree(Blocks.BIRCH_LOG, ENCHANTED_BIRCH_LEAVES, 5, 2)
                .ignoreVines();
    }

    private static TreeFeatureConfig.Builder buildJungleTree() {
        return buildTree(Blocks.JUNGLE_LOG, ENCHANTED_JUNGLE_LEAVES, 4, 8);
    }
}