package io.featurehouse.spm.util.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

import static io.featurehouse.spm.SPMMain.PEEL;

public interface PeelInserter {
    enum PeelActionResult {
        INSERT,
        SPAWN
    }

    static PeelActionResult insert(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack eachStack;
        int i;
        for (i = 0; i < inventory.main.size(); ++i) {
            eachStack = inventory.main.get(i);
            if (eachStack.getItem().equals(PEEL) && eachStack.getCount() < PEEL.getMaxCount()) {
                eachStack.increment(1);
                return PeelActionResult.INSERT;
            }
        }
        for (i = 0; i < inventory.main.size(); ++i) {
            eachStack = inventory.main.get(i);
            if (eachStack.equals(ItemStack.EMPTY)) {
                inventory.main.set(i, new ItemStack(PEEL, 1));
                return PeelActionResult.INSERT;
            }
        }

        return PeelActionResult.SPAWN;
    }

    static void run(PlayerEntity player) {
        if (insert(player).equals(PeelActionResult.SPAWN)) {
            player.dropItem(PEEL);
        } else {
            player.getInventory().markDirty();
        }
    }
}
