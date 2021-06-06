package org.featurehouse.mcmod.spm.util.objsettings;

import net.minecraft.tag.Tag;

public final class Tags {
    public static <E> boolean inTag(E element, Tag<E> tag) {
        return tag.contains(element);
    }
}
