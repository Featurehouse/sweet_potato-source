package org.featurehouse.mcmod.spm.util.objsettings;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class Materials {
    public static final Material MATERIAL_STONE = new Material.Builder(MaterialColor.STONE).build();
    public static final Material MATERIAL_PLANT = new FabricMaterialBuilder(MaterialColor.PLANT).noCollider().destroyOnPush().notSolidBlocking().nonSolid().build();
}
