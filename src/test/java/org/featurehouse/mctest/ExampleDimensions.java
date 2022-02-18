/*package org.featurehouse.mctest;

import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

import java.util.OptionalLong;

@SuppressWarnings("unused")
public interface ExampleDimensions {
    DimensionType DIMENSION_TYPE = DimensionType.create(
            OptionalLong.empty(),
            true,        //
            false,
            false,
            true,           //
            1.0D,
            false,
            true,
            false,       //
            true,
            false,
            20000,
            256,
            256,
            //DirectBiomeAccessType.INSTANCE,
            BlockTags.INFINIBURN_OVERWORLD.getId(),
            new Identifier("overworld"),
            0.1F
    );

     //@deprecated May NOT work
     //@see DimensionOptions#OVERWORLD
    @Deprecated
    DimensionOptions DIMENSION_OPTIONS = new DimensionOptions(
            () -> DIMENSION_TYPE, new NoiseChunkGenerator(
                    null, null, 0L, ChunkGeneratorSettings::getInstance
    ));

}
*/