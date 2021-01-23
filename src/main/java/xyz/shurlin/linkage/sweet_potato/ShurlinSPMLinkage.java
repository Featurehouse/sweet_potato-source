package xyz.shurlin.linkage.sweet_potato;

import io.featurehouse.spm.items.EnchantedBlockItem;
import io.featurehouse.spm.util.properties.objects.ItemSettings;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.*;
import net.minecraft.item.*;
import xyz.shurlin.linkage.sweet_potato.block.sapling.EnchantedPhoenixSaplingGen;

import static io.featurehouse.spm.util.registries.RegistryHelper.*;
import static io.featurehouse.spm.util.properties.objects.BlockSettings.*;

public class ShurlinSPMLinkage implements ModInitializer {
    public static final Block ENCHANTED_PHOENIX_LEAVES;
    public static final Block ENCHANTED_PHOENIX_SAPLING;
    public static final Block POTTED_ENCHANTED_PHOENIX_SAPLING;

    public static final Item ENCHANTED_PHOENIX_LEAVES_ITEM;
    public static final Item ENCHANTED_PHOENIX_SAPLING_ITEM;

    @Override
    public void onInitialize() {
    }

    static {
        ENCHANTED_PHOENIX_LEAVES = createLeaves("enchanted_phoenix_leaves");
        ENCHANTED_PHOENIX_SAPLING = createEnchantedSapling("enchanted_phoenix_sapling", EnchantedPhoenixSaplingGen::new);
        POTTED_ENCHANTED_PHOENIX_SAPLING = createPotted("potted_enchanted_phoenix_sapling", ENCHANTED_PHOENIX_SAPLING);

        ENCHANTED_PHOENIX_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_phoenix_leaves", ENCHANTED_PHOENIX_LEAVES, ItemSettings.DECORATIONS);
        ENCHANTED_PHOENIX_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_phoenix_sapling", ENCHANTED_PHOENIX_SAPLING, ItemSettings.UNCDEC);
    }
}
