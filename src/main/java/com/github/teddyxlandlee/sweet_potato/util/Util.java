package com.github.teddyxlandlee.sweet_potato.util;

import com.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import com.github.teddyxlandlee.annotation.Unused_InsteadOf;
import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.SharedConstants;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public final class Util {
    private Util() {}

    /*@Unused_InsteadOf @Deprecated
    public static void registerFurnaceFuel(Map<Item, Integer> map, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        if (isFlammableWood(item2)) {
            if (SharedConstants.isDevelopment) {
                throw net.minecraft.util.Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName((ItemStack)null).getString() + " a furnace fuel. That will not work!"));
            }
        } else {
            map.put(item2, fuelTime);
        }
    }*/

    private static boolean isFlammableWood(Item item) {
        return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
    }

    public static void registerCompostableItem(float levelIncreaseChance, ItemConvertible item) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), levelIncreaseChance);
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public static LeavesBlock createEnchantedLeavesBlock() {
        return new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES)
                .strength(0.2F)
                .ticksRandomly()
                .sounds(BlockSoundGroup.GRASS)
                .nonOpaque()
                .allowsSpawning(Util::canSpawnOnLeaves)
                .suffocates(Util::never)
                .blockVision(Util::never)
        );
    }

    @NonMinecraftNorFabric
    public static void registerGrindableItem(int ingredientDataAdded, @Nonnull ItemConvertible item) {
        GrinderBlockEntity.INGREDIENT_DATA_MAP.put(item.asItem(), ingredientDataAdded);
    }

    @NonMinecraftNorFabric
    public static void registerGrindableItems(int ingredientDataAdded, @Nonnull Tag<Item> tag) {
        for (Item item: tag.values())
            registerGrindableItem(ingredientDataAdded, item);
    }

    @NonMinecraftNorFabric
    public static boolean grindable(@Nullable ItemStack itemStack) {
        if (itemStack == null)
            itemStack = ItemStack.EMPTY;
        return grindable(itemStack.getItem());
    }

    @NonMinecraftNorFabric
    public static boolean grindable(@Nullable ItemConvertible item) {
        if (item == null)
            return false;
        return GrinderBlockEntity.INGREDIENT_DATA_MAP.containsKey(item);
    }






}
