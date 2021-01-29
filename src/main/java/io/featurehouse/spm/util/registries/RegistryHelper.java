package io.featurehouse.spm.util.registries;

import io.featurehouse.annotation.FabricApiRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;

import static io.featurehouse.spm.SPMMain.MODID;

public interface RegistryHelper {
    static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    static Item item(String id, Item item2) {
        Identifier id2 = id(id);
        return Registry.register(Registry.ITEM, id2, item2);
    }

    static Item defaultItem(String id, @NotNull Item.Settings settings) {
        return item(id, new Item(settings));
    }

    static Block block(String id, Block block2) {
        Identifier id2 = id(id);
        return Registry.register(Registry.BLOCK, id2, block2);
    }

    static BlockItem blockItem(String id, Block block2, @NotNull Item.Settings settings) {
        Identifier id2 = id(id);
        return Registry.register(Registry.ITEM, id2, new BlockItem(block2, settings));
    }

    static <E extends BlockEntity> BlockEntityType<E> blockEntity(String id, Supplier<E> supplier, Block... blocks) {
        Identifier id2 = id(id);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, id2, BlockEntityType.Builder.create(supplier, blocks).build(null));
    }

    //@Environment(EnvType.CLIENT)
    static SoundEvent sound(String id) {
        Identifier id2 = id(id);
        return Registry.register(Registry.SOUND_EVENT, id2, new SoundEvent(id2));
    }

    static <T extends Recipe<Inventory>> RecipeType<T> recipeType(String id) {
        Identifier id2 = id(id);
        return Registry.register(Registry.RECIPE_TYPE, id2, new RecipeType<T>() {
            @Override
            public String toString() {
                return id2.toString();
            }
        });
    }

    static <S extends RecipeSerializer<?>> S recipeSerializer(String id, Supplier<S> serializerSupplier) {
        Identifier id2 = id(id);
        return Registry.register(Registry.RECIPE_SERIALIZER, id2, serializerSupplier.get());
    }

    @FabricApiRegistry
    static <H extends ScreenHandler> ScreenHandlerType<H> simpleScreenHandler(String id, ScreenHandlerRegistry.SimpleClientHandlerFactory<H> factory) {
        Identifier id2 = id(id);
        return ScreenHandlerRegistry.registerSimple(id2, factory);
    }

    @FabricApiRegistry
    static <H extends ScreenHandler> ScreenHandlerType<H> extendedScreenHandler(String id, ScreenHandlerRegistry.ExtendedClientHandlerFactory<H> factory) {
        Identifier id2 = id(id);
        return ScreenHandlerRegistry.registerExtended(id2, factory);
    }

    @FabricApiRegistry
    static Tag<Item> itemTag(String id) {
        Identifier id2 = id(id);
        return TagRegistry.item(id2);
    }

    static Identifier stat(String id, StatFormatter statFormatter) {
        Identifier id2 = id(id);
        Registry.register(Registry.CUSTOM_STAT, id2, id2);
        Stats.CUSTOM.getOrCreateStat(id2, statFormatter);
        return id2;
    }

    static Identifier stat(String id) { return stat(id, StatFormatter.DEFAULT); }
}
