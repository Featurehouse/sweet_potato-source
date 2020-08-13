package com.github.teddyxlandlee.sweet_potato.screen;

import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.github.teddyxlandlee.sweet_potato.util.GrindingResultSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

@Deprecated
public class DeprecatedGrinderScreenHandler extends ModdedYwsuoyiScreenHandler {
    private final PropertyDelegate propertyDelegate;
    protected final World world;

    public DeprecatedGrinderScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PacketByteBuf packetByteBuf) {
        super(type, syncId, playerInventory, inventory, packetByteBuf);
        this.world = playerInventory.player.world;
        this.propertyDelegate = new ArrayPropertyDelegate(3);
        checkSize(inventory, 2);
        //checkDataCount(propertyDelegate, 3);

        this.setIntegerData(1, 1, 1, 2);
        this.addPlayerInv(8, 84);
        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new GrindingResultSlot(((PlayerInventory) playerInv).player, inventory, 1, 116, 35));

        this.addProperties(propertyDelegate);

        if (packetByteBuf != null) {
            this.e = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());
        }
    }

    public DeprecatedGrinderScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(type, syncId, playerInventory, inventory, null);
    }

    @Deprecated
    public DeprecatedGrinderScreenHandler(int i, PlayerInventory playerInventory) {
        this(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, i, playerInventory, new SimpleInventory(2));
    }

    @Override
    public void init() {}

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Environment(EnvType.CLIENT)
    public int getGrindProgress() {
        int grindTime = this.propertyDelegate.get(0);
        int grindTimeTotal = this.propertyDelegate.get(1);
        return grindTimeTotal != 0 && grindTime != 0 ? grindTime * 24 / grindTimeTotal : 0;
    }
}
