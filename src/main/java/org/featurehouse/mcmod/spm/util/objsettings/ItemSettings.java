package org.featurehouse.mcmod.spm.util.objsettings;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;

public final class ItemSettings {
    public static Properties groupFood() {
        return new FabricItemSettings().tab(CreativeModeTab.TAB_FOOD);
    }

    public static Properties oneFood() {
        return new FabricItemSettings().tab(CreativeModeTab.TAB_FOOD).stacksTo(1);
    }

    public static Properties decorations() {
        return new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS);
    }

    public static Properties uncommonDecorations() {
        return new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS).rarity(Rarity.UNCOMMON);
    }

    public static Properties misc() {
        return new FabricItemSettings().tab(CreativeModeTab.TAB_MISC);
    }

    public static Properties easterEgg() {
        return new FabricItemSettings();
    }

    @Deprecated(forRemoval = true) public static final Properties GROUP_FOOD;
    @Deprecated(forRemoval = true) public static final Properties ONE_FOOD; // unstackable food item
    @Deprecated(forRemoval = true) public static final Properties DECORATIONS;
    @Deprecated(forRemoval = true) public static final Properties UNCDEC;   // Uncommon Decorations
    @Deprecated(forRemoval = true) public static final Properties MISC;
    /** Does not appear in creative mode inventory, but still exists. */
    @Deprecated(forRemoval = true) public static final Properties EASTER_EGG;

    private ItemSettings() {}

    static {
        GROUP_FOOD = new FabricItemSettings().tab(CreativeModeTab.TAB_FOOD);
        ONE_FOOD = new FabricItemSettings().tab(CreativeModeTab.TAB_FOOD).stacksTo(1);
        DECORATIONS = new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS);
        UNCDEC = new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS).rarity(Rarity.UNCOMMON);
        MISC = new FabricItemSettings().tab(CreativeModeTab.TAB_MISC);
        EASTER_EGG = new FabricItemSettings();
    }
}