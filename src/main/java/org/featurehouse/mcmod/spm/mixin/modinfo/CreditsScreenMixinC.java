package org.featurehouse.mcmod.spm.mixin.modinfo;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.util.FormattedCharSequence;
import org.featurehouse.mcmod.spm.util.credits.CreditsPrinter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WinScreen.class)
@Environment(EnvType.CLIENT)
abstract class CreditsScreenMixinC extends Screen {
    @Shadow private int totalScrollLength;

    @Shadow private IntSet centeredLines;

    @Shadow private List<FormattedCharSequence> lines;

    @Inject(at = @At("TAIL"), method = "init()V")
    private void printSPMCredits(CallbackInfo ci) {
        CreditsPrinter.print(minecraft, h -> this.totalScrollLength = h,
                centeredLines, lines); 
    }

    @Deprecated
    private CreditsScreenMixinC() { super(null); }
}
