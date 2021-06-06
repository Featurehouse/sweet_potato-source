package org.featurehouse.mcmod.spm.mixin.acc;

import net.minecraft.entity.passive.PigEntity;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PigEntity.class)
public interface PigEntityAccessor {
    @Accessor("BREEDING_INGREDIENT")
    @Mutable
    static void setBreedingIngredient(@SuppressWarnings("unused") Ingredient ingredient) {
        throw new AssertionError("Mixin");
    }
}
