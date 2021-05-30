package org.featurehouse.spm.mixin.global;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.SaveVersionInfo;
import org.featurehouse.spm.world.levelmeta.SPMLevelProperties;
import org.featurehouse.spm.world.levelmeta.SPMLevelPropertiesHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements SPMLevelProperties {
    @Unique
    private NbtCompound sweetPotato_spmMeta = new NbtCompound();

    @Override
    public NbtCompound sweetPotato_getSPMMetaRaw() {
        return this.sweetPotato_spmMeta;
    }

    @Override
    public void sweetPotato_setSPMMetaRaw(NbtCompound tag) {
        this.sweetPotato_spmMeta = tag;
    }

    @Inject(at = @At("RETURN"), method = "readProperties")
    private static void onReadProperties(Dynamic<NbtElement> dynamic, DataFixer dataFixer, int dataVersion, NbtCompound playerData, LevelInfo levelInfo, SaveVersionInfo saveVersionInfo, GeneratorOptions generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<LevelProperties> cir) {
        LevelProperties levelProperties = cir.getReturnValue();
        SPMLevelPropertiesHelper.setCurrentSPMDataVersion((SPMLevelProperties) levelProperties);
    }

    @Inject(at = @At("RETURN"), method = "updateProperties")
    private void onWriteNbt(DynamicRegistryManager dynamicRegistryManager, NbtCompound root, NbtCompound playerData, CallbackInfo ci) {
        root.put("sweet_potato:custom_data", this.sweetPotato_spmMeta);
    }
}
