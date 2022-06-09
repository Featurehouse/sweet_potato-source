package org.featurehouse.mcmod.spm.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.featurehouse.mcmod.spm.mixin.acc.LootTableBuilderAccessor;

import java.util.List;

import static org.featurehouse.mcmod.spm.SPMMain.MODID;

public class SPMLootTables {
    static final ResourceLocation RAW_SWEET_POTATOES, MORE_RAW_SWEET_POTATOES,
                MORE_BAKED_SWEET_POTATOES;

    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, identifier, tableBuilder, source) -> {
            if (!isVanilla(identifier)) return;
            String idPath = identifier.getPath();
            if (idPath.matches("^(entities/((husk)|(zombie(_villager)?)))$")) {
                List<LootPool> pools = ((LootTableBuilderAccessor) tableBuilder).getPools();
                LootUtils.addEntry(pools, 1, LootTableReference.lootTableReference(RAW_SWEET_POTATOES));
            } else if (idPath.matches("^(chests/village/village_\\w+_house)$")) {
                switch (idPath.substring(23, idPath.length() - 6)) {
                    case "plains":
                        VillagerLootTables.plains(tableBuilder);
                    case "snowy":
                        VillagerLootTables.snowy(tableBuilder);
                    case "taiga":
                        VillagerLootTables.taiga(tableBuilder);
                }
            } else if (idPath.startsWith("chests/")) {
                String suf = idPath.substring(7);
                if (suf.matches("^(underwater_ruin_((big)|(small)))$")) {
                    //LootSupplierBuilderHooks builderHooks = (LootSupplierBuilderHooks) fabricLootSupplierBuilder;
                    if (suf.charAt(16) == 'b')
                        UnderwaterRuinLootTables.big(tableBuilder);
                    else
                        UnderwaterRuinLootTables.small(tableBuilder);
                } else if (suf.matches("^(stronghold_c((orridor)|(rossing)))$")) {
                    if (suf.charAt(12) == 'o')
                        StrongholdLootTables.corridor(tableBuilder);
                    else
                        StrongholdLootTables.crossing(tableBuilder);
                } else if (suf.matches("^ancient_city(_ice_box)$")) {
                    DeepDarkLootTables.iceBox(tableBuilder);
                } else if (suf.equals("shipwreck_supply"))
                    MiscLootTables.shipwreckSupply(tableBuilder);
                else if (suf.equals("pillager_outpost"))
                    MiscLootTables.pillagerOutpost(tableBuilder);
                else if (suf.equals("woodland_mansion"))
                    MiscLootTables.woodlandMansion(tableBuilder);
            }
        });
    }

    static {
        RAW_SWEET_POTATOES = new ResourceLocation(MODID, "misc/raw_sweet_potatoes");
        MORE_RAW_SWEET_POTATOES = new ResourceLocation(MODID, "misc/more_raw_sweet_potatoes");
        MORE_BAKED_SWEET_POTATOES = new ResourceLocation(MODID, "misc/more_baked_sweet_potatoes");
    }

    static boolean isVanilla(ResourceLocation identifier) {
        return identifier.getNamespace().equals("minecraft");
    }
}
