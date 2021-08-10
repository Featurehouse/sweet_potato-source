package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.mixin.acc.ParrotEntityAccessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Stream;

public class AnimalIngredients {
    private AnimalIngredients() {}

    public static Stream<ItemStack> configurePig(ItemStack[] stacks) {
        ArrayList<ItemStack> stackList = new ArrayList<>(Arrays.asList(stacks));
        stackList.add(SPMMain.ENCHANTED_CARROT_ITEM.getDefaultStack());
        stackList.add(SPMMain.ENCHANTED_VANILLA_POTATO_ITEM.getDefaultStack());
        stackList.add(SPMMain.PEEL.getDefaultStack());

        return stackList.stream();
    }

    public static void configureParrot() {
        Set<Item> parrotTamingIngredients = ParrotEntityAccessor.getTamingIngredients();
        parrotTamingIngredients.add(SPMMain.ENCHANTED_BEETROOT_SEEDS);
        parrotTamingIngredients.add(SPMMain.ENCHANTED_WHEAT_SEEDS);
    }

    public static Stream<ItemStack> configureChicken(ItemStack[] stacks) {
        ArrayList<ItemStack> stackList = new ArrayList<>(Arrays.asList(stacks));
        stackList.add(SPMMain.ENCHANTED_WHEAT_SEEDS.getDefaultStack());
        stackList.add(SPMMain.ENCHANTED_BEETROOT_SEEDS.getDefaultStack());

        return stackList.stream();
    }
}
