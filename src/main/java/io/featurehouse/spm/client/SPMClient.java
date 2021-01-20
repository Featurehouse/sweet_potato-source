package io.featurehouse.spm.client;

import io.featurehouse.spm.SPMMain;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class SPMClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        /*ScreenProviderRegistry.INSTANCE.<SeedUpdaterScreenHandler>registerFactory(new Identifier(
                SPMMain.MODID, "seed_updating"
        ), (handler) -> {
            assert MinecraftClient.getInstance().player != null;
            return new SeedUpdaterScreen(handler, MinecraftClient.getInstance().player.inventory,
                    new TranslatableText(SPMMain.SEED_UPDATER_TRANSLATION_KEY));
        });*/
        ScreenRegistry.register(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE, SeedUpdaterScreen::new);
        ScreenRegistry.register(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, GrinderScreen::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
                SPMMain.ENCHANTED_ACACIA_LEAVES, SPMMain.ENCHANTED_DARK_OAK_LEAVES,
                SPMMain.ENCHANTED_JUNGLE_LEAVES, SPMMain.ENCHANTED_OAK_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getBirchColor(), SPMMain.ENCHANTED_BIRCH_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(), SPMMain.ENCHANTED_SPRUCE_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlock(SPMMain.PURPLE_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SPMMain.RED_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SPMMain.WHITE_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SPMMain.SEED_UPDATER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                SPMMain.ENCHANTED_ACACIA_SAPLING, SPMMain.ENCHANTED_BIRCH_SAPLING,
                SPMMain.ENCHANTED_DARK_OAK_SAPLING, SPMMain.ENCHANTED_OAK_SAPLING,
                SPMMain.ENCHANTED_JUNGLE_SAPLING, SPMMain.ENCHANTED_SPRUCE_SAPLING);
        BlockRenderLayerMap.INSTANCE.putBlock(SPMMain.GRINDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                SPMMain.POTTED_ENCHANTED_ACACIA_SAPLING,
                SPMMain.POTTED_ENCHANTED_BIRCH_SAPLING,
                SPMMain.POTTED_ENCHANTED_DARK_OAK_SAPLING,
                SPMMain.POTTED_ENCHANTED_JUNGLE_SAPLING,
                SPMMain.POTTED_ENCHANTED_OAK_SAPLING,
                SPMMain.POTTED_ENCHANTED_SPRUCE_SAPLING);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                SPMMain.ENCHANTED_BEETROOTS_CROP, SPMMain.ENCHANTED_CARROTS_CROP,
                SPMMain.ENCHANTED_VANILLA_POTATOES_CROP, SPMMain.ENCHANTED_WHEAT_CROP);
        BlockRenderLayerMap.INSTANCE.putBlock(SPMMain.ENCHANTED_SUGAR_CANE, RenderLayer.getCutout());
    }
}
