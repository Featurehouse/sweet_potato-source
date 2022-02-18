package org.featurehouse.mcmod.spm.mixin;

import com.google.gson.JsonObject;
import org.featurehouse.mcmod.spm.advancement.BalancedDietHelper;
import net.minecraft.advancement.Advancement.Builder;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Builder.class)
public abstract class MixinAdvancementTask {
    @Inject(method = "fromJson", at = @At("RETURN"))
    private static void onModifyFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer, CallbackInfoReturnable<Builder> cir) {
        Identifier id = predicateDeserializer.getAdvancementId();
        if ("balanced_diet".equals(id.getPath()) && "minecraft".equals(id.getNamespace())) {
            BalancedDietHelper.setupCriteria(cir.getReturnValue());
        }
    }
}
