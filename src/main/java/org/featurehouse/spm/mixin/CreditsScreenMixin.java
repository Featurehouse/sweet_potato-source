package org.featurehouse.spm.mixin;

import org.featurehouse.spm.SPMMain;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.resource.Resource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
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

@Environment(EnvType.CLIENT)
@Mixin(CreditsScreen.class)
public class CreditsScreenMixin extends Screen {
    @Shadow private IntSet field_24261; // centeredLines
    @Shadow private List<StringRenderable> credits;
    @Shadow @Final private static Logger LOGGER;
    @Shadow private int creditsHeight;

    @Deprecated
    protected CreditsScreenMixin(Text title) {
        super(title);
    }

    @Inject(
            /*at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "java/io/InputStream.close()V",
                    ordinal = 1
            ),*/
            at = @At("TAIL"),
            method = "init()V"
    )
    private void onInit(CallbackInfo ci) {
        printSPMCredits();
    }

    private void printSPMCredits() {
        assert this.client != null;
        Resource resource1 = null;
        try {
            resource1 = this.client.getResourceManager().getResource(new Identifier(SPMMain.MODID, "texts/credits.txt"));
            InputStream inputStream1 = resource1.getInputStream();
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
            String string10086;
            while ((string10086 = bufferedReader1.readLine()) != null) {
                string10086 = string10086.replaceAll("PLAYERNAME", this.client.getSession().getUsername()).replaceAll("\t", "\u0020\u0020\u0020\u0020");
                boolean centre = false;
                if (string10086.startsWith("[C]")) {
                    string10086 = string10086.substring(3);
                    centre = true;
                }

                List<StringRenderable> oneLineSplit = this.client.textRenderer.wrapLines(new LiteralText(string10086), 274);

                for (StringRenderable StringRenderable1 : oneLineSplit) {
                    if (centre) this.field_24261.add(this.credits.size());
                    this.credits.add(StringRenderable1);
                }
                this.credits.add(StringRenderable.EMPTY);
            } inputStream1.close();

            this.creditsHeight = this.credits.size() * 12;
        } catch (IOException e) {
            LOGGER.error("Couldn't load credits from Sweet Potato Mod", e);
        } finally {
            IOUtils.closeQuietly(resource1);
        }
    }
}