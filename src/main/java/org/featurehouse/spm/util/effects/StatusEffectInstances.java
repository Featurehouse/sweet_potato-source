package org.featurehouse.spm.util.effects;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class StatusEffectInstances {
    private static final Logger LOGGER = LogManager.getLogger("SPM Status Effect Manager");

    /**
     * For Enchanted Sweet Potatoes only. Not compatible with ordinal ones.
     */
    @Nullable   // null: empty status
    public static StatusEffectInstance readNbt(CompoundTag tag) {
        if (!tag.contains("id", NbtType.STRING)) return null;
        String raw = tag.getString("id");
        Identifier id = new Identifier(raw);
        if (!Registry.STATUS_EFFECT.getIds().contains(id)) {
            LOGGER.error("Cannot apply status effect: {}", raw);
            return null;
        } StatusEffect effect = Registry.STATUS_EFFECT.get(id);
        int duration = tag.getInt("duration"), amplifier = tag.getInt("amplifier"); // defaulted as 0
        return new StatusEffectInstance(effect, duration, amplifier);
    }

    /**
     * For Enchanted Sweet Potatoes only. Not compatible with ordinal ones.
     */
    public static CompoundTag writeNbt(StatusEffectInstance effect) {
        CompoundTag tag = new CompoundTag();
        StatusEffect statusEffect = effect.getEffectType();
        if (Registry.STATUS_EFFECT.stream().noneMatch(statusEffect1 -> statusEffect1 == statusEffect)) {
            LOGGER.error("Cannot write status effect: {}", statusEffect.getName());
            return tag;
        }
        Identifier id = Registry.STATUS_EFFECT.getId(statusEffect);
        assert id != null;  // Stupid, STATUS_EFFECT is instance of DefaultedRegistry,
                            // getId(StatusEffect) is annotated as @NotNull
        tag.putString("id", id.toString());
        tag.putInt("duration", effect.getDuration());
        tag.putInt("amplifier", effect.getAmplifier());
        return tag;
    }
}
