package org.featurehouse.spm.recipe;

import org.featurehouse.spm.SPMMain;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

public class SeedUpdatingRecipe implements Recipe<Inventory> {
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;
    private final Identifier id;

    public SeedUpdatingRecipe(Identifier id, Ingredient base, Ingredient addition, ItemStack result) {
        this.base = base;
        this.addition = addition;
        this.result = result;
        this.id = id;
    }

    //@Override
    public boolean matches(@NotNull Inventory inv, World world) {
        return this.base.test(inv.getStack(0)) && this.addition.test(inv.getStack(1));
    }

    @Override
    public ItemStack craft(Inventory inv) {
        ItemStack itemStack = this.result.copy();
        CompoundTag compoundTag = inv.getStack(0).getTag();
        if (compoundTag != null) {
            itemStack.setTag(compoundTag.copy());
        }

        return itemStack;
    }

    @Environment(EnvType.CLIENT)//@Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    //@Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(SPMMain.SEED_UPDATER);
    }

    //@Override
    public Identifier getId() {
        return this.id;
    }

    //@Override
    public RecipeSerializer<?> getSerializer() {
        return SPMMain.SEED_UPDATING_RECIPE_SERIALIZER;
    }

    //@Override
    public RecipeType<?> getType() {
        return SPMMain.SEED_UPDATING_RECIPE_TYPE;
    }

    public boolean method_30029(ItemStack itemStack) {
        return this.addition.test(itemStack);
    }

    public static class Serializer extends AbstractRecipeSerializer<SeedUpdatingRecipe> {

        //@Override
        public SeedUpdatingRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "addition"));
            ItemStack itemStack = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new SeedUpdatingRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        //@Override
        public SeedUpdatingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            Ingredient ingredient2 = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new SeedUpdatingRecipe(identifier, ingredient, ingredient2, itemStack);
        }

        //@Override
        public void write(PacketByteBuf buf, @NotNull SeedUpdatingRecipe recipe) {
            recipe.base.write(buf);
            recipe.addition.write(buf);
            buf.writeItemStack(recipe.result);
        }
    }
}
