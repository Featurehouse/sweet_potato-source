package org.featurehouse.mcmod.spm.mixin.modinfo;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.OrderedText;
import org.featurehouse.mcmod.spm.util.credits.CreditsPrinter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CreditsScreen.class)
@Environment(EnvType.CLIENT)
abstract class CreditsScreenMixinC extends Screen {
    @Shadow private int creditsHeight;

    @Shadow private IntSet centeredLines;

    @Shadow private List<OrderedText> credits;

    @Inject(at = @At("TAIL"), method = "init()V")
    private void printSPMCredits(CallbackInfo ci) {
        CreditsPrinter.print(client, h -> this.creditsHeight = h,
                centeredLines, credits);
    }

    @Deprecated
    private CreditsScreenMixinC() { super(null); }
}
