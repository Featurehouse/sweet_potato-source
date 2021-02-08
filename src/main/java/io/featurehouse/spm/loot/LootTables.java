package io.featurehouse.spm.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.mixin.loot.table.LootSupplierBuilderHooks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;

import static io.featurehouse.spm.SPMMain.MODID;

public class LootTables {
    public static final Identifier
        ENTITIES_HUSK, ENTITIES_ZOMBIE, ENTITIES_ZOMBIE_VILLAGER,
        RAW_SWEET_POTATOES, MORE_RAW_SWEET_POTATOES;

    public static void init() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
            if (identifier.getPath().matches("^(entities/((husk)|(zombie(_villager)?)))$") && isVanilla(identifier)) {
                LootSupplierBuilderHooks builderHooks = (LootSupplierBuilderHooks) fabricLootSupplierBuilder;
                LootPool newPool1 = FabricLootPoolBuilder.of(builderHooks.getPools().get(1))
                        .withEntry(LootTableEntry.builder(RAW_SWEET_POTATOES).build()).build();
                builderHooks.getPools().set(1, newPool1);
            }
        });
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
            String idPath;
            if ((idPath = identifier.getPath()).matches("^(chests/village/village_\\w+_house)$") && isVanilla(identifier)) {
                LootSupplierBuilderHooks builderHooks = (LootSupplierBuilderHooks) fabricLootSupplierBuilder;
                switch (idPath.substring(23, idPath.length() - 6)) {
                    case "plains":
                        VillagerLootTables.plains(builderHooks);
                    case "snowy":
                        VillagerLootTables.snowy(builderHooks);
                    case "taiga":
                        VillagerLootTables.taiga(builderHooks);
                }
            }
        });
    }

    static {
        ENTITIES_HUSK = new Identifier("entities/husk");
        ENTITIES_ZOMBIE = new Identifier("entities/zombie");
        ENTITIES_ZOMBIE_VILLAGER = new Identifier("entities/zombie_villager");

        RAW_SWEET_POTATOES = new Identifier(MODID, "misc/raw_sweet_potatoes");
        MORE_RAW_SWEET_POTATOES = new Identifier(MODID, "misc/more_raw_sweet_potatoes");
    }

    static boolean isVanilla(Identifier identifier) {
        return identifier.getNamespace().equals("minecraft");
    }
}
