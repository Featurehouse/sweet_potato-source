package io.featurehouse.spm.resource.magicalenchantment;

import com.google.gson.*;
import io.featurehouse.spm.SPMMain;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MagicalEnchantmentLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    protected static final Identifier FABRIC_ID = new Identifier(SPMMain.MODID, "magical_enchantments");
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LogManager.getLogger();

    public MagicalEnchantmentLoader() {
        super(GSON, "spm__magical_enchantments");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {
        WeightedStatusEffect.EFFECTS.clear();
        loader.forEach((fileId, json) -> {
            JsonArray root = JsonHelper.asArray(json, fileId.toString());
            Set<WeightedStatusEffect> set = new HashSet<>();
            int i = 0;
            for (JsonElement je: root) {
                JsonObject eachObj = JsonHelper.asObject(je, "Element #" + i);
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
                ++i;
            } WeightedStatusEffect.EFFECTS.addAll(set);
        });
    }

    @Override
    public Identifier getFabricId() {
        return FABRIC_ID;
    }

}
