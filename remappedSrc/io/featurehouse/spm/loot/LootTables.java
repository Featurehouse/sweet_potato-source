package io.featurehouse.spm.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;

import static io.featurehouse.spm.SPMMain.MODID;

public class LootTables {
    static final Identifier RAW_SWEET_POTATOES, MORE_RAW_SWEET_POTATOES;

    public static void init() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
            if (!isVanilla(identifier)) return;
            String idPath = identifier.getPath();
            if (idPath.matches("^(entities/((husk)|(zombie(_villager)?)))$")) {
                LootSupplierBuilderHooks builderHooks = (LootSupplierBuilderHooks) fabricLootSupplierBuilder;
                LootPool newPool1 = FabricLootPoolBuilder.of(builderHooks.getPools().get(1))
                        .withEntry(LootTableEntry.builder(RAW_SWEET_POTATOES).build()).build();
                builderHooks.getPools().set(1, newPool1);
            } else if (idPath.matches("^(chests/village/village_\\w+_house)$")) {
                LootSupplierBuilderHooks builderHooks = (LootSupplierBuilderHooks) fabricLootSupplierBuilder;
                switch (idPath.substring(23, idPath.length() - 6)) {
                    case "plains":
                        VillagerLootTables.plains(builderHooks);
                    case "snowy":
                        VillagerLootTables.snowy(builderHooks);
                    case "taiga":
                        VillagerLootTables.taiga(builderHooks);
                }
            } else if (idPath.startsWith("chests/")) {
                String suf = idPath.substring(7);
                if (suf.matches("^(underwater_ruin_((big)|(small)))$")) {
                    LootSupplierBuilderHooks builderHooks = (LootSupplierBuilderHooks) fabricLootSupplierBuilder;
                    if (suf.substring(16).equals("big"))
                        UnderwaterRuinLootTables.big(builderHooks);
                    else
                        UnderwaterRuinLootTables.small(builderHooks);
                } else if (suf.matches("^(stronghold_c((orridor)|(rossing)))")) {
                    LootSupplierBuilderHooks builderHooks = (LootSupplierBuilderHooks) fabricLootSupplierBuilder;
                    if (suf.substring(12).equals("orridor"))
                        StrongholdLootTables.corridor(builderHooks);
                    else
                        StrongholdLootTables.crossing(builderHooks);
                } else if (suf.equals("shipwreck_supply"))
                    MiscLootTables.shipwreckSupply((LootSupplierBuilderHooks) fabricLootSupplierBuilder);
                else if (suf.equals("pillager_outpost"))
                    MiscLootTables.pillagerOutpost((LootSupplierBuilderHooks) fabricLootSupplierBuilder);
                else if (suf.equals("woodland_mansion"))
                    MiscLootTables.woodlandMansion((LootSupplierBuilderHooks) fabricLootSupplierBuilder);
            }
        });
    }

    static {
        RAW_SWEET_POTATOES = new Identifier(MODID, "misc/raw_sweet_potatoes");
        MORE_RAW_SWEET_POTATOES = new Identifier(MODID, "misc/more_raw_sweet_potatoes");
    }

    static boolean isVanilla(Identifier identifier) {
        return identifier.getNamespace().equals("minecraft");
    }
}
