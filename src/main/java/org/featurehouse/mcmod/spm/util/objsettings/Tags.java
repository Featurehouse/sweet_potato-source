package org.featurehouse.mcmod.spm.util.objsettings;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;

import java.util.*;

public interface Tags {
    static <E> Tag<E> singleton(E e) {
        return new Singleton<>(e);
    }
    static Tag<Item> singleItem(ItemConvertible item) {
        return singleton(item.asItem());
    }

    static boolean containsItemKey(ItemConvertible item, Map<Tag<Item>, ?> tagMap) {
        var i = item.asItem();
        for (Tag<Item> tag : tagMap.keySet()) {
            if (tag.contains(i)) return true;
        } return false;
    }

    record Singleton<E>(E item) implements Tag<E> {
        @Override
        public boolean contains(E entry) {
            return Objects.equals(entry, item);
        }

        @Override
        public List<E> values() {
            return Collections.singletonList(item);
        }
    }
}
