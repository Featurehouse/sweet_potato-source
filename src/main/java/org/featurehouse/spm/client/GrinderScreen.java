package org.featurehouse.spm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import org.featurehouse.spm.SPMMain;
import org.featurehouse.spm.screen.GrinderScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GrinderScreen extends HandledScreen<GrinderScreenHandler> {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier(SPMMain.MODID, "textures/gui/container/grinder.png");

    public GrinderScreen(GrinderScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    /*@Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        assert this.client != null;
        this.client.getTextureManager().bindTexture(TEXTURE);
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int l = this.handler.getGrindProgress();
        this.drawTexture(matrices, this.x + 73 //attention//, this.y + 34, 176, 14, l+1, 16);

        this.drawTexture(matrices, 74, 35, 22, 67, 22, 16);
        this.drawTexture(matrices, 74, 35, 0, 67, this.handler.getGrindProgress(), 16);
    }*/

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.drawForeground(matrices, mouseX, mouseY);
        double ingredientData = this.handler.getIngredientData();
        this.textRenderer.draw(matrices, new TranslatableText(
                        "container.grinding.ingredientData",
                        ingredientData),
                8.0f, 59.0f, 0);
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        assert this.client != null;
        this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        //this.addProgressArrow(74, 35, 0);
        int l = this.handler.getGrindProgress();
        this.drawTexture(matrices, this.x + 74, this.y + 35, 176, 0, l+1, 16);  // arrow
        //super.drawBackground(matrices, delta, mouseX, mouseY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        RenderSystem.disableBlend();
        //this.renderForeground(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
