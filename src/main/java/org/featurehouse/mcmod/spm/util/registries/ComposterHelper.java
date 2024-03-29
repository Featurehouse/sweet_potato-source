package org.featurehouse.mcmod.spm.util.registries;

import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoComponent;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.ApiStatus;

public final class ComposterHelper {
    private ComposterHelper() {}

    @ApiStatus.Internal
    public static void register() {
        registerCompostableItem(0.3f, SPMMain.PEEL);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_OAK_SAPLING_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_SPRUCE_SAPLING_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_BIRCH_SAPLING_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_JUNGLE_SAPLING_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_ACACIA_SAPLING_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_DARK_OAK_SAPLING_ITEM);

        registerCompostableItem(0.3f, SPMMain.ENCHANTED_ACACIA_LEAVES_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_BIRCH_LEAVES_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_DARK_OAK_LEAVES_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_JUNGLE_LEAVES_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_OAK_LEAVES_ITEM);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_SPRUCE_LEAVES_ITEM);

        registerCompostableItem(0.3f, SPMMain.ENCHANTED_WHEAT_SEEDS);
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_BEETROOT_SEEDS);
        registerCompostableItem(0.65f, SPMMain.ENCHANTED_CARROT_ITEM);
        registerCompostableItem(0.65f, SPMMain.ENCHANTED_VANILLA_POTATO_ITEM);

        registerCompostableItem(0.5f, SPMMain.ENCHANTED_SUGAR_CANE_ITEM);


        for (SweetPotatoType type: SweetPotatoType.values()) {
            for (SweetPotatoStatus status: SweetPotatoStatus.values()) {
                SweetPotatoComponent component = type.getComponent(status);
                if (component != null) {
                    component.registerCompostableItem(type, status);
                    component.registerGrindableItem(type, status);
                }
            }
        }
    }

    public static void registerCompostableItem(float levelIncreaseChance, ItemLike item) {
        CompostingChanceRegistry.INSTANCE.add(item, levelIncreaseChance);
    }
}
