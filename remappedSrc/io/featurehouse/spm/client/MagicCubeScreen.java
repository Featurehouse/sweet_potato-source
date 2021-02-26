package io.featurehouse.spm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.featurehouse.annotation.OperationBeforeDeveloping;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.screen.MagicCubeScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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
    @OperationBeforeDeveloping
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        assert this.client != null;
        this.client.getTextureManager().bindTexture(BG);
        this.drawTexture(matrices, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);
        short m = this.handler.getMainFuelTime(), v = this.handler.getViceFuelTime();
        int md = mainFuelDisplayHeight(m), vd = viceFuelDisplayHeight(v);
        if (m >= 0) this.drawTexture(matrices, this.x + 55,  this.y + 59, 176, 29, 18, 4);
        if (v >  0) this.drawTexture(matrices, this.x + 101, this.y + 59, 176, 29, 17, 4);
        this.drawTexture(matrices, this.x + 57 , this.y + 58 - md, 199, 13 - md, 13, md);
        this.drawTexture(matrices, this.x + 105, this.y + 57 - vd, 176, 28 - vd, 10, vd);
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
        RenderSystem.disableBlend();
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
