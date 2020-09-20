package io.github.teddyxlandlee.sweet_potato.items;

import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;

public class AliasedEnchantedItem extends AliasedBlockItem {
    public AliasedEnchantedItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
