package com.github.teddyxlandlee.sweet_potato.screen;

import com.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import com.github.teddyxlandlee.annotation.Unused_InsteadOf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public abstract class YwsuoyiScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
    // The former class is written by ywsuoyi.
    // Here, I rewrite it to a recipe screen handler.

    public Inventory inventory;
    public Inventory playerInv;
    public BlockEntity e;
    public DefaultedList<Integer> autoRender;

    @NonMinecraftNorFabric
    private final int[] integerData = new int[4];

    public YwsuoyiScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PacketByteBuf packetByteBuf) {
        super(type, syncId);
        this.autoRender = DefaultedList.of();
        this.inventory = inventory;
        this.playerInv = playerInventory;
        if (packetByteBuf != null) {
            this.e = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());
        }

        inventory.onOpen(playerInventory.player);
        this.init();
    }

    @Deprecated
    public YwsuoyiScreenHandler(ScreenHandlerType<?> oven, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(oven, syncId, playerInventory, inventory, null);
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        return super.onButtonClick(player, id);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public void addPlayerInv(int x, int y) {
        int n;
        for(n = 0; n < 3; ++n) {
            for(int m = 0; m < 9; ++m) {
                this.addSlot(new Slot(this.playerInv, m + n * 9 + 9, x + m * 18, n * 18 + y));
            }
        }

        for(n = 0; n < 9; ++n) {
            this.addSlot(new Slot(this.playerInv, n, x + n * 18, 58 + y));
        }

    }

    @Unused_InsteadOf @Deprecated
    public void addrectInv(int x, int y, int row, int list) {
        for(int n = 0; n < row; ++n) {
            for(int m = 0; m < list; ++m) {
                this.addSlot(new Slot(this.inventory, m + n * list, x + m * 18, y + n * 18));
            }
        }

    }

    @Unused_InsteadOf @Deprecated
    public Slot addSlot(Slot slot, int type) {
        this.autoRender.add(type);
        return super.addSlot(slot);
    }

    public Slot addSlot(Slot slot) {
        this.autoRender.add(1);
        return super.addSlot(slot);
    }

    public abstract void init();

    // -*- Below this line, everything is written by teddyxlandlee instead. -*- //
    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        if (this.inventory instanceof RecipeInputProvider)
            ((RecipeInputProvider) this.inventory).provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.inventory.clear();
    }

    @Override
    public abstract boolean matches(Recipe<? super Inventory> recipe);

    @NonMinecraftNorFabric
    public void setIntegerData(int craftingResultSlotIndex, int craftingWidth, int craftingHeight, int craftingSlotCount) {
        integerData[0] = craftingResultSlotIndex;
        integerData[1] = craftingWidth;
        integerData[2] = craftingHeight;
        integerData[3] = craftingSlotCount;
    }

    public int getCraftingResultSlotIndex() {
        return integerData[0];
    }

    public int getCraftingWidth() {
        return integerData[1];
    }

    public int getCraftingHeight() {
        return integerData[2];
    }

    @Environment(EnvType.CLIENT)
    public int getCraftingSlotCount() {
        return integerData[3];
    }
}
