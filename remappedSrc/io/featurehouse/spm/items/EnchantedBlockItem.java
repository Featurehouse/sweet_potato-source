package io.featurehouse.spm.items;

import io.featurehouse.spm.util.registries.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class EnchantedBlockItem extends BlockItem {
    public EnchantedBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public static EnchantedBlockItem of(String id, Block original, Settings settings) {
        return (EnchantedBlockItem) RegistryHelper.item(id, new EnchantedBlockItem(original, settings));
    }
}
