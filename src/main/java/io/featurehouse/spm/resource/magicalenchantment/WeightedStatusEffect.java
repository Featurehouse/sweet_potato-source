package io.featurehouse.spm.resource.magicalenchantment;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.collection.WeightedList;

import java.util.Collection;

public class WeightedStatusEffect {
    public static final ObjectSet<WeightedStatusEffect> EFFECTS = new ObjectOpenHashSet<>();

    private final StatusEffectInstance effect;
    private final int weight;
    private final int addWithPowder;

    public WeightedStatusEffect(StatusEffectInstance effect, int weight, int addWithPowder) {
        this.effect = effect;
        this.weight = weight;
        this.addWithPowder = addWithPowder;
    }

    public int getWeight() {
        return weight;
    }

    public StatusEffectInstance getEffect() {
        return effect;
    }

    public int getAddWithPowder() {
        return addWithPowder;
    }

    public static void dump2weightedList(WeightedList<StatusEffectInstance> weightedList, Collection<WeightedStatusEffect> col, boolean withPowder) {
        for (WeightedStatusEffect wse: col) {
            weightedList.add(wse.effect, withPowder ? wse.weight + wse.addWithPowder : wse.weight);
        }
    }
}
