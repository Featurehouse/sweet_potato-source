package org.featurehouse.mcmod.spm.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import static org.featurehouse.mcmod.spm.loot.LootTables.MORE_RAW_SWEET_POTATOES;

public class UnderwaterRuinLootTables {
    public static void big(LootSupplierBuilderHooks hooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(hooks.getPools().get(0));
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(10).build());
        LootPool newPool = builder.build();
        hooks.getPools().set(0, newPool);
    }

    public static void small(LootSupplierBuilderHooks hooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(hooks.getPools().get(0));
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(5).build());
        LootPool newPool = builder.build();
        hooks.getPools().set(0, newPool);
    }
}
