package org.featurehouse.mcmod.spm.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;

@StableApi
public class EnchantedItem extends Item {

    public EnchantedItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
