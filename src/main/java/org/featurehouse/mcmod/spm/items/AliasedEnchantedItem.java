package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

@StableApi
public class AliasedEnchantedItem extends ItemNameBlockItem {
    public AliasedEnchantedItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @ApiStatus.Internal
    public static AliasedEnchantedItem of(String id, Block original) {
        return of(id, original, CreativeModeTab.TAB_MISC);
    }

    @ApiStatus.Internal
    public static AliasedEnchantedItem of(String id, Block original, CreativeModeTab itemGroup) {
        return (AliasedEnchantedItem) RegistryHelper.item(id, new AliasedEnchantedItem(original, new FabricItemSettings().tab(itemGroup)));
    }

    @ApiStatus.Internal
    public static AliasedEnchantedItem ofMiscFood(String id, Block original, FoodProperties foodComponent) {
        return (AliasedEnchantedItem) RegistryHelper.item(id, new AliasedEnchantedItem(original, new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).food(foodComponent)));
    }
}
