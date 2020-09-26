package io.github.teddyxlandlee.sweet_potato.screen;

import io.github.teddyxlandlee.annotation.DeprecatedFrom;
import io.github.teddyxlandlee.annotation.InDebugUse;
import io.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import io.github.teddyxlandlee.debug.Debug;
import io.github.teddyxlandlee.sweet_potato.ExampleMod;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import io.github.teddyxlandlee.sweet_potato.util.DeprecatedGrindingResultSlot;
import io.github.teddyxlandlee.sweet_potato.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GrinderScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private PropertyDelegate propertyDelegate;
    protected final World world;
    @Deprecated
    protected GrinderBlockEntity blockEntity;

    /**
     * Through debugging, we found that block-entity-created and
     * */
    public GrinderScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new SimpleInventory(2), /*new ArrayPropertyDelegate(3)*/ null);
    }

    public GrinderScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(type, syncId);
        checkSize(inventory, 2);
        if (propertyDelegate != null) {
            checkDataCount(propertyDelegate, 3);
        }
        this.inventory = inventory;
        if (propertyDelegate != null) {
            this.propertyDelegate = propertyDelegate;
            this.addProperties(propertyDelegate);
        }
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new DeprecatedGrindingResultSlot(playerInventory.player, inventory, 1, 116, 35));

        this.createPlayerInventory(playerInventory);

        Debug.debug(this, "Successfully created Grinder Screen Handler by Block Entity");
    }

    //@DeprecatedFrom(DeprecatedGrinderScreenHandler$4.class)
    public GrinderScreenHandler(int i, PlayerInventory playerInventory) {
        this(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, i, playerInventory);
    }

    @NonMinecraftNorFabric
    private void createPlayerInventory(PlayerInventory playerInventory) {
        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {  // Not void stack
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 1) {
                if (!this.insertItem(itemStack2, 3, 39, true))
                    return ItemStack.EMPTY;
                slot.onStackChanged(itemStack2, itemStack);
            } else if (index != 0) {
                if (Util.grindable(itemStack2)) {
                    if (!this.insertItem(itemStack2, 0, 1, false))
                        return ItemStack.EMPTY;
                } else if (index >= 2 && index < 29) {
                    if (!this.insertItem(itemStack2, 29, 38, false))
                        return ItemStack.EMPTY;
                } else if (index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false))
                    return ItemStack.EMPTY;
            } else if (!this.insertItem(itemStack2, 2, 38, false))
                return ItemStack.EMPTY;

            if (itemStack2.isEmpty())
                slot.setStack(ItemStack.EMPTY);
            else
                slot.markDirty();

            if (itemStack2.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Environment(EnvType.CLIENT)
    //@Deprecated
    public int getGrindProgress() {
        int grindTime = this.propertyDelegate.get(0);
        int grindTimeTotal = this.propertyDelegate.get(1);
        return grindTimeTotal != 0 && grindTime != 0 ? grindTime * 22 / grindTimeTotal : 0;
    }

    //@Deprecated
    public int getIngredientData() {
        return this.propertyDelegate.get(2);
    }
}
