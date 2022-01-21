package org.featurehouse.mcmod.spm.resource.magicalenchantment;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.collection.WeightedList;

import java.util.Collection;

public record WeightedStatusEffect(StatusEffectInstance effect,
                                   int weight,
                                   int addWithPowder) {
    static final ObjectSet<WeightedStatusEffect> EFFECTS = new ObjectOpenHashSet<>();

    public static void dump2weightedList(WeightedList<StatusEffectInstance> weightedList, Collection<WeightedStatusEffect> col, boolean withPowder) {
        for (WeightedStatusEffect wse : col) {
            weightedList.add(wse.effect, withPowder ? wse.weight + wse.addWithPowder : wse.weight);
        }
    }

    public static void dump2weightedList(WeightedList<StatusEffectInstance> weightedList, boolean withPowder) {
        dump2weightedList(weightedList, EFFECTS, withPowder);
    }
}
