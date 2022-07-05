package org.featurehouse.mcmod.spm.mixin.acc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.item.Item;

@Mixin(Parrot.class)
public interface ParrotEntityAccessor {
    @Accessor("TAMING_INGREDIENTS")
    @Mutable
    static Set<Item> getTamingIngredients() {
        throw new AssertionError("Mixin");
    }
}
