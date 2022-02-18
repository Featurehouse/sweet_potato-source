package org.featurehouse.mcmod.spm.util.tag;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;

import java.util.Objects;
import java.util.stream.Stream;

public record TagContainer<T> (Registry<T> registry,
                               TagKey<T> tagKey) {
    public static <T> TagContainer<T> register(Identifier id, Registry<T> registry) {
        var tagKey = TagKey.intern(registry.getKey(), id);
        return new TagContainer<>(registry, tagKey);
    }

    public RegistryEntryList.Named<T> entries() {
        return registry.getOrCreateEntryList(tagKey);
    }

    public Stream<T> stream() {
        return entries().stream().map(RegistryEntry::value);
    }

    public boolean contains(T t) {
        return entries().stream().anyMatch(entry -> Objects.equals(t, entry.value()));
    }

    @Override
    public String toString() {
        return "TagContainer[" + registry.getKey().getValue()
                + '/' + tagKey.id() + ']';
    }
}
