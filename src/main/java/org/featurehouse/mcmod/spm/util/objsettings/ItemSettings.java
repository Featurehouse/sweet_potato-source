package org.featurehouse.mcmod.spm.util.objsettings;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;

public final class ItemSettings {
    public static Settings groupFood() {
        return new FabricItemSettings().group(ItemGroup.FOOD);
    }

    public static Settings oneFood() {
        return new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1);
    }

    public static Settings decorations() {
        return new FabricItemSettings().group(ItemGroup.DECORATIONS);
    }

    public static Settings uncommonDecorations() {
        return new FabricItemSettings().group(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON);
    }

    public static Settings misc() {
        return new FabricItemSettings().group(ItemGroup.MISC);
    }

    public static Settings easterEgg() {
        return new FabricItemSettings();
    }

    @Deprecated(forRemoval = true) public static final Settings GROUP_FOOD;
    @Deprecated(forRemoval = true) public static final Settings ONE_FOOD; // unstackable food item
    @Deprecated(forRemoval = true) public static final Settings DECORATIONS;
    @Deprecated(forRemoval = true) public static final Settings UNCDEC;   // Uncommon Decorations
    @Deprecated(forRemoval = true) public static final Settings MISC;
    /** Does not appear in creative mode inventory, but still exists. */
    @Deprecated(forRemoval = true) public static final Settings EASTER_EGG;

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