package org.featurehouse.mcmod.spm.util.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.featurehouse.mcmod.spm.SPMMain;

public class MagicCubeInputSlot extends Slot {
    public MagicCubeInputSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return SPMMain.RAW_SWEET_POTATOES.contains(stack.getItem());
        //return stack.getItem().isIn(SPMMain.RAW_SWEET_POTATOES);
    }
}
