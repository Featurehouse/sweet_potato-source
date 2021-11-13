package org.featurehouse.mcmod.spm.screen;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.recipe.SeedUpdatingRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        this.list = this.world.getRecipeManager().listAllOfType(SPMMain.SEED_UPDATING_RECIPE_TYPE);
    }

    protected boolean canUse(@NotNull BlockState state) {
        return state.isOf(SPMMain.SEED_UPDATER);
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, SPMMain.SEED_UPDATER);
    }

    @Override
    public void updateResult() {
        List<SeedUpdatingRecipe> list1 = this.world.getRecipeManager().getAllMatches(
                SPMMain.SEED_UPDATING_RECIPE_TYPE, this.input, this.world
        );
        if (list1.isEmpty())
            this.output.setStack(0, ItemStack.EMPTY);
        else {
            this.recipe = list1.get(0);
            ItemStack itemStack = this.recipe.craft(this.input);
            this.output.setLastRecipe(recipe);
            this.output.setStack(0, itemStack);
        }
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return this.recipe != null && this.recipe.matches(this.input, this.world);
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, @NotNull ItemStack stack) {
        //stack.onCraft(player.world, player, stack.getCount());

        this.output.unlockLastRecipe(player);
        this.putStack(0);
        this.putStack(1);
        //output.markDirty();
        this.context.run((world1, blockPos) -> {
            /*world1.syncWorldEvent(1044, blockPos, 8844110));*/
            world1.playSound(null, blockPos, SPMMain.AGROFORESTRY_TABLE_FINISH, SoundCategory.BLOCKS, 1.0F, world1.getRandom().nextFloat() * 0.1F + 0.9F);
        });
        player.incrementStat(SPMMain.CROP_UPGRADED);
        //return stack;
    }

    private void putStack(int i) {
        // method_29539
        ItemStack itemStack = this.input.getStack(i);
        itemStack.decrement(1);
        this.input.setStack(i, itemStack);
    }

    @Override
    protected boolean isUsableAsAddition(ItemStack itemStack) {
        // shouldQuickMoveToAdditionalSlot
        return this.list.stream().anyMatch(seedUpdatingRecipe -> seedUpdatingRecipe.method_30029(itemStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
    }
}
