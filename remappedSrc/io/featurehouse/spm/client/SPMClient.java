package io.featurehouse.spm.client;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.linkage.SPMLinkageClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class SPMClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE, SeedUpdaterScreen::new);
        ScreenRegistry.register(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, GrinderScreen::new);
        ScreenRegistry.register(SPMMain.MAGIC_CUBE_SCREEN_HANDLER_TYPE, MagicCubeScreen::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
                SPMMain.ENCHANTED_ACACIA_LEAVES, SPMMain.ENCHANTED_DARK_OAK_LEAVES,
                SPMMain.ENCHANTED_JUNGLE_LEAVES, SPMMain.ENCHANTED_OAK_LEAVES
        );
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getBirchColor(), SPMMain.ENCHANTED_BIRCH_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(), SPMMain.ENCHANTED_SPRUCE_LEAVES);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(),
                SPMMain.ENCHANTED_ACACIA_LEAVES_ITEM, SPMMain.ENCHANTED_DARK_OAK_LEAVES_ITEM,
                SPMMain.ENCHANTED_JUNGLE_LEAVES_ITEM, SPMMain.ENCHANTED_OAK_LEAVES_ITEM
        );
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getBirchColor(), SPMMain.ENCHANTED_BIRCH_LEAVES_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getSpruceColor(), SPMMain.ENCHANTED_SPRUCE_LEAVES_ITEM);


        FabricLoader.getInstance().getEntrypoints("sweet_potato.client", SPMLinkageClient.class).forEach(SPMLinkageClient::initClient);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                SPMMain.PURPLE_POTATO_CROP, SPMMain.RED_POTATO_CROP,
                SPMMain.WHITE_POTATO_CROP, SPMMain.SEED_UPDATER,
                SPMMain.ENCHANTED_ACACIA_SAPLING, SPMMain.ENCHANTED_BIRCH_SAPLING,
                SPMMain.ENCHANTED_DARK_OAK_SAPLING, SPMMain.ENCHANTED_OAK_SAPLING,
                SPMMain.ENCHANTED_JUNGLE_SAPLING, SPMMain.ENCHANTED_SPRUCE_SAPLING,
                //SPMMain.GRINDER,
                SPMMain.POTTED_ENCHANTED_ACACIA_SAPLING,
                SPMMain.POTTED_ENCHANTED_BIRCH_SAPLING,
                SPMMain.POTTED_ENCHANTED_DARK_OAK_SAPLING,
                SPMMain.POTTED_ENCHANTED_JUNGLE_SAPLING,
                SPMMain.POTTED_ENCHANTED_OAK_SAPLING,
                SPMMain.POTTED_ENCHANTED_SPRUCE_SAPLING,
                SPMMain.ENCHANTED_BEETROOTS_CROP, SPMMain.ENCHANTED_CARROTS_CROP,
                SPMMain.ENCHANTED_VANILLA_POTATOES_CROP, SPMMain.ENCHANTED_WHEAT_CROP,
                SPMMain.ENCHANTED_SUGAR_CANE
        );
    }
}
