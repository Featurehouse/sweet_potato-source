package org.featurehouse.spm.resource.magicalenchantment;

import com.google.gson.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class MagicalEnchantmentSerializer implements JsonDeserializer<Collection<WeightedStatusEffect>> {
    private static final Logger LOGGER = LogManager.getLogger("SPM Magical Enchantment Serializer");

    @Override
    public Collection<WeightedStatusEffect> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray root = JsonHelper.asArray(json, "magical enchantment");
        Set<WeightedStatusEffect> set = new HashSet<>();
        for (JsonElement je: root) {
            JsonObject eachObj = JsonHelper.asObject(je, "a weighted status effect");
            Identifier id = new Identifier(JsonHelper.getString(eachObj, "id"));
            if (!Registry.STATUS_EFFECT.getIds().contains(id)) {
                LOGGER.error(new JsonSyntaxException("Invalid status effect id: " + id.toString()));
                continue;
            } StatusEffect effect = Registry.STATUS_EFFECT.get(id);
            int duration = JsonHelper.getInt(eachObj, "duration", 0);
            int amplifier = JsonHelper.getInt(eachObj, "amplifier", 0);
            int weight = JsonHelper.getInt(eachObj, "weight", 1);
            int addWithPowder = JsonHelper.getInt(eachObj, "powder_adds", 10);
            set.add(new WeightedStatusEffect(new StatusEffectInstance(effect, duration, amplifier), weight, addWithPowder));
        } return set;
    }
}
