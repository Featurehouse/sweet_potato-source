package com.github.teddyxlandlee.sweet_potato.screen;

import com.github.teddyxlandlee.annotation.Approved;
import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.github.teddyxlandlee.sweet_potato.recipe.GrinderRecipe;
import com.github.teddyxlandlee.sweet_potato.util.GrindingResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class GrinderScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;
    private final RecipeType<GrinderRecipe> recipeType;

    private GrinderScreenHandler(ScreenHandlerType<?> screenHandlerType, RecipeType<GrinderRecipe> recipeType, int syncId, PlayerInventory playerInventory) {
        this(screenHandlerType, recipeType, syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(3));
    }

    @Approved
    public GrinderScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, ExampleMod.GRINDER_RECIPE_TYPE, syncId, playerInventory);
    }

    @Approved
    public GrinderScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        this(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, ExampleMod.GRINDER_RECIPE_TYPE, syncId, playerInventory, inventory, propertyDelegate);
    }

    private GrinderScreenHandler(ScreenHandlerType<?> screenHandlerType, RecipeType<GrinderRecipe> recipeType, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(screenHandlerType, syncId);
        this.recipeType = recipeType;
        checkSize(inventory, 2);
        checkDataCount(propertyDelegate, 3);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new GrindingResultSlot(playerInventory.player, inventory, 1, 116, 35));

        int k;
        for (k = 0; k < 3; ++k) {   // Main Inventory
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {   // HotBar
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addProperties(propertyDelegate);
    }


    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        if (this.inventory instanceof RecipeInputProvider)
            ((RecipeInputProvider) this.inventory).provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.inventory.clear();
    }

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 1;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 2;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    /*@Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if (index == 1) {
                if (!this.insertItem(itemStack1, Integer.MAX_VALUE, Integer.MAX_VALUE, true))
                    return ItemStack.EMPTY;
            }
        }
    }*/
}
