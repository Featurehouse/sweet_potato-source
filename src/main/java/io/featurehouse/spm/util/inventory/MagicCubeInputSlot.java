package io.featurehouse.spm.util.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class MagicCubeInputSlot extends Slot {
    public MagicCubeInputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return 1;
    }
}
