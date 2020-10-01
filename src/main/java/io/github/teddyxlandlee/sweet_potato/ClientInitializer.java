package io.github.teddyxlandlee.sweet_potato;

import io.github.teddyxlandlee.sweet_potato.screen.GrinderScreen;
import io.github.teddyxlandlee.sweet_potato.screen.GrinderScreenHandler;
import io.github.teddyxlandlee.sweet_potato.screen.SeedUpdaterScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ClientInitializer implements ClientModInitializer {
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
        //ScreenRegistry.register(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, GrinderScreen::new);
        //ScreenRegistry.register(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, DeprecatedGrinderScreen$3::new);
        ScreenRegistry.<GrinderScreenHandler, GrinderScreen>register(
                SPMMain.GRINDER_SCREEN_HANDLER_TYPE, GrinderScreen::new
        );

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
    }
}
