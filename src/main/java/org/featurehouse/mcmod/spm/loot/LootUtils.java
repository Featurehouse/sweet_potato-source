package org.featurehouse.mcmod.spm.loot;

import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import java.util.List;

public class LootUtils {
    public static void addEntry(List<LootPool> pools, int index, LootPoolEntryContainer.Builder<?> entry) {
        LootPool original = pools.get(index);
        LootPool.Builder builder = FabricLootPoolBuilder.copyOf(original).add(entry);
        pools.set(index, builder.build());
    }

    public static void setEntry(List<LootPool> pools, int poolIndex, int lootIndex,
                                LootPoolEntryContainer.Builder<?> entry) {
        LootPool original = pools.get(poolIndex);
        original.entries[lootIndex] = entry.build();
    }
}
