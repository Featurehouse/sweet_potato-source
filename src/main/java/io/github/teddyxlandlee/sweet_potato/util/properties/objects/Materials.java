package io.github.teddyxlandlee.sweet_potato.util.properties.objects;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;

public final class Materials {
    public static final Material MATERIAL_STONE = new Material.Builder(MapColor.STONE).build();
    public static final Material MATERIAL_PLANT = new FabricMaterialBuilder(MapColor.FOLIAGE).allowsMovement().destroyedByPiston().lightPassesThrough().notSolid().build();
}
