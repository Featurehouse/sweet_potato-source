package io.featurehouse.spm.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * A deprecated grinder recipe option from beta.
 * @deprecated in a very early time.
 * @since beta-1.0.0 pres
 * @author teddyxlandlee
 */
@Deprecated
public class GrinderRecipe implements Recipe<Inventory> {
    protected final Identifier id;
    protected final String group;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int grindTime;

    public GrinderRecipe(Identifier id, String group, Ingredient input, ItemStack output, int grindTime) {
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

    @Environment(EnvType.CLIENT)
    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        //return SPMMain.GRINDER_RECIPE_SERIALIZER;
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        //return SPMMain.GRINDER_RECIPE_TYPE;
        return null;
    }

    public static class Serializer extends AbstractRecipeSerializer<GrinderRecipe> {

        @Override
        public GrinderRecipe read(Identifier id, JsonObject json) {
            return null;
        }

        @Override
        public GrinderRecipe read(Identifier id, PacketByteBuf buf) {
            return null;
        }

        @Override
        public void write(PacketByteBuf buf, GrinderRecipe recipe) {

        }
    }
    @Deprecated
    public static GrinderRecipe.Serializer register_recipe_serializer(Identifier id, GrinderRecipe.Serializer serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, id, serializer);
    }
}
