package org.featurehouse.spm.mixin.global;

import net.minecraft.advancement.Advancement.Task;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Task.class)
public interface AdvancementTaskAccessor {
    @Accessor
    void setRequirements(String[][] req);

    @Accessor
    String[][] getRequirements();
}
