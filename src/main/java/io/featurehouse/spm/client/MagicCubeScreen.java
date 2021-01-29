package io.featurehouse.spm.client;

import io.featurehouse.annotation.OperationBeforeDeveloping;
import io.featurehouse.spm.screen.MagicCubeScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class MagicCubeScreen extends HandledScreen<MagicCubeScreenHandler> {
    public MagicCubeScreen(MagicCubeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    @OperationBeforeDeveloping
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        //TODO
    }
}
