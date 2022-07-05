package org.featurehouse.mcmod.spm.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import static org.featurehouse.mcmod.spm.loot.LootTables.MORE_RAW_SWEET_POTATOES;

public class MiscLootTables {
    public static void pillagerOutpost(LootSupplierBuilderHooks hooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(hooks.getPools().get(1));
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(4).build());
        hooks.getPools().set(1, builder.build());
    }

    public static void shipwreckSupply(LootSupplierBuilderHooks hooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(hooks.getPools().get(0));
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(7).build());
        hooks.getPools().set(0, builder.build());
    }

    public static void woodlandMansion(LootSupplierBuilderHooks hooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(hooks.getPools().get(1));
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(15).build());
        hooks.getPools().set(1, builder.build());
    }


}
