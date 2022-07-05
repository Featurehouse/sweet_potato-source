package org.featurehouse.mcmod.spm.mixin.acc;

import net.minecraft.advancements.Advancement.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Builder.class)
public interface AdvancementTaskAccessor {
    @Accessor
    void setRequirements(String[][] req);

    @Accessor
    String[][] getRequirements();
}
