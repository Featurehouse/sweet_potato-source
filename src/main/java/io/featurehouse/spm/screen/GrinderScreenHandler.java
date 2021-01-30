package io.featurehouse.spm.screen;

import io.featurehouse.annotation.NonMinecraftNorFabric;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.util.Util;
import io.featurehouse.spm.util.inventory.UniversalResultSlot;
import io.featurehouse.spm.util.properties.grinder.IntGrinderProperties;
import io.featurehouse.spm.util.properties.grinder.NullGrinderProperties;
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

public class GrinderScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final IntGrinderProperties properties;

    protected World world;

    /**
     * From: Registry
     */
    public GrinderScreenHandler(int i, PlayerInventory playerInventory) {
        this(i, playerInventory, playerInventory.player.world, new SimpleInventory(2), new NullGrinderProperties());
    }

    /**
     * From: Grinder Block Entity
     */
    public GrinderScreenHandler(int syncId, PlayerInventory playerInventory, World world, Inventory inventory, IntGrinderProperties properties) {
        super(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, syncId);
        this.inventory = inventory;
        this.properties = properties;
        this.addProperties(properties);
        this.world = world;

        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new UniversalResultSlot(playerInventory.player, inventory, 1, 116, 35));

        this.createPlayerInventory(playerInventory);
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
                if (!this.insertItem(itemStack2, 2, 38, true))
                    return ItemStack.EMPTY;
                slot.onStackChanged(itemStack2, itemStack);
            } else if (index != 0) {
                if (Util.grindable(itemStack2)) {
                    if (!this.insertItem(itemStack2, 0, 1, false))
                        return ItemStack.EMPTY;
                } else if (index < 29) {
                    if (!this.insertItem(itemStack2, 29, 38, false))
                        return ItemStack.EMPTY;
                } else if (index < 38 && !this.insertItem(itemStack2, 2, 29, false))
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
        int grindTime = properties.getGrindTime();
        int grindTimeTotal = properties.getGrindTimeTotal();
        return grindTimeTotal != 0 && grindTime != 0 ? grindTime * 22 / grindTimeTotal : 0;
    }

    @Environment(EnvType.CLIENT)
    public double getIngredientData() {
        return properties.getIngredientData();
    }
}
