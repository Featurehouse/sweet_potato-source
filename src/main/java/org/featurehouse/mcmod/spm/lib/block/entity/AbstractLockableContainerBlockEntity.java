//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.featurehouse.mcmod.spm.lib.block.entity;

import org.featurehouse.mcmod.spm.util.tick.ITickable;
import org.jetbrains.annotations.ApiStatus;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@ApiStatus.Experimental
public abstract class AbstractLockableContainerBlockEntity extends BaseContainerBlockEntity implements ITickable {
    protected /*private*/ NonNullList<ItemStack> inventory;
    private final int size;

    public AbstractLockableContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.inventory = NonNullList.withSize(size, ItemStack.EMPTY);
        this.size = size;
    }

    public void saveAdditional(CompoundTag tag) {  // toTag
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.inventory);

        //return tag;
    }

    public void load(CompoundTag tag) {          // fromTag
        super.load(tag);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.inventory);
    }

    public int getContainerSize() {
        return this.size;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    @Override
    public boolean stillValid(Player player) {
        assert this.level != null;  // Stupid IDEA, let you go
        return this.level.getBlockEntity(this.worldPosition) == this && player.distanceToSqr(
                (double) this.worldPosition.getX() + 0.5D,
                (double) this.worldPosition.getY() + 0.5D,
                (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public boolean isEmpty() {
        Iterator<?> iterator = this.inventory.iterator();

        ItemStack itemStack;
        do {
            if (!iterator.hasNext())
                return true;
            itemStack = (ItemStack) iterator.next();
        } while (itemStack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.inventory, slot);
    }
}
