package org.featurehouse.mcmod.spm.mixin.ingredient;

import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.featurehouse.mcmod.spm.mixin.acc.IngredientAccessor;
import org.featurehouse.mcmod.spm.util.registries.AnimalIngredients;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ChickenEntity.class)
public class ChickenEntityMixin {
    @Shadow @Final @Mutable
    private static Ingredient BREEDING_INGREDIENT;

    @SuppressWarnings("all")
    @Inject(at = @At("RETURN"), method = "<clinit>*")
    private static void modifyBreedingIngredient() {
        IngredientAccessor acc = (IngredientAccessor) (Object) BREEDING_INGREDIENT;
        acc.setMatchingStacks(null);    // clear cache
        acc.cacheMatchingStacks();
        ItemStack[] matchingStacks = acc.getMatchingStacks();
        BREEDING_INGREDIENT = Ingredient.ofStacks(AnimalIngredients.configureChicken(matchingStacks));
    }
}
