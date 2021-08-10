package org.featurehouse.mcmod.spm.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;

@StableApi
public class EnchantedItem extends Item {

    public EnchantedItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
