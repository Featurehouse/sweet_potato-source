package org.featurehouse.mcmod.spm.mixin.modinfo;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.WorldData;
import org.featurehouse.mcmod.spm.world.levelmeta.SPMLevelProperties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldData.class)
public interface ServerPropertiesMixin extends SPMLevelProperties {
    @Override
    default CompoundTag sweetPotato_getSPMMetaRaw() {
        return new CompoundTag();
    }

    @Override
    default void sweetPotato_setSPMMetaRaw(CompoundTag tag) {
    }
}
