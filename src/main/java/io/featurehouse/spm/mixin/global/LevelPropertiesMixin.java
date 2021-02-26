package io.featurehouse.spm.mixin.global;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.featurehouse.spm.world.levelmeta.SPMLevelProperties;
import io.featurehouse.spm.world.levelmeta.SPMLevelPropertiesHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.SaveVersionInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements SPMLevelProperties {
    private CompoundTag sweetPotato_spmMeta = new CompoundTag();

    @Override
    public CompoundTag sweetPotato_getSPMMetaRaw() {
        return this.sweetPotato_spmMeta;
    }

    @Override
    public void sweetPotato_setSPMMetaRaw(CompoundTag tag) {
        this.sweetPotato_spmMeta = tag;
    }

    @Inject(at = @At("RETURN"), method = "readProperties")
    private static void onReadProperties(Dynamic<Tag> dynamic, DataFixer dataFixer, int dataVersion, CompoundTag playerData, LevelInfo levelInfo, SaveVersionInfo saveVersionInfo, GeneratorOptions generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<LevelProperties> cir) {
        LevelProperties levelProperties = cir.getReturnValue();
        SPMLevelPropertiesHelper.setCurrentSPMDataVersion((SPMLevelProperties) levelProperties);
    }

    @Inject(at = @At("RETURN"), method = "updateProperties")
    private void onWriteNbt(DynamicRegistryManager dynamicRegistryManager, CompoundTag root, CompoundTag playerData, CallbackInfo ci) {
        root.put("sweet_potato:custom_data", this.sweetPotato_spmMeta);
    }
}
