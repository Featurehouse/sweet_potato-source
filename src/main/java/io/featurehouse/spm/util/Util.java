package io.featurehouse.spm.util;

import io.featurehouse.annotation.NonMinecraftNorFabric;
import io.featurehouse.spm.blocks.entities.GrinderBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Deprecated
    private static boolean isFlammableWood(Item item) {
        return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
    }

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
