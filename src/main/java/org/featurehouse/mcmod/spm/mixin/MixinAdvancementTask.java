package org.featurehouse.mcmod.spm.mixin;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import org.featurehouse.mcmod.spm.advancement.BalancedDietHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Builder.class)
public abstract class MixinAdvancementTask {
    @Inject(method = "fromJson", at = @At("RETURN"))
    private static void onModifyFromJson(JsonObject obj, DeserializationContext predicateDeserializer, CallbackInfoReturnable<Builder> cir) {
        ResourceLocation id = predicateDeserializer.getAdvancementId();
        if ("balanced_diet".equals(id.getPath()) && "minecraft".equals(id.getNamespace())) {
            BalancedDietHelper.setupCriteria(cir.getReturnValue());
        }
    }
}
