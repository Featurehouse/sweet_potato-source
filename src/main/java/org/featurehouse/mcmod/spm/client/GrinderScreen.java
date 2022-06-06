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
import org.featurehouse.mcmod.spm.screen.GrinderScreenHandler;

@Environment(EnvType.CLIENT)
public class GrinderScreen extends AbstractContainerScreen<GrinderScreenHandler> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(SPMMain.MODID, "textures/gui/container/grinder.png");

    public GrinderScreen(GrinderScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderLabels(PoseStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.renderLabels(matrices, mouseX, mouseY);
        double ingredientData = this.menu.getIngredientData();
        this.font.draw(matrices, Component.translatable(
                        "container.grinding.ingredientData",
                        ingredientData),
                8.0f, 59.0f, 0);
    }

    @Override
    public void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrices, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int l = this.menu.getGrindProgress();
        this.blit(matrices, i + 74, j + 35, 176, 0, l+1, 16);  // arrow

    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //RenderSystem.disableBlend();
        this.renderTooltip(matrices, mouseX, mouseY);
    }
}