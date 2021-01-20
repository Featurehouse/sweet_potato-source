package io.featurehouse.spm.util.properties.objects;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;

public final class ItemSettings {
    public static final Settings GROUP_FOOD;
    public static final Settings ONE_FOOD; // unstackable food item
    public static final Settings DECORATIONS;
    public static final Settings UNCDEC;   // Uncommon Decorations
    public static final Settings MISC;
    /** Does not appear in creative mode inventory, but still exists. */
    public static final Settings EASTER_EGG;

    private ItemSettings() {}

    static {
        GROUP_FOOD = new FabricItemSettings().group(ItemGroup.FOOD);
        ONE_FOOD = new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1);
        DECORATIONS = new FabricItemSettings().group(ItemGroup.DECORATIONS);
        UNCDEC = new FabricItemSettings().group(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON);
        MISC = new FabricItemSettings().group(ItemGroup.MISC);
        EASTER_EGG = new FabricItemSettings();
    }
}