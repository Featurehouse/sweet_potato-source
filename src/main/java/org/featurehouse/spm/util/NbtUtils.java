package org.featurehouse.spm.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import static net.fabricmc.fabric.api.util.NbtType.COMPOUND;

public class NbtUtils {
    public static boolean notCompoundTag(NbtElement tag) {
        return tag == null || tag.getType() != COMPOUND || !(tag instanceof NbtCompound);
    }
}