package com.github.teddyxlandlee.sweet_potato.recipe;

import com.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GrinderRecipe implements Recipe<Inventory> {
    //protected final RecipeType<?> type;
    protected final Identifier id;
    protected final String group;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int grindTime;

    public GrinderRecipe(/*RecipeType<?> type, */Identifier id, @Nullable String group, Ingredient input, ItemStack output, int grindTime) {
        //this.type = type;
        this.id = id;
        this.group = group;
        this.input = input;
        this.output = output;
        this.grindTime = grindTime;
    }


    @Override
    public boolean matches(Inventory inv, World world) {
        return this.input.test(inv.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return this.output.copy();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public String getGroup() {
        return this.group;
    }

    @NonMinecraftNorFabric
    public int getGrindTime() {
        return this.grindTime;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ExampleMod.GRINDER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ExampleMod.GRINDER_RECIPE_TYPE;
    }

    public static class Serializer extends AbstractRecipeSerializer<GrinderRecipe> {
        @Override
        public GrinderRecipe read(Identifier id, JsonObject json) {
            JsonElement jsonElement = JsonHelper.hasArray(json, "ingredient") ? JsonHelper.getArray(json, "ingredient") : JsonHelper.getObject(json, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonElement);
            Identifier resultId = new Identifier(JsonHelper.getString(json, "result", "sweet_potato:potato_powder"));
            int resultCount = JsonHelper.getInt(json, "result-count", 2);
            ItemStack result = new ItemStack(Registry.ITEM.getOrEmpty(resultId).orElseThrow(
                    () -> new IllegalStateException("[com.github.teddyxlandlee.recipe.GrinderRecipe] Item does not exist")
            ), resultCount);
            int time = JsonHelper.getInt(json, "time", 200);

            return new GrinderRecipe(id, null, ingredient, result, time);
        }

        @Override
        public GrinderRecipe read(Identifier id, PacketByteBuf buf) {
            String group = buf.readString(32767);   // ?
            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack itemStack = buf.readItemStack();
            int time = buf.readVarInt();
            return new GrinderRecipe(id, group, ingredient, itemStack, time);
        }

        @Override
        public void write(PacketByteBuf buf, GrinderRecipe recipe) {
            buf.writeString(recipe.group);
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
            buf.writeVarInt(recipe.grindTime);
        }

    }
    public static GrinderRecipe.Serializer register_recipe_serializer(Identifier id, GrinderRecipe.Serializer serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, id, serializer);
    }
}
