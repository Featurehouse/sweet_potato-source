package io.featurehouse.spm.advancement;

import net.minecraft.item.Item;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;

public class SimpleItemPredicate extends ItemPredicate {
    public SimpleItemPredicate(Item item) {
        super(null, item, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
    }
}
