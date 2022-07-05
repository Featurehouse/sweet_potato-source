package org.featurehouse.mcmod.spm.resource.magicalenchantment;

import com.google.gson.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.featurehouse.mcmod.spm.SPMMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MagicalEnchantmentLoader extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {
    protected static final ResourceLocation FABRIC_ID = new ResourceLocation(SPMMain.MODID, "magical_enchantments");
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerFactory.getLogger("MagicalEnchantmentLoader");

    public MagicalEnchantmentLoader() {
        super(GSON, "spm__magical_enchantments");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> loader, ResourceManager manager, ProfilerFiller profiler) {
        WeightedStatusEffect.EFFECTS.clear();
        loader.forEach((fileId, json) -> {
            JsonArray root = GsonHelper.convertToJsonArray(json, fileId.toString());
            Set<WeightedStatusEffect> set = new HashSet<>();
            int i = 0;
            for (JsonElement je: root) {
                JsonObject eachObj = GsonHelper.convertToJsonObject(je, "Element #" + i);
                ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(eachObj, "id"));
                if (!Registry.MOB_EFFECT.keySet().contains(id)) {
                    LOGGER.error("Invalid status effect id: " + id);
                    continue;
                } MobEffect effect = Registry.MOB_EFFECT.get(id);
                int duration = GsonHelper.getAsInt(eachObj, "duration", 0);
                int amplifier = GsonHelper.getAsInt(eachObj, "amplifier", 0);
                int weight = GsonHelper.getAsInt(eachObj, "weight", 1);
                int addWithPowder = GsonHelper.getAsInt(eachObj, "powder_adds", 10);
                set.add(new WeightedStatusEffect(new MobEffectInstance(effect, duration, amplifier), weight, addWithPowder));
                ++i;
            } WeightedStatusEffect.EFFECTS.addAll(set);
        });
    }

    @Override
    public ResourceLocation getFabricId() {
        return FABRIC_ID;
    }

}
