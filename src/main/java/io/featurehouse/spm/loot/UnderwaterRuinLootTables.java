package io.featurehouse.spm.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootTableEntry;

import static io.featurehouse.spm.loot.LootTables.MORE_RAW_SWEET_POTATOES;

public class UnderwaterRuinLootTables {
    public static void big(LootSupplierBuilderHooks hooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(hooks.getPools().get(0));
        builder.withEntry(LootTableEntry.builder(MORE_RAW_SWEET_POTATOES).weight(10).build());
        LootPool newPool = builder.build();
        hooks.getPools().set(0, newPool);
    }

    public static void small(LootSupplierBuilderHooks hooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(hooks.getPools().get(0));
        builder.withEntry(LootTableEntry.builder(MORE_RAW_SWEET_POTATOES).weight(5).build());
        LootPool newPool = builder.build();
        hooks.getPools().set(0, newPool);
    }
}
