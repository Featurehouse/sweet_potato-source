package io.github.teddyxlandlee.sweet_potato.util.existedmaps;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoStatus;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoType;
import io.github.teddyxlandlee.sweet_potato.util.Util;

public final class ComposterHelper {
    private ComposterHelper() {}

    public static void register() {
        Util.registerCompostableItem(0.3f, SPMMain.PEEL);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_OAK_SAPLING_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_SPRUCE_SAPLING_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_BIRCH_SAPLING_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_JUNGLE_SAPLING_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_ACACIA_SAPLING_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_DARK_OAK_SAPLING_ITEM);

        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_ACACIA_LEAVES_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_BIRCH_LEAVES_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_DARK_OAK_LEAVES_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_JUNGLE_LEAVES_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_OAK_LEAVES_ITEM);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_SPRUCE_LEAVES_ITEM);

        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_WHEAT_SEEDS);
        Util.registerCompostableItem(0.3f, SPMMain.ENCHANTED_BEETROOT_SEEDS);
        Util.registerCompostableItem(0.65f, SPMMain.ENCHANTED_CARROT_ITEM);
        Util.registerCompostableItem(0.65f, SPMMain.ENCHANTED_VANILLA_POTATO_ITEM);

        Util.registerCompostableItem(0.5f, SPMMain.ENCHANTED_SUGAR_CANE_ITEM);


        for (SweetPotatoType type: SweetPotatoType.values()) {
            for (SweetPotatoStatus status: SweetPotatoStatus.values()) {
                if (type.getComponent(status) != null) {
                    type.getComponent(status).registerCompostableItem(type, status);
                    type.getComponent(status).registerGrindableItem(type, status);
                }
            }
        }
    }
}
