package io.featurehouse.spm.util.inventory;

import io.featurehouse.spm.SPMMain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public interface PeelInserter {
    enum PeelActionResult {
        INSERT,
        SPAWN
    }

    static PeelActionResult insert(PlayerEntity player) {
        PlayerInventory inventory = player.inventory;
        ItemStack eachStack;
        int i;
        for (i = 0; i < inventory.main.size(); ++i) {
            eachStack = inventory.main.get(i);
            if (eachStack.getItem().equals(SPMMain.PEEL) && eachStack.getCount() < SPMMain.PEEL.getMaxCount()) {
                eachStack.increment(1);
                return PeelActionResult.INSERT;
            }
        }
        for (i = 0; i < inventory.main.size(); ++i) {
            eachStack = inventory.main.get(i);
            if (eachStack.equals(ItemStack.EMPTY)) {
                inventory.main.set(i, new ItemStack(SPMMain.PEEL, 1));
                return PeelActionResult.INSERT;
            }
        }

        return PeelActionResult.SPAWN;
    }

    static void run(PlayerEntity player) {
        if (insert(player).equals(PeelActionResult.SPAWN)) {
            player.dropItem(SPMMain.PEEL);
        } else {
            player.inventory.markDirty();
        }
    }
}
