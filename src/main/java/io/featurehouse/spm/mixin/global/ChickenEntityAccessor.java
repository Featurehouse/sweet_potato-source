package io.featurehouse.spm.mixin.global;

import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChickenEntity.class)
public interface ChickenEntityAccessor {
    @Accessor("BREEDING_INGREDIENT")
    static void setBreedingIngredient(Ingredient ingredient) {
        throw new AssertionError("Mixin");
    }
}
