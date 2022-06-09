package org.featurehouse.mcmod.spm.loot;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.featurehouse.mcmod.spm.mixin.acc.LootTableBuilderAccessor;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;

import java.util.List;

import static org.featurehouse.mcmod.spm.loot.SPMLootTables.*;

public class DeepDarkLootTables {
    public static void iceBox(LootTable.Builder hooks) {
        List<LootPool> pools = ((LootTableBuilderAccessor) hooks).getPools();
        LootUtils.setEntry(pools, 0, 0,
                LootTableReference.lootTableReference(RegistryHelper.id("misc/vanilla/ancient_city_stew")));
        LootUtils.addEntry(pools, 0,
                LootTableReference.lootTableReference(MORE_BAKED_SWEET_POTATOES));
    }
}
