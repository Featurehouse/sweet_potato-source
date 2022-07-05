package org.featurehouse.mcmod.spm.util.registries;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.featurehouse.mcmod.spm.util.tag.TagContainer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static org.featurehouse.mcmod.spm.SPMMain.MODID;

public interface RegistryHelper {
    static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }

    static Item item(String id, Item item2) {
        ResourceLocation id2 = id(id);
        return Registry.register(Registry.ITEM, id2, item2);
    }

    static Item defaultItem(String id, @NotNull Item.Properties settings) {
        return item(id, new Item(settings));
    }

    static Block block(String id, Block block2) {
        ResourceLocation id2 = id(id);
        return Registry.register(Registry.BLOCK, id2, block2);
    }

    static BlockItem blockItem(String id, Block block2, @NotNull Item.Properties settings) {
        ResourceLocation id2 = id(id);
        return Registry.register(Registry.ITEM, id2, new BlockItem(block2, settings));
    }

    static <E extends BlockEntity> BlockEntityType<E> blockEntity(String id, FabricBlockEntityTypeBuilder.Factory<E> supplier, Block... blocks) {
        ResourceLocation id2 = id(id);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, id2, FabricBlockEntityTypeBuilder.create(supplier, blocks).build(null));
    }

    //@Environment(EnvType.CLIENT)
    static SoundEvent sound(String id) {
        ResourceLocation id2 = id(id);
        return Registry.register(Registry.SOUND_EVENT, id2, new SoundEvent(id2));
    }

    static <T extends Recipe<Container>> RecipeType<T> recipeType(String id) {
        ResourceLocation id2 = id(id);
        return Registry.register(Registry.RECIPE_TYPE, id2, new RecipeType<T>() {
            @Override
            public String toString() {
                return id2.toString();
            }
        });
    }

    static <S extends RecipeSerializer<?>> S recipeSerializer(String id, Supplier<S> serializerSupplier) {
        ResourceLocation id2 = id(id);
        return Registry.register(Registry.RECIPE_SERIALIZER, id2, serializerSupplier.get());
    }

    //@FabricApiRegistry
    static <H extends AbstractContainerMenu> MenuType<H> simpleScreenHandler(String id, ScreenHandlerRegistry.SimpleClientHandlerFactory<H> factory) {
        ResourceLocation id2 = id(id);
        return ScreenHandlerRegistry.registerSimple(id2, factory);
    }

    //@FabricApiRegistry
    static <H extends AbstractContainerMenu> MenuType<H> extendedScreenHandler(String id, ScreenHandlerRegistry.ExtendedClientHandlerFactory<H> factory) {
        ResourceLocation id2 = id(id);
        return ScreenHandlerRegistry.registerExtended(id2, factory);
    }

    //@FabricApiRegistry
    static TagContainer<Item> itemTag(String id) {
        ResourceLocation id2 = id(id);
        return TagContainer.register(id2, Registry.ITEM);
    }

    static ResourceLocation stat(String id, StatFormatter statFormatter) {
        ResourceLocation id2 = id(id);
        Registry.register(Registry.CUSTOM_STAT, id2, id2);
        Stats.CUSTOM.get(id2, statFormatter);
        return id2;
    }

    static ResourceLocation stat(String id) { return stat(id, StatFormatter.DEFAULT); }
}
