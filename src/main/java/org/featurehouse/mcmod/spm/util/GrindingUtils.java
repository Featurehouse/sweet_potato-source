package org.featurehouse.mcmod.spm.util;

import org.featurehouse.annotation.NonMinecraftNorFabric;
import org.featurehouse.mcmod.spm.blocks.entities.GrinderBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GrindingUtils {
    private GrindingUtils() {}

    @NonMinecraftNorFabric
    public static void registerGrindableItem(double ingredientDataAdded, @NotNull ItemConvertible item) {
        GrinderBlockEntity.INGREDIENT_DATA_MAP.put(item.asItem(), ingredientDataAdded);
    }

    @NonMinecraftNorFabric
    public static void registerGrindableItems(int ingredientDataAdded, @NotNull Tag<Item> tag) {
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