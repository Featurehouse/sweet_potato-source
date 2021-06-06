package org.featurehouse.mcmod.spm.mixin.acc;

import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChickenEntity.class)
public interface ChickenEntityAccessor {
    @Accessor("BREEDING_INGREDIENT")
    @Mutable
    static void setBreedingIngredient(Ingredient ingredient) {
        throw new AssertionError("Mixin");
    }
}
