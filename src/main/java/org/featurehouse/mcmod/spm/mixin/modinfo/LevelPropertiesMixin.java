package org.featurehouse.mcmod.spm.mixin.modinfo;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelVersion;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.featurehouse.mcmod.spm.world.levelmeta.SPMLevelProperties;
import org.featurehouse.mcmod.spm.world.levelmeta.SPMLevelPropertiesHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class LevelPropertiesMixin implements SPMLevelProperties {
    @Unique
    private CompoundTag sweetPotato_spmMeta = new CompoundTag();

    @Override
    public CompoundTag sweetPotato_getSPMMetaRaw() {
        return this.sweetPotato_spmMeta;
    }

    @Override
    public void sweetPotato_setSPMMetaRaw(CompoundTag tag) {
        this.sweetPotato_spmMeta = tag;
    }

    @Inject(at = @At("RETURN"), method = "parse")
    private static void onReadProperties(Dynamic<Tag> dynamic, DataFixer dataFixer, int dataVersion, CompoundTag playerData, LevelSettings levelInfo, LevelVersion saveVersionInfo, WorldGenSettings generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<PrimaryLevelData> cir) {
        PrimaryLevelData levelProperties = cir.getReturnValue();
        SPMLevelPropertiesHelper.setCurrentSPMDataVersion((SPMLevelProperties) levelProperties);
    }

    @Inject(at = @At("RETURN"), method = "setTagData")
    private void onWriteNbt(RegistryAccess dynamicRegistryManager, CompoundTag root, CompoundTag playerData, CallbackInfo ci) {
        root.put("sweet_potato:custom_data", this.sweetPotato_spmMeta);
    }
}
