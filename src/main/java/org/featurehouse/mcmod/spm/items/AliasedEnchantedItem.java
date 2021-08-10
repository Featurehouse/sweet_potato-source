package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@StableApi
public class AliasedEnchantedItem extends AliasedBlockItem {
    public AliasedEnchantedItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @ApiStatus.Internal
    public static AliasedEnchantedItem of(String id, Block original) {
        return of(id, original, ItemGroup.MISC);
    }

    @ApiStatus.Internal
    public static AliasedEnchantedItem of(String id, Block original, ItemGroup itemGroup) {
        return (AliasedEnchantedItem) RegistryHelper.item(id, new AliasedEnchantedItem(original, new FabricItemSettings().group(itemGroup)));
    }

    @ApiStatus.Internal
    public static AliasedEnchantedItem ofMiscFood(String id, Block original, FoodComponent foodComponent) {
        return (AliasedEnchantedItem) RegistryHelper.item(id, new AliasedEnchantedItem(original, new FabricItemSettings().group(ItemGroup.MISC).food(foodComponent)));
    }
}
