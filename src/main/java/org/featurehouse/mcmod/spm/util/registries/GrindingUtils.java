package org.featurehouse.mcmod.spm.util.registries;

import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.featurehouse.mcmod.spm.util.tag.TagContainer;
import org.featurehouse.mcmod.spm.util.tag.TagLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.OptionalDouble;

@StableApi  // Maybe not since 2.0!
public final class GrindingUtils {
    public static IngredientDataMap ingredientDataMap() {
        return IngredientDataMap.INSTANCE;
    }

    private GrindingUtils() {}

    public static void registerGrindableItem(double ingredientDataAdded, @NotNull ItemConvertible item) {
        Objects.requireNonNull(item, "item");
        ingredientDataMap().put(item.asItem(), ingredientDataAdded);
    }

    public static void registerGrindableTag(double ingredientDataAdded, @NotNull TagContainer<Item> tagContainer) {
        Objects.requireNonNull(tagContainer, "tagContainer");
        ingredientDataMap().put(tagContainer, ingredientDataAdded);
    }

    public static boolean grindable(@Nullable ItemStack itemStack) {
        if (itemStack == null)
            return false;
        return grindable(itemStack.getItem());
    }

    public static boolean grindable(@Nullable ItemConvertible item) {
        if (item == null)
            return false;
        return ingredientDataMap().containsItem(item.asItem());
    }

    public record IngredientDataMap(Object2DoubleMap<TagLike<Item>> map) {
        private static final IngredientDataMap INSTANCE = new IngredientDataMap();

        public IngredientDataMap() {
            this(new Object2DoubleLinkedOpenHashMap<>());
        }

        public double put(Item key, double value) {
            return map.put(TagLike.asItem(key), value);
        }

        public double put(TagContainer<Item> key, double value) {
            return map.put(TagLike.asTag(key), value);
        }

        private OptionalDouble get0(Item key) {
            for (var entry : map.object2DoubleEntrySet()) {
                if (entry.getKey().contains(key))
                    return OptionalDouble.of(entry.getDoubleValue());
            } return OptionalDouble.empty();
        }

        public double getDouble(Item key) {
            return get0(key).orElse(map.defaultReturnValue());
        }

        public boolean containsItem(Item key) {
            return get0(key).isPresent();
        }

        @Override
        public String toString() {
            return map.toString();
        }
    }

    /**
     * @deprecated this {@link Tag tag} is a completely different thing from {@link TagContainer}
     * or {@link net.minecraft.tag.TagKey} since 22w06a.
     */
    @Deprecated(forRemoval = true)
    public static void registerGrindableItems(int ingredientDataAdded, @NotNull Tag<Item> tag) {
        for (Item item: tag.values())
            registerGrindableItem(ingredientDataAdded, item);
    }
}
