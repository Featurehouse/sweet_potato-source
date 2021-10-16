package org.featurehouse.mcmod.spm.advancement;

import com.google.common.collect.ImmutableSet;
import net.minecraft.item.Item;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;

@StableApi
public class SimpleItemPredicate extends ItemPredicate {
    public SimpleItemPredicate(Item item) {
        super(null, ImmutableSet.of(item), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
    }
}