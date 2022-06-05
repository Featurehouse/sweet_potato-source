package org.featurehouse.mcmod.spm.util.inventory;

import static org.featurehouse.mcmod.spm.SPMMain.PEEL;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface PeelInserter {
    enum PeelActionResult {
        INSERT,
        SPAWN
    }

    static PeelActionResult insert(Player player) {
        Inventory inventory = player.getInventory();
        ItemStack eachStack;
        int i;
        for (i = 0; i < inventory.items.size(); ++i) {
            eachStack = inventory.items.get(i);
            if (eachStack.getItem().equals(PEEL) && eachStack.getCount() < PEEL.getMaxStackSize()) {
                eachStack.grow(1);
                return PeelActionResult.INSERT;
            }
        }
        for (i = 0; i < inventory.items.size(); ++i) {
            eachStack = inventory.items.get(i);
            if (eachStack.equals(ItemStack.EMPTY)) {
                inventory.items.set(i, new ItemStack(PEEL, 1));
                return PeelActionResult.INSERT;
            }
        }

        return PeelActionResult.SPAWN;
    }

    static void run(Player player) {
        if (insert(player).equals(PeelActionResult.SPAWN)) {
            player.spawnAtLocation(PEEL);
        } else {
            player.getInventory().setChanged();
        }
    }
}
