package org.featurehouse.mcmod.spm.mixin.acc;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Ingredient.class)
public interface IngredientAccessor {
    @Invoker("cacheMatchingStacks")
    void cacheMatchingStacks();

    @Accessor("matchingStacks")
    ItemStack[] getMatchingStacks();

    @Accessor("matchingStacks") @Mutable
    void setMatchingStacks(ItemStack[] matchingStacks);
}