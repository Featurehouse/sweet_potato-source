package org.featurehouse.mcmod.spm.util.effects;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class StatusEffectInstances {
    private static final Logger LOGGER = LoggerFactory.getLogger("SPM Status Effect Manager");

    /**
     * For Enchanted Sweet Potatoes only. Not compatible with ordinal ones.
     */
    @Nullable   // null: empty status
    public static MobEffectInstance readNbt(CompoundTag tag) {
        if (!tag.contains("id", NbtType.STRING)) return null;
        String raw = tag.getString("id");
        ResourceLocation id = new ResourceLocation(raw);
        if (!Registry.MOB_EFFECT.keySet().contains(id)) {
            LOGGER.error("Cannot apply status effect: {}", raw);
            return null;
        } MobEffect effect = Registry.MOB_EFFECT.get(id);
        int duration = tag.getInt("duration"), amplifier = tag.getInt("amplifier"); // defaulted as 0
        return new MobEffectInstance(effect, duration, amplifier);
    }

    /**
     * For Enchanted Sweet Potatoes only. Not compatible with ordinal ones.
     */
    public static CompoundTag writeNbt(MobEffectInstance effect) {
        CompoundTag tag = new CompoundTag();
        MobEffect statusEffect = effect.getEffect();
        if (Registry.MOB_EFFECT.stream().noneMatch(statusEffect1 -> statusEffect1 == statusEffect)) {
            LOGGER.error("Cannot write status effect: {}", statusEffect.getDisplayName());
            return tag;
        }
        ResourceLocation id = Registry.MOB_EFFECT.getKey(statusEffect);
        Objects.requireNonNull(id);

        tag.putString("id", id.toString());
        tag.putInt("duration", effect.getDuration());
        tag.putInt("amplifier", effect.getAmplifier());
        return tag;
    }
}
