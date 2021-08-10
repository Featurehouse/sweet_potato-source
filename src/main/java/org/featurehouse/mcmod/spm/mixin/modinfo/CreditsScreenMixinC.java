package org.featurehouse.mcmod.spm.mixin.modinfo;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.resource.Resource;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.Logger;
import org.featurehouse.mcmod.spm.util.credits.ModCredits;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@Mixin(CreditsScreen.class)
@Environment(EnvType.CLIENT)
final class CreditsScreenMixinC extends Screen {
    @Shadow @Final private static Logger LOGGER;

    @Shadow private IntSet centeredLines;

    @Shadow private List<OrderedText> credits;

    @Inject(at = @At("TAIL"), method = "init()V")
    private void printSPMCredits(CallbackInfo ci) {
        Resource resource = null;
        try {
            resource = Objects.requireNonNull(this.client)
                    .getResourceManager()
                    .getResource(SPM_FILE);
            InputStream is = resource.getInputStream();

            ModCredits modCredits = ModCredits.fromJson(
                    JsonHelper.deserialize(new BufferedReader(new InputStreamReader(is))),
                    SPM_FILE);

            wrapCentralLines("""
                    \u00a7f============
                    \u00a7eSweet Potato Mod Credits
                    \u00a7f============""");
            wrapLines(false, "\u00a77Author Group");
            renderNameList(modCredits.getAuthorGroup());
            wrapLines(false, "\u00a77Contributors");
            renderContributorList(modCredits.getContributors());
            wrapCentralLines("""
                    \u00a7f============
                    \u00a7eSpecial Thanks from Sweet Potato Mod
                    \u00a7f============""");
            wrapLines(false, "\u00a77Collaborators");
            renderNameList(modCredits.getCollaborators());
            wrapLines(false, "\u00a77Very Important Supporters");
            renderNameList(modCredits.getImportantSupporters());

            is.close();
        } catch (IOException e) {
            LOGGER.error("Couldn't load credits from Sweet Potato Mod", e);
        } finally {
            IOUtils.closeQuietly(resource);
        }
    }

    @Deprecated
    private CreditsScreenMixinC(Text title) { super(title); }

    private static final Identifier SPM_FILE = RegistryHelper.id("credits.json");

    private void wrapLines(boolean central, String string) {
        List<OrderedText> wrapLines = Objects.requireNonNull(client).textRenderer.wrapLines(Text.of(string), 274);
        for (OrderedText text : wrapLines) {
            if (central) centeredLines.add(credits.size());
            credits.add(text);
        } credits.add(OrderedText.EMPTY);
    }

    private void wrapCentralLines(String string) {
        for (String oneLine : string.split("\n"))
            wrapLines(true, oneLine);
    }

    private void renderContributorList(List<ImmutablePair<String, String>> contributors) {
        for (ImmutablePair<String, String> nameAndId : contributors) {
            String rawText = "\u00a7f\t\t" + nameAndId.getLeft();
            if (!nameAndId.getLeft().equals(nameAndId.getRight()))
                rawText = rawText + "\u00a77 (" + nameAndId.getRight() + ')';
            wrapLines(false, rawText);
        }
    }

    private void renderNameList(List<String> names) {
        for (String name : names) {
            wrapLines(false, "\u00a7f\t\t" + name);
        }
    }
}
