package org.featurehouse.mcmod.spm.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.inventory.UniversalResultSlot;
import org.featurehouse.mcmod.spm.util.iprops.IntGrinderProperties;
import org.featurehouse.mcmod.spm.util.registries.GrindingUtils;

public class GrinderScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final IntGrinderProperties properties;

    protected Level world;

    /**
     * From: Registry
     */
    public GrinderScreenHandler(int i, Inventory playerInventory) {
        this(i, playerInventory, playerInventory.player.level, new SimpleContainer(2), new IntGrinderProperties.Impl());
    }

    /**
     * From: Grinder Block Entity
     */
    public GrinderScreenHandler(int syncId, Inventory playerInventory, Level world, Container inventory, IntGrinderProperties properties) {
        super(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, syncId);
        this.inventory = inventory;
        this.properties = properties;
        this.addDataSlots(properties);
        this.world = world;

        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new UniversalResultSlot(playerInventory.player, inventory, 1, 116, 35));

        this.createPlayerInventory(playerInventory);
    }

    //@NonMinecraftNorFabric
    private void createPlayerInventory(Inventory playerInventory) {
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
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {  // Not void stack
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index == 1) {
                if (!this.moveItemStackTo(itemStack2, 2, 38, true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index != 0) {
                if (GrindingUtils.grindable(itemStack2)) {
                    if (!this.moveItemStackTo(itemStack2, 0, 1, false))
                        return ItemStack.EMPTY;
                } else if (index < 29) {
                    if (!this.moveItemStackTo(itemStack2, 29, 38, false))
                        return ItemStack.EMPTY;
                } else if (index < 38 && !this.moveItemStackTo(itemStack2, 2, 29, false))
                    return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(itemStack2, 2, 38, false))
                return ItemStack.EMPTY;

            if (itemStack2.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();

            if (itemStack2.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(player, itemStack2);
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
