package org.featurehouse.spm.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.mixin.loot.table.LootPoolBuilderHooks;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTableRange;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetCountLootFunction;

import java.util.List;

import static org.featurehouse.spm.loot.LootTables.MORE_RAW_SWEET_POTATOES;

public class VillagerLootTables {
    private static final LootTableRange universalPotatoRange = new UniformLootTableRange(1.0F, 7.0F);

    public static void plains(LootSupplierBuilderHooks builderHooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(builderHooks.getPools().get(0));
        ((LootPoolBuilderHooks) builder).getEntries().set(3,
                ItemEntry.builder(Items.POTATO).weight(6).apply(
                        SetCountLootFunction.builder(universalPotatoRange)).build());
        builder.withEntry(LootTableEntry.builder(MORE_RAW_SWEET_POTATOES).weight(6).build());
        LootPool newPool = builder.build();
        builderHooks.getPools().set(0, newPool);
    }

    public static void snowy(LootSupplierBuilderHooks builderHooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(builderHooks.getPools().get(0));
        List<LootPoolEntry> entries = ((LootPoolBuilderHooks) builder).getEntries();
        entries.set(2, ItemEntry.builder(Items.POTATO).weight(7).apply(
                SetCountLootFunction.builder(universalPotatoRange)).build());
        entries.set(3, ItemEntry.builder(Items.BREAD).weight(7).apply(
                SetCountLootFunction.builder(new UniformLootTableRange(1.0F, 4.0F))).build());
        builder.withEntry(LootTableEntry.builder(MORE_RAW_SWEET_POTATOES).weight(7).build());
        LootPool newPool = builder.build();
        builderHooks.getPools().set(0, newPool);
    }

    public static void taiga(LootSupplierBuilderHooks builderHooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(builderHooks.getPools().get(0));
        ((LootPoolBuilderHooks) builder).getEntries().set(3,
                ItemEntry.builder(Items.POTATO).weight(7).apply(
                        SetCountLootFunction.builder(universalPotatoRange)).build());
        builder.withEntry(LootTableEntry.builder(MORE_RAW_SWEET_POTATOES).weight(7).build());
        LootPool newPool = builder.build();
        builderHooks.getPools().set(0, newPool);
    }
}
