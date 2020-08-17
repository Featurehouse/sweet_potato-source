package com.github.teddyxlandlee.sweet_potato.screen;

import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Deprecated
@Environment(EnvType.CLIENT)
public class DeprecatedGrinderScreen$2 extends HandledScreen<GrinderScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ExampleMod.MODID, "textures/gui/container/grinder.png");
    private final GrinderScreenHandler handler;

    public DeprecatedGrinderScreen$2(GrinderScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        assert this.client != null;
        this.client.getTextureManager().bindTexture(TEXTURE);
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int l = this.handler.getGrindProgress();
        this.drawTexture(matrices, this.x + 73/*attention*/, this.y + 34, 176, 14, l+1, 16);

        this.drawTexture(matrices, 74, 35, 22, 67, 22, 16);
        this.drawTexture(matrices, 74, 35, 0, 67, this.handler.getGrindProgress(), 16);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.drawForeground(matrices, mouseX, mouseY);
        this.textRenderer.draw(matrices, new TranslatableText("container.grinding.ingredientData", handler.getIngredientData()),
                8.0f, 59.0f, 0);
    }
}
