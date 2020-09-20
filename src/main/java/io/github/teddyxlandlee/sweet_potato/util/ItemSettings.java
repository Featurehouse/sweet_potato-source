package io.github.teddyxlandlee.sweet_potato.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;

public final class ItemSettings {
    public static final Item.Settings UNCDEC;   // Uncommon Decorations

    private ItemSettings() {}

    static {
        UNCDEC = new Item.Settings()
                .group(ItemGroup.DECORATIONS)
                .rarity(Rarity.UNCOMMON);
    }
}