package org.featurehouse.mcmod.spm.util.inventory;

import org.featurehouse.mcmod.spm.SPMMain;
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

    @Override
    public boolean canInsert(ItemStack stack) {
        return SPMMain.RAW_SWEET_POTATOES.contains(stack.getItem());
        //return stack.getItem().isIn(SPMMain.RAW_SWEET_POTATOES);
    }
}
