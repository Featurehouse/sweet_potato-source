package org.featurehouse.mcmod.spm.mixin.ingredient;

import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.featurehouse.mcmod.spm.mixin.acc.IngredientAccessor;
import org.featurehouse.mcmod.spm.util.registries.AnimalIngredients;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PigEntity.class)
abstract class PigEntityMixin {
    @Shadow @Final @Mutable
    private static Ingredient BREEDING_INGREDIENT;

    //@Inject(at = @At("RETURN"), method = "<clinit>*")
    //private static void modifyBreedingIngredient() {
    static {
        @SuppressWarnings("all")
        IngredientAccessor acc = (IngredientAccessor) (Object) BREEDING_INGREDIENT;
        acc.setMatchingStacks(null);    // clear cache
        acc.cacheMatchingStacks();
        ItemStack[] matchingStacks = acc.getMatchingStacks();
        BREEDING_INGREDIENT = Ingredient.ofStacks(AnimalIngredients.configurePig(matchingStacks));
    }
}
