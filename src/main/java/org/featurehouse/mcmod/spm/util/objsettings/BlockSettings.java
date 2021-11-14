package org.featurehouse.mcmod.spm.util.objsettings;

import org.featurehouse.mcmod.spm.blocks.plants.EnchantedSaplings;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public final class BlockSettings {
    public static FabricBlockSettings functionalMinable(Material material, float hardness, float blastResistance) {
        return FabricBlockSettings.of(material).hardness(hardness).resistance(blastResistance).requiresTool();
    }

    public static EnchantedSaplings createEnchantedSapling(String id, Supplier<SaplingGenerator> saplingGeneratorSupplier) {
        return (EnchantedSaplings) RegistryHelper.block(id, new EnchantedSaplings(saplingGeneratorSupplier.get(), grassLike()));
    }

    public static FlowerPotBlock createPotted(String id, Block inside) {
        return (FlowerPotBlock) RegistryHelper.block(id, new FlowerPotBlock(inside, FabricBlockSettings.of(Material.DECORATION)));
    }

    public static FabricBlockSettings grassLike() { return FabricBlockSettings.copyOf(GRASS_LIKE); }
    public static FabricBlockSettings grass() { return FabricBlockSettings.copyOf(GRASS); }

    private static final FabricBlockSettings GRASS_LIKE;
    private static final FabricBlockSettings GRASS;

    private BlockSettings() {}

    @Deprecated(forRemoval = true)
    public static FabricBlockSettings create(AbstractBlock.Settings settings) {
        //return (FabricBlockSettings)settings;
        return FabricBlockSettings.copyOf(settings);
    }

    static {
        GRASS_LIKE = FabricBlockSettings.of(Materials.MATERIAL_PLANT) // Wanted: move MATERIAL_PLANT to Util
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP);
        GRASS = FabricBlockSettings.of(Materials.MATERIAL_PLANT)
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.GRASS);
    }

    static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    public static LeavesBlock createLeaves(String id) {
        return (LeavesBlock) RegistryHelper.block(id, new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlockSettings::canSpawnOnLeaves).suffocates((state, world, pos) -> false).blockVision((state, world, pos) -> false)));
    }

    @Deprecated
    public static LeavesBlock createEnchantedLeavesBlock() {
        return new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES)
                .strength(0.2F)
                .ticksRandomly()
                .sounds(BlockSoundGroup.GRASS)
                .nonOpaque()
                .allowsSpawning(BlockSettings::canSpawnOnLeaves)
                .suffocates((state, world, pos) -> false)
                .blockVision((state, world, pos) -> false)
        );
    }
}