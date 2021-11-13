package xyz.shurlin.linkage.sweet_potato;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.featurehouse.mcmod.spm.items.EnchantedBlockItem;
import org.featurehouse.mcmod.spm.util.objsettings.ItemSettings;
import org.featurehouse.mcmod.spm.util.registries.ComposterHelper;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import xyz.shurlin.linkage.sweet_potato.block.sapling.EnchantedPearSaplingGen;
import xyz.shurlin.linkage.sweet_potato.block.sapling.EnchantedPhoenixSaplingGen;

import static org.featurehouse.mcmod.spm.util.objsettings.BlockSettings.*;

public class ShurlinSPMLinkage implements xyz.shurlin.linkage.sweet_potato.linkage.internal.ShurlinSPMLinkage {
    public static final ModContainer MOD = FabricLoader.getInstance()
            .getModContainer("sweet_potato_mod-shurlin-linkage")
            .orElseThrow(() -> new InternalError("Cannot identify the runtime environment - mod " +
                    "`sweet_potato_mod-shurlin-linkage` cannot be found"));

    public static final Block ENCHANTED_PHOENIX_LEAVES;
    public static final Block ENCHANTED_PHOENIX_SAPLING;
    public static final Block POTTED_ENCHANTED_PHOENIX_SAPLING;
    public static final Block ENCHANTED_PEAR_LEAVES;
    public static final Block ENCHANTED_PEAR_RIPE_LEAVES;
    public static final Block ENCHANTED_PEAR_SAPLING;
    public static final Block POTTED_ENCHANTED_PEAR_SAPLING;

    public static final Item ENCHANTED_PHOENIX_LEAVES_ITEM;
    public static final Item ENCHANTED_PHOENIX_SAPLING_ITEM;
    public static final Item ENCHANTED_PEAR_LEAVES_ITEM;
    public static final Item ENCHANTED_PEAR_RIPE_LEAVES_ITEM;
    public static final Item ENCHANTED_PEAR_SAPLING_ITEM;

    @Override
    public void invoke() {
        System.out.println("Hello SPM & Shurlin!");
        ResourceManagerHelper.registerBuiltinResourcePack(RegistryHelper.id("shurlin-spm-linkage"),
                MOD, ResourcePackActivationType.ALWAYS_ENABLED);

        ComposterHelper.registerCompostableItem(0.3f, ENCHANTED_PHOENIX_SAPLING_ITEM);
        ComposterHelper.registerCompostableItem(0.3f, ENCHANTED_PHOENIX_LEAVES_ITEM);
        ComposterHelper.registerCompostableItem(0.3f, ENCHANTED_PEAR_SAPLING_ITEM);
        ComposterHelper.registerCompostableItem(0.3f, ENCHANTED_PEAR_LEAVES_ITEM);
        ComposterHelper.registerCompostableItem(0.3f, ENCHANTED_PEAR_RIPE_LEAVES_ITEM);
        FuelRegistry.INSTANCE.add(ENCHANTED_PHOENIX_SAPLING_ITEM, 100);
        FuelRegistry.INSTANCE.add(ENCHANTED_PEAR_SAPLING_ITEM, 100);
    }

    static {
        ENCHANTED_PHOENIX_LEAVES = createLeaves("enchanted_phoenix_leaves");
        ENCHANTED_PHOENIX_SAPLING = createEnchantedSapling("enchanted_phoenix_sapling", EnchantedPhoenixSaplingGen::new);
        POTTED_ENCHANTED_PHOENIX_SAPLING = createPotted("potted_enchanted_phoenix_sapling", ENCHANTED_PHOENIX_SAPLING);
        ENCHANTED_PEAR_LEAVES = createLeaves("enchanted_pear_leaves");
        ENCHANTED_PEAR_RIPE_LEAVES = createLeaves("enchanted_pear_ripe_leaves");
        ENCHANTED_PEAR_SAPLING = createEnchantedSapling("enchanted_pear_sapling", EnchantedPearSaplingGen::new);
        POTTED_ENCHANTED_PEAR_SAPLING = createPotted("potted_enchanted_pear_sapling", ENCHANTED_PEAR_SAPLING);

        ENCHANTED_PHOENIX_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_phoenix_leaves", ENCHANTED_PHOENIX_LEAVES, ItemSettings.decorations());
        ENCHANTED_PHOENIX_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_phoenix_sapling", ENCHANTED_PHOENIX_SAPLING, ItemSettings.uncommonDecorations());
        ENCHANTED_PEAR_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_pear_leaves", ENCHANTED_PEAR_LEAVES, ItemSettings.decorations());
        ENCHANTED_PEAR_RIPE_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_pear_ripe_leaves", ENCHANTED_PEAR_RIPE_LEAVES, ItemSettings.decorations());
        ENCHANTED_PEAR_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_pear_sapling", ENCHANTED_PEAR_SAPLING, ItemSettings.uncommonDecorations());
    }
}
