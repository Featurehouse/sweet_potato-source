package io.github.teddyxlandlee.sweet_potato.util.properties.objects;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public final class BlockSettings {
    public static final FabricBlockSettings GRASS_LIKE;
    public static final FabricBlockSettings GRASS;

    private BlockSettings() {}

    @Deprecated
    public static FabricBlockSettings create(AbstractBlock.Settings settings) {
        return (FabricBlockSettings)settings;
    }

    static {
        GRASS_LIKE = FabricBlockSettings.of(SPMMain.MATERIAL_PLANT) // Wanted: move MATERIAL_PLANT to Util
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP);
        GRASS = FabricBlockSettings.of(SPMMain.MATERIAL_PLANT)
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.GRASS);
    }

    protected static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

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