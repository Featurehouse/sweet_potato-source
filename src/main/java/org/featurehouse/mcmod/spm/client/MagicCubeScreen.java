package org.featurehouse.mcmod.spm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.screen.MagicCubeScreenHandler;

@Environment(EnvType.CLIENT)
public class MagicCubeScreen extends HandledScreen<MagicCubeScreenHandler> {
    private static final Identifier BG = new Identifier(SPMMain.MODID, "textures/gui/container/magic_cube.png");

    public MagicCubeScreen(MagicCubeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 181;    // overwrite
        this.playerInventoryTitleY = backgroundHeight - 94;
    }

    @Override
    //was @OperationBeforeDeveloping
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        this.drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        short m = this.handler.getMainFuelTime(), v = this.handler.getViceFuelTime();
        int md = mainFuelDisplayHeight(m), vd = viceFuelDisplayHeight(v);
        if (m >= 0) this.drawTexture(matrices, x + 55,  y + 59, 176, 29, 18, 4);
        if (v >  0) this.drawTexture(matrices, x + 101, y + 59, 176, 29, 17, 4);
        this.drawTexture(matrices, x + 57 , y + 58 - md, 199, 13 - md, 13, md);
        this.drawTexture(matrices, x + 105, y + 57 - vd, 176, 28 - vd, 10, vd);
    }

    private static int mainFuelDisplayHeight(short mfTime) {
        // Height total: 13
        // Range: 0..199
        return mfTime >= 0 ? mfTime * 13 / 200 : 0;
    }

    private static int viceFuelDisplayHeight(short vfTime) {
        return vfTime > 0 ? vfTime * 19 / 400 : 0;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //RenderSystem.disableBlend();
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
