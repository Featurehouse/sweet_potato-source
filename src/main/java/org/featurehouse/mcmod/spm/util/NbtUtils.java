package org.featurehouse.mcmod.spm.util;

import net.minecraft.nbt.*;

import static net.fabricmc.fabric.api.util.NbtType.*;

public class NbtUtils {
    public static boolean notCompoundTag(NbtElement tag) {
        return tag == null || tag.getType() != COMPOUND || !(tag instanceof NbtCompound);
    }
}
