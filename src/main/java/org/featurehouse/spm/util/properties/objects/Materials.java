package org.featurehouse.spm.util.properties.objects;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

public final class Materials {
    public static final Material MATERIAL_STONE = new Material.Builder(MaterialColor.STONE).build();
    public static final Material MATERIAL_PLANT = new FabricMaterialBuilder(MaterialColor.FOLIAGE).allowsMovement().destroyedByPiston().lightPassesThrough().notSolid().build();
}
