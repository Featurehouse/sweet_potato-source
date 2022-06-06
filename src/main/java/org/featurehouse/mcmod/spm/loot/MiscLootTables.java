package org.featurehouse.mcmod.spm.loot;

import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.featurehouse.mcmod.spm.mixin.acc.LootTableBuilderAccessor;

import static org.featurehouse.mcmod.spm.loot.SPMLootTables.MORE_RAW_SWEET_POTATOES;

public class MiscLootTables {
    public static void pillagerOutpost(LootTable.Builder hooks) {
        LootUtils.addEntry(((LootTableBuilderAccessor) hooks).getPools(), 1,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(4));
    }

    public static void shipwreckSupply(LootTable.Builder hooks) {
        LootUtils.addEntry(((LootTableBuilderAccessor) hooks).getPools(), 0,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(7));
    }

    public static void woodlandMansion(LootTable.Builder hooks) {
        LootUtils.addEntry(((LootTableBuilderAccessor) hooks).getPools(), 1,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(15));
    }
}
