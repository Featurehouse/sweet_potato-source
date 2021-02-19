package xyz.shurlin.linkage.sweet_potato.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import xyz.shurlin.linkage.ShurlinLinkageApiClient;
import xyz.shurlin.linkage.sweet_potato.ShurlinSPMLinkage;

@Environment(EnvType.CLIENT)
public class ShurlinSPMLinkageClient implements ShurlinLinkageApiClient {
    @Override
    public void initClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ShurlinSPMLinkage.ENCHANTED_PHOENIX_SAPLING,
                ShurlinSPMLinkage.POTTED_ENCHANTED_PHOENIX_SAPLING,
                ShurlinSPMLinkage.ENCHANTED_PEAR_SAPLING,
                ShurlinSPMLinkage.POTTED_ENCHANTED_PEAR_SAPLING
        );
    }
}
