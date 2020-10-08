package io.github.teddyxlandlee.sweet_potato.screen;

import io.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.util.GrindingResultSlot;
import io.github.teddyxlandlee.sweet_potato.util.Util;
import io.github.teddyxlandlee.sweet_potato.util.network.GrinderPropertiesAccessor;
import io.github.teddyxlandlee.sweet_potato.util.network.NullAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GrinderScreenHandler extends ScreenHandler {
    Logger logger = LogManager.getLogger();

    private final Inventory inventory;
    private final GrinderPropertiesAccessor propertiesAccessor;

    protected World world;

    /**
     * From: Registry
     */
    public GrinderScreenHandler(int i, PlayerInventory playerInventory) {
        this(i, playerInventory, playerInventory.player.world, new SimpleInventory(2), new NullAccessor());
    }

    /**
     * From: Grinder Block Entity
     */
    public GrinderScreenHandler(int syncId, PlayerInventory playerInventory, World world, Inventory inventory, GrinderPropertiesAccessor accessor) {
        super(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, syncId);
        //this.playerInventory = playerInventory;
        this.inventory = inventory;
        this.propertiesAccessor = accessor;
        this.addProperties(accessor);
        this.world = world;

        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new GrindingResultSlot(playerInventory.player, inventory, 1, 116, 35));

        this.createPlayerInventory(playerInventory);
        //TODO: Make client-side block entity available
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
    public int getGrindProgress() {
        int grindTime = propertiesAccessor.getGrindTime();
        int grindTimeTotal = propertiesAccessor.getGrindTimeTotal();
        return grindTimeTotal != 0 && grindTime != 0 ? grindTime * 22 / grindTimeTotal : 0;
    }

    @Environment(EnvType.CLIENT)
    public float getIngredientData() {
        return propertiesAccessor.getIngredientData();
    }
}
