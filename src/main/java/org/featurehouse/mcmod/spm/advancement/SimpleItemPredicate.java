package org.featurehouse.mcmod.spm.advancement;

import com.google.common.collect.ImmutableSet;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.world.item.Item;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;

@StableApi
public class SimpleItemPredicate extends ItemPredicate {
    public SimpleItemPredicate(Item item) {
        super(null, ImmutableSet.of(item), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY);
    }
}
