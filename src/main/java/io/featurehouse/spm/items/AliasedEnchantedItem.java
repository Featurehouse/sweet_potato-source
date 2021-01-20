package io.featurehouse.spm.items;

import io.featurehouse.spm.util.registries.RegistryHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AliasedEnchantedItem extends AliasedBlockItem {
    public AliasedEnchantedItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public static AliasedEnchantedItem of(String id, Block original) {
        return of(id, original, ItemGroup.MISC);
    }

    public static AliasedEnchantedItem of(String id, Block original, ItemGroup itemGroup) {
        return (AliasedEnchantedItem) RegistryHelper.item(id, new AliasedEnchantedItem(original, new FabricItemSettings().group(itemGroup)));
    }

    public static AliasedEnchantedItem ofMiscFood(String id, Block original, FoodComponent foodComponent) {
        return (AliasedEnchantedItem) RegistryHelper.item(id, new AliasedEnchantedItem(original, new FabricItemSettings().group(ItemGroup.MISC).food(foodComponent)));
    }
}
