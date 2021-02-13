package io.featurehouse.spm.util;

import net.minecraft.nbt.*;

import static net.fabricmc.fabric.api.util.NbtType.*;

public class NbtUtils {

    public static boolean notShortTag(Tag tag) {
        return tag == null || tag.getType() != SHORT || !(tag instanceof ShortTag);
    }

    public static boolean notListTag(Tag tag) {
        return tag == null || tag.getType() != LIST || !(tag instanceof ListTag);
    }

    public static boolean notCompoundTag(Tag tag) {
        return tag == null || tag.getType() != COMPOUND || !(tag instanceof CompoundTag);
    }

    public static boolean notStringTag(Tag tag) {
        return tag == null || tag.getType() != STRING || !(tag instanceof StringTag);
    }

    public static boolean notIntTag(Tag tag) {
        return tag == null || tag.getType() != INT || !(tag instanceof IntTag);
    }
}