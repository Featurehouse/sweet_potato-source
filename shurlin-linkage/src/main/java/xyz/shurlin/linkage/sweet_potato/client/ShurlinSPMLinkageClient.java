package xyz.shurlin.linkage.sweet_potato.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import xyz.shurlin.linkage.sweet_potato.ShurlinSPMLinkage;

@Environment(EnvType.CLIENT)
public class ShurlinSPMLinkageClient
        implements xyz.shurlin.linkage.sweet_potato.linkage.internal.ShurlinSPMLinkage {
    @Override
    public void invoke() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ShurlinSPMLinkage.ENCHANTED_PHOENIX_SAPLING,
                ShurlinSPMLinkage.POTTED_ENCHANTED_PHOENIX_SAPLING,
                ShurlinSPMLinkage.ENCHANTED_PEAR_SAPLING,
                ShurlinSPMLinkage.POTTED_ENCHANTED_PEAR_SAPLING
        );
    }
}
