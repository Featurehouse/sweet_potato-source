package org.featurehouse.mcmod.spm.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.mixin.loot.table.LootPoolBuilderHooks;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import java.util.List;

import static org.featurehouse.mcmod.spm.loot.LootTables.MORE_RAW_SWEET_POTATOES;

public class VillagerLootTables {
    private static final NumberProvider universalPotatoRange = UniformGenerator.between(1.0F, 7.0F);

    public static void plains(LootSupplierBuilderHooks builderHooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(builderHooks.getPools().get(0));
        ((LootPoolBuilderHooks) builder).getEntries().set(3,
                LootItem.lootTableItem(Items.POTATO).setWeight(6).apply(
                        SetItemCountFunction.setCount(universalPotatoRange)).build());
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(6).build());
        LootPool newPool = builder.build();
        builderHooks.getPools().set(0, newPool);
    }

    public static void snowy(LootSupplierBuilderHooks builderHooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(builderHooks.getPools().get(0));
        List<LootPoolEntryContainer> entries = ((LootPoolBuilderHooks) builder).getEntries();
        entries.set(2, LootItem.lootTableItem(Items.POTATO).setWeight(7).apply(
                SetItemCountFunction.setCount(universalPotatoRange)).build());
        entries.set(3, LootItem.lootTableItem(Items.BREAD).setWeight(7).apply(
                SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).build());
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(7).build());
        LootPool newPool = builder.build();
        builderHooks.getPools().set(0, newPool);
    }

    public static void taiga(LootSupplierBuilderHooks builderHooks) {
        FabricLootPoolBuilder builder = FabricLootPoolBuilder.of(builderHooks.getPools().get(0));
        ((LootPoolBuilderHooks) builder).getEntries().set(3,
                LootItem.lootTableItem(Items.POTATO).setWeight(7).apply(
                        SetItemCountFunction.setCount(universalPotatoRange)).build());
        builder.withEntry(LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(7).build());
        LootPool newPool = builder.build();
        builderHooks.getPools().set(0, newPool);
    }
}
