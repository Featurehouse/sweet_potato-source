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

    private ItemSettings() {}
}