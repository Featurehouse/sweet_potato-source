package org.featurehouse.spm.mixin.global;

import org.featurehouse.spm.world.levelmeta.SPMLevelProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SaveProperties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SaveProperties.class)
public interface ServerPropertiesMixin extends SPMLevelProperties {
    @Override
    default CompoundTag sweetPotato_getSPMMetaRaw() {
        return new CompoundTag();
    }

    @Override
    default void sweetPotato_setSPMMetaRaw(CompoundTag tag) {
    }
}
