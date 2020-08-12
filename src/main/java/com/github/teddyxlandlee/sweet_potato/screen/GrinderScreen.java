package com.github.teddyxlandlee.sweet_potato.screen;

import bilibili.ywsuoyi.gui.screen;
import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GrinderScreen extends screen<GrinderScreenHandler> {
    public GrinderScreen(GrinderScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, new Identifier(ExampleMod.MODID, "textures/gui/container/grinder.png"));

        //this.addProgressArrow(74, 35, 0);
        this.addProgressBar(74, 35, 22, 0);

    }

    @Override
    public int getBlockEntityVar(int i, BlockEntity blockEntity) {
        // ?????????????????????????????????????????????
        if (blockEntity instanceof GrinderBlockEntity) {
            return ((GrinderBlockEntity) blockEntity).propertyDelegate.get(i);
        } return 0;
    }
}
