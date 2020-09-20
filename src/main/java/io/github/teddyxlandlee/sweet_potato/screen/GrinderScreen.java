package io.github.teddyxlandlee.sweet_potato.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.teddyxlandlee.annotation.InDebugUse;
import io.github.teddyxlandlee.debug.Debug;
import io.github.teddyxlandlee.debug.PartType;
import io.github.teddyxlandlee.sweet_potato.ExampleMod;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GrinderScreen extends CottonInventoryScreen<GrinderScreenHandler> {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier(ExampleMod.MODID, "textures/gui/container/grinder.png");

    @InDebugUse
    private final boolean[] doDebug = new boolean[] {true, true};

    public GrinderScreen(GrinderScreenHandler description, PlayerEntity player) {
        super(description, player, new TranslatableText("container.sweet_potato.grinding"));
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
        this.textRenderer.draw(matrices, new TranslatableText(
                "container.grinding.ingredientData",
                 this.handler.getIngredientData()),
                8.0f, 59.0f, 0);
        if (doDebug[0]) Debug.debug(this.getClass(), PartType.METHOD, "drawForeground",
                "Successfully finish foreground"); this.doDebug[0] = false;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float partialTicks, int mouseX, int mouseY) {
        assert this.client != null;
        this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.drawTexture(matrices, this.x + 79, this.y + 34, 176, 0,
                this.getScreenHandler().getGrindProgress() + 1, 16);
        if (doDebug[1]) Debug.debug(this.getClass(), PartType.METHOD, "drawBackground",
                "Successfully finished background"); this.doDebug[1] = false;
    }

    @Deprecated
    public int getBlockEntityVar(int i, BlockEntity blockEntity) {
        if (blockEntity instanceof GrinderBlockEntity) {
            final int result = ((GrinderBlockEntity) blockEntity).propertyDelegate.get(0) >> 1;
            Debug.debug(this.getClass(), PartType.METHOD, "getBlockEntityVar",
                    "i = " + i + ", result = " + result);
            return result;
        }

        return 0;
    }
}
