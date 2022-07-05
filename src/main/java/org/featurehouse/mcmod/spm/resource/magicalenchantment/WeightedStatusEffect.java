package org.featurehouse.mcmod.spm.resource.magicalenchantment;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.behavior.ShufflingList;

public record WeightedStatusEffect(MobEffectInstance effect,
                                   int weight,
                                   int addWithPowder) {
    static final ObjectSet<WeightedStatusEffect> EFFECTS = new ObjectOpenHashSet<>();

    public static void dump2weightedList(ShufflingList<MobEffectInstance> weightedList, Collection<WeightedStatusEffect> col, boolean withPowder) {
        for (WeightedStatusEffect wse : col) {
            weightedList.add(wse.effect, withPowder ? wse.weight + wse.addWithPowder : wse.weight);
        }
    }

    public static void dump2weightedList(ShufflingList<MobEffectInstance> weightedList, boolean withPowder) {
        dump2weightedList(weightedList, EFFECTS, withPowder);
    }
}
