package org.featurehouse.mcmod.spm.mixin.ingredient;

import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.featurehouse.mcmod.spm.mixin.acc.IngredientAccessor;
import org.featurehouse.mcmod.spm.util.registries.AnimalIngredients;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Pig.class)
abstract class PigEntityMixin {
    @Shadow @Final @Mutable
    private static Ingredient BREEDING_INGREDIENT;

    static {
        @SuppressWarnings("all")
        IngredientAccessor acc = (IngredientAccessor) (Object) BREEDING_INGREDIENT;
        acc.setMatchingStacks(null);    // clear cache
        //acc.cacheMatchingStacks();
        ItemStack[] matchingStacks = BREEDING_INGREDIENT.getItems();
        BREEDING_INGREDIENT = Ingredient.of(AnimalIngredients.configurePig(matchingStacks));
    }
}
