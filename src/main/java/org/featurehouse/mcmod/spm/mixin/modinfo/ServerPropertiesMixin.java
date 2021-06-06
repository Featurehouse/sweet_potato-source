package org.featurehouse.mcmod.spm.mixin.modinfo;

import org.featurehouse.mcmod.spm.world.levelmeta.SPMLevelProperties;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.SaveProperties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SaveProperties.class)
public interface ServerPropertiesMixin extends SPMLevelProperties {
    @Override
    default NbtCompound sweetPotato_getSPMMetaRaw() {
        return new NbtCompound();
    }

    @Override
    default void sweetPotato_setSPMMetaRaw(NbtCompound tag) {
    }
}
