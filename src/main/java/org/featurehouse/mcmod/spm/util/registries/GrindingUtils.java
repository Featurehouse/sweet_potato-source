package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import org.featurehouse.mcmod.spm.blocks.entities.GrinderBlockEntity;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@SuppressWarnings("unused")
@StableApi
public final class GrindingUtils {
    private GrindingUtils() {}

    public static void registerGrindableItem(double ingredientDataAdded, @NotNull ItemConvertible item) {
        Item item1 = item.asItem();
        if (item1 == Items.AIR) return;
        GrinderBlockEntity.INGREDIENT_DATA_MAP.put(item1, ingredientDataAdded);
    }

    public static void registerGrindableItems(int ingredientDataAdded, @NotNull Tag<Item> tag) {
        registerGrindableItems(ingredientDataAdded, tag.values());
    }

    public static void registerGrindableItems(int ingredientDataAdded, @NotNull Collection<Item> items) {
        for (Item item: items)
            registerGrindableItem(ingredientDataAdded, item);
    }

    public static void registerGrindableItems(int ingredientDataAdded, @NotNull Item... items) {
        for (Item item: items) {
            registerGrindableItem(ingredientDataAdded, item);
        }
    }

    public static boolean isGrindable(@Nullable ItemStack itemStack) {
        if (itemStack == null)
            return false;
        return isGrindable(itemStack.getItem());
    }

    public static boolean isGrindable(@Nullable ItemConvertible item) {
        return item != null && GrinderBlockEntity.INGREDIENT_DATA_MAP.containsKey(item);
    }
}
