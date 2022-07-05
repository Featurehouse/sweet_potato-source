package org.featurehouse.mcmod.spm.util.objsettings;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.featurehouse.mcmod.spm.blocks.plants.EnchantedSaplings;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;

import java.util.function.Supplier;

public final class BlockSettings {
    public static BlockBehaviour.Properties functionalMinable(Material material, float hardness, float blastResistance) {
        return FabricBlockSettings.of(material).destroyTime(hardness).explosionResistance(blastResistance).requiresCorrectToolForDrops();
    }

    public static EnchantedSaplings createEnchantedSapling(String id, Supplier<AbstractTreeGrower> saplingGeneratorSupplier) {
        return (EnchantedSaplings) RegistryHelper.block(id, new EnchantedSaplings(saplingGeneratorSupplier.get(), grassLike()));
    }

    public static FlowerPotBlock createPotted(String id, Block inside) {
        return (FlowerPotBlock) RegistryHelper.block(id, new FlowerPotBlock(inside, FabricBlockSettings.of(Material.DECORATION)));
    }

    public static FabricBlockSettings grassLike() { return FabricBlockSettings.copyOf(GRASS_LIKE); }
    public static FabricBlockSettings grass() { return FabricBlockSettings.copyOf(GRASS); }

    private static final BlockBehaviour.Properties GRASS_LIKE;
    private static final BlockBehaviour.Properties GRASS;

    private BlockSettings() {}

    static {
        GRASS_LIKE = FabricBlockSettings.of(Materials.MATERIAL_PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.CROP);
        GRASS = FabricBlockSettings.of(Materials.MATERIAL_PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS);
    }

    static boolean canSpawnOnLeaves(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    public static LeavesBlock createLeaves(String id) {
        return (LeavesBlock) RegistryHelper.block(id,
                new LeavesBlock(FabricBlockSettings.of(Material.LEAVES)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isValidSpawn(BlockSettings::canSpawnOnLeaves)
                        .isSuffocating(BlockSettings::alwaysFalse)
                        .isViewBlocking(BlockSettings::alwaysFalse)));
    }

    @Deprecated(forRemoval = true)
    public static LeavesBlock createEnchantedLeavesBlock() {
        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn(BlockSettings::canSpawnOnLeaves)
                .isSuffocating(BlockSettings::alwaysFalse)
                .isViewBlocking(BlockSettings::alwaysFalse)
        );
    }

    private static boolean alwaysFalse(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }
}