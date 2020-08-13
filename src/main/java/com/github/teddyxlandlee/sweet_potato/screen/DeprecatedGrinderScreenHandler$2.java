package com.github.teddyxlandlee.sweet_potato.screen;

import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.github.teddyxlandlee.sweet_potato.util.GrindingResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

@Deprecated
public class DeprecatedGrinderScreenHandler$2 extends AbstractRecipeScreenHandler<Inventory> {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;

    public DeprecatedGrinderScreenHandler$2(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(2));
    }

    protected DeprecatedGrinderScreenHandler$2(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(type, syncId);
        checkSize(inventory, 2);
        checkDataCount(propertyDelegate, 2);
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

    public DeprecatedGrinderScreenHandler$2(int syncId, PlayerInventory playerInventory) {
        this(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, syncId, playerInventory);
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {

    }

    @Override
    public void clearCraftingSlots() {

    }

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return false;
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return 0;
    }

    @Override
    public int getCraftingHeight() {
        return 0;
    }

    @Override
    public int getCraftingSlotCount() {
        return 0;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
