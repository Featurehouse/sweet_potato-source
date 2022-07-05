package org.featurehouse.mcmod.spm.util.tag;

import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public record TagContainer<T> (Registry<T> registry,
                               TagKey<T> tagKey) {
    public static <T> TagContainer<T> register(ResourceLocation id, Registry<T> registry) {
        var tagKey = TagKey.create(registry.key(), id);
        return new TagContainer<>(registry, tagKey);
    }

    public HolderSet.Named<T> entries() {
        return registry.getOrCreateTag(tagKey);
    }

    public Stream<T> stream() {
        return entries().stream().map(Holder::value);
    }

    public boolean contains(T t) {
        return entries().stream().anyMatch(entry -> Objects.equals(t, entry.value()));
    }

    @Override
    public String toString() {
        return "TagContainer[" + registry.key().location()
                + '/' + tagKey.location() + ']';
    }
}
