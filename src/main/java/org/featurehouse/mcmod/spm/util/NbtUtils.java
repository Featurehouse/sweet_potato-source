package org.featurehouse.mcmod.spm.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.ApiStatus;

import static net.fabricmc.fabric.api.util.NbtType.COMPOUND;

@ApiStatus.Experimental
public class NbtUtils {
    public static boolean notCompoundTag(NbtElement tag) {
        return tag == null || tag.getType() != COMPOUND || !(tag instanceof NbtCompound);
    }
}