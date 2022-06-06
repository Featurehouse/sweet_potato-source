package org.featurehouse.mcmod.spm.loot;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.featurehouse.mcmod.spm.mixin.acc.LootTableBuilderAccessor;

import java.util.List;

import static org.featurehouse.mcmod.spm.loot.SPMLootTables.MORE_RAW_SWEET_POTATOES;

public class VillagerLootTables {
    private static final NumberProvider universalPotatoRange = UniformGenerator.between(1.0F, 7.0F);

    public static void plains(LootTable.Builder builderHooks) {
        List<LootPool> pools = ((LootTableBuilderAccessor) builderHooks).getPools();
        LootUtils.setEntry(pools, 0, 3, LootItem.lootTableItem(Items.POTATO).setWeight(6).apply(
                SetItemCountFunction.setCount(universalPotatoRange)));
        LootUtils.addEntry(pools, 0, LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(6));
    }

    public static void snowy(LootTable.Builder builderHooks) {
        List<LootPool> pools = ((LootTableBuilderAccessor) builderHooks).getPools();
        LootUtils.setEntry(pools, 0, 2,
                LootItem.lootTableItem(Items.POTATO).setWeight(7).apply(
                SetItemCountFunction.setCount(universalPotatoRange)));
        LootUtils.setEntry(pools, 0, 3,
                LootItem.lootTableItem(Items.BREAD).setWeight(7).apply(
                        SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))));
        LootUtils.addEntry(pools, 0,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(7) );
    }

    public static void taiga(LootTable.Builder builderHooks) {
        List<LootPool> pools = ((LootTableBuilderAccessor) builderHooks).getPools();
        LootUtils.setEntry(pools, 0, 3,
                LootItem.lootTableItem(Items.POTATO).setWeight(7).apply(
                SetItemCountFunction.setCount(universalPotatoRange)));
        LootUtils.addEntry(pools, 0,
                LootTableReference.lootTableReference(MORE_RAW_SWEET_POTATOES).setWeight(7));
    }
}
