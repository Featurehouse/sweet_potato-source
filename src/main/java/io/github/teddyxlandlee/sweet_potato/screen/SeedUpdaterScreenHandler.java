package io.github.teddyxlandlee.sweet_potato.screen;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.recipe.SeedUpdatingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SeedUpdaterScreenHandler extends ForgingScreenHandler {
    private final World world; // field_25385
    @Nullable
    private SeedUpdatingRecipe recipe; // field_25386
    private final List<SeedUpdatingRecipe> list; // field_25668

    public SeedUpdaterScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public SeedUpdaterScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE, syncId, inventory, context);
        this.world = inventory.player.world;
        this.list = this.world.getRecipeManager().method_30027(SPMMain.SEED_UPDATING_RECIPE_TYPE);
    }

    protected boolean canUse(@Nonnull BlockState state) {
        return state.isOf(SPMMain.SEED_UPDATER);
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, SPMMain.SEED_UPDATER);
    }

    //@Override
    public void updateResult() {
        List<SeedUpdatingRecipe> list1 = this.world.getRecipeManager().getAllMatches(
                SPMMain.SEED_UPDATING_RECIPE_TYPE, this.input, this.world
        );
        if (list1.isEmpty())
            this.output.setStack(0, ItemStack.EMPTY);
        else {
            this.recipe = list1.get(0);
            ItemStack itemStack = this.recipe.craft(this.input);
            this.output.setStack(0, itemStack);
        }
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return this.recipe != null && this.recipe.matches(this.input, this.world);
    }

    //@Override
    protected ItemStack onTakeOutput(PlayerEntity player, ItemStack stack) {
        this.putStack(0);
        this.putStack(1);
        this.context.run((world1, blockPos) -> {
            world1.syncWorldEvent(1044/*?*/, blockPos, 0/*?*/);
        });
        return stack;
    }

    private void putStack(int i) {
        // method_29539
        ItemStack itemStack = this.input.getStack(i);
        itemStack.decrement(1);
        this.input.setStack(i, itemStack);
    }

    @Override
    protected boolean method_30025(ItemStack itemStack) {
        return this.list.stream().anyMatch(seedUpdatingRecipe -> seedUpdatingRecipe.method_30029(itemStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
    }
}
