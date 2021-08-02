package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.item.Item;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.mixin.acc.ParrotEntityAccessor;

import java.util.Set;

public class AnimalIngredients {
    public static void configureParrot() {
        Set<Item> parrotTamingIngredients = ParrotEntityAccessor.getTamingIngredients();
        parrotTamingIngredients.add(SPMMain.ENCHANTED_BEETROOT_SEEDS);
        parrotTamingIngredients.add(SPMMain.ENCHANTED_WHEAT_SEEDS);
    }
}
