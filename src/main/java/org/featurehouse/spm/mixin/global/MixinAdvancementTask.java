package org.featurehouse.spm.mixin.global;

import com.google.gson.JsonObject;
import org.featurehouse.spm.advancement.BalancedDietHelper;
import net.minecraft.advancement.Advancement.Task;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Task.class)
public abstract class MixinAdvancementTask {
    @Inject(method = "fromJson", at = @At("RETURN"))
    private static void onModifyFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer, CallbackInfoReturnable<Task> cir) {
        Identifier id = predicateDeserializer.getAdvancementId();
        if (id.getPath().equals("balanced_diet") && id.getNamespace().equals("minecraft")) {
            BalancedDietHelper.setupCriteria(cir.getReturnValue());
        }
    }
}
