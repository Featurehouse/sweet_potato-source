package org.featurehouse.mctestmod.spmdimensions.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

import java.util.function.Supplier;

import static org.featurehouse.mctestmod.spmdimensions.SPMDimensionsMain.ID;

public interface DimensionsTestRegistryHelper {
    static Identifier id(String raw) {
        return new Identifier(ID, raw);
    }

    static Biome biome(String raw, Supplier<Biome> supplier) {
        return Registry.register(BuiltinRegistries.BIOME, id(raw), supplier.get());
    }

    static <E> RegistryKey<E> key(String raw, RegistryKey<Registry<E>> type) {
        return RegistryKey.of(type, id(raw));
    }

    static ChunkGeneratorSettings chunkGeneratorSettings(String raw, ChunkGeneratorSettings settings) {
        BuiltinRegistries.add(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, id(raw), settings);
        return settings;
    }
}
