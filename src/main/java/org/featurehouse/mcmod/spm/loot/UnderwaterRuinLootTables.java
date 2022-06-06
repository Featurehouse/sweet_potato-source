package org.featurehouse.mcmod.spm.loot;

import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.featurehouse.mcmod.spm.mixin.acc.LootTableBuilderAccessor;

import static org.featurehouse.mcmod.spm.loot.SPMLootTables.MORE_RAW_SWEET_POTATOES;

public class UnderwaterRuinLootTables {
    public static void big(LootTable.Builder hooks) {
        LootUtils.addEntry(((LootTableBuilderAccessor) hooks).getPools(), 0,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(10));
    }

    public static void small(LootTable.Builder hooks) {
        LootUtils.addEntry(((LootTableBuilderAccessor) hooks).getPools(), 0,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(5));
    }
}
