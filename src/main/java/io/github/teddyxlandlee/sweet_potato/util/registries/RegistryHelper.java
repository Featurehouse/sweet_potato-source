package io.github.teddyxlandlee.sweet_potato.util.registries;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;

public interface RegistryHelper {
    static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    //@Environment(EnvType.CLIENT)
    static SoundEvent sound(String id) {
        Identifier id2 = id(id);
        return Registry.register(Registry.SOUND_EVENT, id2, new SoundEvent(id2));
    }
}
