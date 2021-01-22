package io.featurehouse.spm.structures.tree.feature;

import com.google.common.collect.ImmutableList;
import io.featurehouse.spm.SPMMain;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.tree.AlterGroundTreeDecorator;
import net.minecraft.world.gen.tree.LeaveVineTreeDecorator;
import net.minecraft.world.gen.tree.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.*;

import java.util.OptionalInt;

import static io.featurehouse.spm.structures.tree.feature.TreeFeatures$BlockStates.*;

public final class TreeFeatures {
    private TreeFeatures() {}

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(SPMMain.MODID, id), configuredFeature);
    }

    public static final ConfiguredFeature<TreeFeatureConfig, ?>
            FANCY_OAK, FANCY_OAK_BEES_005, OAK, OAK_BEES_005,
            SPRUCE, MEGA_SPRUCE, MEGA_PINE,
            BIRCH, BIRCH_BEES_005,
            JUNGLE_TREE_NO_VINE, MEGA_JUNGLE_TREE,
            ACACIA,                     DARK_OAK;

    static {
        FANCY_OAK = register("fancy_oak", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(ENCHANTED_OAK_LEAVES), new LargeOakFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
        FANCY_OAK_BEES_005 = register("fancy_oak_bees_005", Feature.TREE.configure(FANCY_OAK.getConfig().setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES))));
        OAK = register("oak", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(TreeFeatures$BlockStates.OAK_LOG), new SimpleBlockStateProvider(ENCHANTED_OAK_LEAVES), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
        OAK_BEES_005 = register("oak_bees_005", Feature.TREE.configure(OAK.getConfig().setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES))));

        SPRUCE = register("spruce", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(SPRUCE_LOG), new SimpleBlockStateProvider(ENCHANTED_SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformIntDistribution.of(2, 1), UniformIntDistribution.of(0, 2), UniformIntDistribution.of(1, 1)), new StraightTrunkPlacer(5, 2, 1), new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build()));
        MEGA_SPRUCE = register("mega_spruce", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(SPRUCE_LOG), new SimpleBlockStateProvider(ENCHANTED_SPRUCE_LEAVES), new MegaPineFoliagePlacer(UniformIntDistribution.of(0), UniformIntDistribution.of(0), UniformIntDistribution.of(13, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(PODZOL)))).build()));
        MEGA_PINE = register("mega_pine", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(SPRUCE_LOG), new SimpleBlockStateProvider(ENCHANTED_SPRUCE_LEAVES), new MegaPineFoliagePlacer(UniformIntDistribution.of(0), UniformIntDistribution.of(0), UniformIntDistribution.of(3, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(PODZOL)))).build()));

        BIRCH = register("birch", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BIRCH_LOG), new SimpleBlockStateProvider(ENCHANTED_BIRCH_LEAVES), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));
        BIRCH_BEES_005 = register("birch_bees_005", Feature.TREE.configure(BIRCH.getConfig().setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES))));

        JUNGLE_TREE_NO_VINE = register("jungle_tree_no_vine", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(JUNGLE_LOG), new SimpleBlockStateProvider(ENCHANTED_JUNGLE_LEAVES), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
        MEGA_JUNGLE_TREE = register("mega_jungle_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(JUNGLE_LOG), new SimpleBlockStateProvider(ENCHANTED_JUNGLE_LEAVES), new JungleFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 2), new MegaJungleTrunkPlacer(10, 2, 19), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)).build()));

        ACACIA = register("acacia", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ACACIA_LOG), new SimpleBlockStateProvider(ENCHANTED_ACACIA_LEAVES), new AcaciaFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0)), new ForkingTrunkPlacer(5, 2, 2), new TwoLayersFeatureSize(1, 0, 2)).ignoreVines().build()));

        DARK_OAK = register("dark_oak", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DARK_OAK_LOG), new SimpleBlockStateProvider(ENCHANTED_DARK_OAK_LEAVES), new DarkOakFoliagePlacer(UniformIntDistribution.of(0), UniformIntDistribution.of(0)), new DarkOakTrunkPlacer(6, 2, 1), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).maxWaterDepth(2147483647).heightmap(Heightmap.Type.MOTION_BLOCKING).ignoreVines().build()));

    }
}
