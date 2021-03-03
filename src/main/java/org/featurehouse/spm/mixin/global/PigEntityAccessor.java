package org.featurehouse.spm.mixin.global;

import net.minecraft.entity.passive.PigEntity;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PigEntity.class)
public interface PigEntityAccessor {
    @Accessor("BREEDING_INGREDIENT")
    static void setBreedingIngredient(Ingredient ingredient) {
        throw new AssertionError("Mixin");
    }
}
