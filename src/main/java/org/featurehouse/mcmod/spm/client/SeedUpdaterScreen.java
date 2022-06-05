package org.featurehouse.mcmod.spm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.featurehouse.mcmod.spm.screen.SeedUpdaterScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class SeedUpdaterScreen extends ItemCombinerScreen<SeedUpdaterScreenHandler> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("sweet_potato:textures/gui/container/seed_updating.png");

    public SeedUpdaterScreen(SeedUpdaterScreenHandler handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title, TEXTURE);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void renderLabels(PoseStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.renderLabels(matrices, mouseX, mouseY);
    }
}
