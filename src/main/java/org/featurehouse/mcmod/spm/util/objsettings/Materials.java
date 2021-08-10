package org.featurehouse.mcmod.spm.util.objsettings;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class Materials {
    public static final Material MATERIAL_STONE = new Material.Builder(MapColor.STONE_GRAY).build();
    public static final Material MATERIAL_PLANT = new FabricMaterialBuilder(MapColor.DARK_GREEN).allowsMovement().destroyedByPiston().lightPassesThrough().notSolid().build();
}
