package com.github.teddyxlandlee.sweet_potato.util;

import com.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Rarity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Util {
    private Util() {}

    public static void registerCompostableItem(float levelIncreaseChance, ItemConvertible item) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), levelIncreaseChance);
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

    public static final class BlockSettings {
        public static final FabricBlockSettings GRASS_LIKE;

        private BlockSettings() {}

        @Deprecated
        public static FabricBlockSettings create(AbstractBlock.Settings settings) {
            return (FabricBlockSettings)settings;
        }

        static {
            GRASS_LIKE = FabricBlockSettings.of(ExampleMod.MATERIAL_PLANT) // Wanted: move MATERIAL_PLANT to Util
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP);
        }
    }

    public static final class ItemSettings {
        public static final Item.Settings UNCDEC;

        private ItemSettings() {}

        static {
            UNCDEC = new Item.Settings()
                    .group(ItemGroup.DECORATIONS)
                    .rarity(Rarity.UNCOMMON);
        }
    }
}
