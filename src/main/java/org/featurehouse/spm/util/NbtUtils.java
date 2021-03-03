package org.featurehouse.spm.util;

import net.minecraft.nbt.*;

import static net.fabricmc.fabric.api.util.NbtType.*;

public class NbtUtils {
    public static boolean notCompoundTag(Tag tag) {
        return tag == null || tag.getType() != COMPOUND || !(tag instanceof CompoundTag);
    }
}
