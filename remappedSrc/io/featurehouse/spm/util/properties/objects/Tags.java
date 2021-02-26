package io.featurehouse.spm.util.properties.objects;

import net.minecraft.tag.Tag;

public final class Tags {
    public static <E> boolean inTag(E element, Tag<E> tag) {
        return tag.contains(element);
    }
}
