package org.featurehouse.mcmod.spm.loot;

import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.featurehouse.mcmod.spm.mixin.acc.LootTableBuilderAccessor;

import static org.featurehouse.mcmod.spm.loot.SPMLootTables.MORE_RAW_SWEET_POTATOES;

public class StrongholdLootTables {
    public static void corridor(LootTable.Builder hooks) {
        LootUtils.addEntry(((LootTableBuilderAccessor) hooks).getPools(), 0,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(11));
    }

    public static void crossing(LootTable.Builder hooks) {
        LootUtils.addEntry(((LootTableBuilderAccessor) hooks).getPools(), 0,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(12));
    }
}
