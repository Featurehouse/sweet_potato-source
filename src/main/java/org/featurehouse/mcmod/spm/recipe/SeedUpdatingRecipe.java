package org.featurehouse.mcmod.spm.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import org.featurehouse.mcmod.spm.SPMMain;
import org.jetbrains.annotations.NotNull;

public record SeedUpdatingRecipe(Identifier id, Ingredient base,
                                 Ingredient addition,
                                 ItemStack result) implements Recipe<Inventory> {

    @Override
    public boolean matches(@NotNull Inventory inv, World world) {
        return this.base.test(inv.getStack(0)) && this.addition.test(inv.getStack(1));
    }

    @Override
    public ItemStack craft(Inventory inv) {
        ItemStack itemStack = this.result.copy();
        NbtCompound compoundTag = inv.getStack(0).getNbt();
        if (compoundTag != null) {
            itemStack.setNbt(compoundTag.copy());
        }

        return itemStack;
    }

    @Environment(EnvType.CLIENT) @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Environment(EnvType.CLIENT) @Deprecated
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(SPMMain.SEED_UPDATER);
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SPMMain.SEED_UPDATING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SPMMain.SEED_UPDATING_RECIPE_TYPE;
    }

    public boolean method_30029(ItemStack itemStack) {
        return this.addition.test(itemStack);
    }

    public static class Serializer implements RecipeSerializer<SeedUpdatingRecipe> {

        @Override
        public SeedUpdatingRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "addition"));
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new SeedUpdatingRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        @Override
        public SeedUpdatingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            Ingredient ingredient2 = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new SeedUpdatingRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        @Override
        public void write(PacketByteBuf buf, @NotNull SeedUpdatingRecipe recipe) {
            recipe.base.write(buf);
            recipe.addition.write(buf);
            buf.writeItemStack(recipe.result);
        }
    }
}