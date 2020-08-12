package com.github.teddyxlandlee.sweet_potato;

import com.github.teddyxlandlee.sweet_potato.screen.GrinderScreen;
import com.github.teddyxlandlee.sweet_potato.screen.SeedUpdaterScreen;
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
                ExampleMod.MODID, "seed_updating"
        ), (handler) -> {
            assert MinecraftClient.getInstance().player != null;
            return new SeedUpdaterScreen(handler, MinecraftClient.getInstance().player.inventory,
                    new TranslatableText(ExampleMod.SEED_UPDATER_TRANSLATION_KEY));
        });*/
        ScreenRegistry.register(ExampleMod.SEED_UPDATER_SCREEN_HANDLER_TYPE, SeedUpdaterScreen::new);
        ScreenRegistry.register(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, GrinderScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.PURPLE_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.RED_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.WHITE_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.SEED_UPDATER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ExampleMod.ENCHANTED_ACACIA_SAPLING, ExampleMod.ENCHANTED_BIRCH_SAPLING,
                ExampleMod.ENCHANTED_DARK_OAK_SAPLING, ExampleMod.ENCHANTED_OAK_SAPLING,
                ExampleMod.ENCHANTED_JUNGLE_SAPLING, ExampleMod.ENCHANTED_SPRUCE_SAPLING);
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleMod.GRINDER, RenderLayer.getCutout());
    }
}
