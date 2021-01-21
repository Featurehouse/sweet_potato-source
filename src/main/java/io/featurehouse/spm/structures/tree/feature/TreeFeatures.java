package io.featurehouse.spm.structures.tree.feature;

import com.google.common.collect.ImmutableList;
import io.featurehouse.spm.SPMMain;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.OptionalInt;

import static io.featurehouse.spm.structures.tree.feature.TreeFeatures$BlockStates.*;

public final class TreeFeatures {
    private TreeFeatures() {}

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(SPMMain.MODID, id), configuredFeature);
    }

    public static final ConfiguredFeature<TreeFeatureConfig, ?>
            FANCY_OAK, FANCY_OAK_BEES_005, OAK, OAK_BEES_005;

    static {
        FANCY_OAK = register("fancy_oak", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(ENCHANTED_OAK_LEAVES), new LargeOakFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
        FANCY_OAK_BEES_005 = register("fancy_oak_bees_005", Feature.TREE.configure(FANCY_OAK.getConfig().setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES))));
        OAK = register("oak", Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(TreeFeatures$BlockStates.OAK_LOG), new SimpleBlockStateProvider(ENCHANTED_OAK_LEAVES), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
        OAK_BEES_005 = register("oak_bees_005", Feature.TREE.configure(OAK.getConfig().setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES))));
    }
}
