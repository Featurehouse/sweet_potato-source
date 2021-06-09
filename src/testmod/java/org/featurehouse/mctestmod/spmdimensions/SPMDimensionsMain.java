package org.featurehouse.mctestmod.spmdimensions;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

import static org.featurehouse.mctestmod.spmdimensions.util.DimensionsTestRegistryHelper.*;

/**
 * @see net.minecraft.world.biome.BuiltinBiomes
 * @see net.fabricmc.fabric.mixin.biome.BuiltinBiomesAccessor
 */
public class SPMDimensionsMain implements ModInitializer {
    public static final String ID = "spm_dimensions_testmod_v0";

    public static final RegistryKey<Biome> COPIED_END_KEY;
    public static final Biome COPIED_END;

    /**
     * @see Registry
     * @see Registry#CHUNK_GENERATOR_SETTINGS_KEY
     * @see net.minecraft.util.registry.BuiltinRegistries#CHUNK_GENERATOR_SETTINGS
     */
    @Override
    public void onInitialize() {

    }

    static {
        COPIED_END_KEY = key("copied_end", Registry.BIOME_KEY);
        COPIED_END = biome("copied_end", DefaultBiomeCreator::createEndMidlands);
    }
}
