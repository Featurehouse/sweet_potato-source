package io.featurehouse.spm.util;

import net.minecraft.nbt.*;

public class NbtUtils {
    public static final int BYTE_TAG     = 1;
    public static final int SHORT_TAG    = 2;
    public static final int INT_TAG      = 3;
    public static final int STRING_TAG   = 8;
    public static final int LIST_TAG     = 9;
    public static final int COMPOUND_TAG = 10;

    public static boolean notShortTag(Tag tag) {
        return tag == null || tag.getType() != SHORT_TAG || !(tag instanceof ShortTag);
    }

    public static boolean notListTag(Tag tag) {
        return tag == null || tag.getType() != LIST_TAG || !(tag instanceof ListTag);
    }

    public static boolean notCompoundTag(Tag tag) {
        return tag == null || tag.getType() != COMPOUND_TAG || !(tag instanceof CompoundTag);
    }

    public static boolean notStringTag(Tag tag) {
        return tag == null || tag.getType() != STRING_TAG || !(tag instanceof StringTag);
    }

    public static boolean notIntTag(Tag tag) {
        return tag == null || tag.getType() != INT_TAG || !(tag instanceof IntTag);
    }
}
