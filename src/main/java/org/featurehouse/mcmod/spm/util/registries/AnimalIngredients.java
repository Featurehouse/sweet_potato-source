package org.featurehouse.mcmod.spm.util.registries;

import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.mixin.acc.ParrotEntityAccessor;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@ApiStatus.Internal
public final class AnimalIngredients {
    private AnimalIngredients() {}

    public static Stream<ItemStack> configurePig(ItemStack[] stacks) {
        ArrayList<ItemStack> stackList = new ArrayList<>(Arrays.asList(stacks));
        stackList.add(SPMMain.ENCHANTED_CARROT_ITEM.getDefaultInstance());
        stackList.add(SPMMain.ENCHANTED_VANILLA_POTATO_ITEM.getDefaultInstance());
        stackList.add(SPMMain.PEEL.getDefaultInstance());

        return stackList.stream();
    }

    public static void configureParrot() {
        Set<Item> parrotTamingIngredients = ParrotEntityAccessor.getTamingIngredients();
        parrotTamingIngredients.add(SPMMain.ENCHANTED_BEETROOT_SEEDS);
        parrotTamingIngredients.add(SPMMain.ENCHANTED_WHEAT_SEEDS);
    }

    public static Stream<ItemStack> configureChicken(ItemStack[] stacks) {
        ArrayList<ItemStack> stackList = new ArrayList<>(Arrays.asList(stacks));
        stackList.add(SPMMain.ENCHANTED_WHEAT_SEEDS.getDefaultInstance());
        stackList.add(SPMMain.ENCHANTED_BEETROOT_SEEDS.getDefaultInstance());

        return stackList.stream();
    }
}
