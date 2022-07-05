package org.featurehouse.mcmod.spm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.screen.MagicCubeScreenHandler;

@Environment(EnvType.CLIENT)
public class MagicCubeScreen extends AbstractContainerScreen<MagicCubeScreenHandler> {
    private static final ResourceLocation BG = new ResourceLocation(SPMMain.MODID, "textures/gui/container/magic_cube.png");

    public MagicCubeScreen(MagicCubeScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 181;    // overwrite
        this.inventoryLabelY = imageHeight - 94;
    }

    @Override
    //@OperationBeforeDeveloping
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        this.blit(matrices, x, y, 0, 0, imageWidth, imageHeight);
        short m = this.menu.getMainFuelTime(), v = this.menu.getViceFuelTime();
        int md = mainFuelDisplayHeight(m), vd = viceFuelDisplayHeight(v);
        if (m >= 0) this.blit(matrices, x + 55,  y + 59, 176, 29, 18, 4);
        if (v >  0) this.blit(matrices, x + 101, y + 59, 176, 29, 17, 4);
        this.blit(matrices, x + 57 , y + 58 - md, 199, 13 - md, 13, md);
        this.blit(matrices, x + 105, y + 57 - vd, 176, 28 - vd, 10, vd);
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
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //RenderSystem.disableBlend();
        this.renderTooltip(matrices, mouseX, mouseY);
    }
}
