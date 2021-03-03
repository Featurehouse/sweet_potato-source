package org.featurehouse.spm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import org.featurehouse.spm.screen.SeedUpdaterScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SeedUpdaterScreen extends ForgingScreen<SeedUpdaterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("sweet_potato:textures/gui/container/seed_updating.png");

    public SeedUpdaterScreen(SeedUpdaterScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, TEXTURE);
        this.titleX = 60;
        this.titleY = 18;
    }

    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.drawForeground(matrices, mouseX, mouseY);
    }
}
