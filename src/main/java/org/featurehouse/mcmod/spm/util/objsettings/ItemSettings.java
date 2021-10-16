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

    @Deprecated public static final Settings GROUP_FOOD = groupFood();
    @Deprecated public static final Settings ONE_FOOD = oneFood(); // unstackable food item
    @Deprecated public static final Settings DECORATIONS = decorations();
    @Deprecated public static final Settings UNCDEC = uncommonDecorations();   // Uncommon Decorations
    @Deprecated public static final Settings MISC = misc();
    /** Does not appear in creative mode inventory, but still exists. */
    @Deprecated public static final Settings EASTER_EGG = easterEgg();

    private ItemSettings() {}
}