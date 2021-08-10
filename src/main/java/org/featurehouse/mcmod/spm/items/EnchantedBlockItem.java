package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@StableApi
public class EnchantedBlockItem extends BlockItem {
    public EnchantedBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @ApiStatus.Internal
    public static EnchantedBlockItem of(String id, Block original, Settings settings) {
        return (EnchantedBlockItem) RegistryHelper.item(id, new EnchantedBlockItem(original, settings));
    }
}
