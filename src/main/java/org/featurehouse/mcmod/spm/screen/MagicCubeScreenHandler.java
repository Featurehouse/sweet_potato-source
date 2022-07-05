package org.featurehouse.mcmod.spm.screen;

import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.blocks.MagicCubeBlock;
import org.featurehouse.mcmod.spm.util.inventory.MagicCubeInputSlot;
import org.featurehouse.mcmod.spm.util.inventory.UniversalResultSlot;
import org.featurehouse.mcmod.spm.util.iprops.IntMagicCubeProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static org.featurehouse.mcmod.spm.SPMMain.*;

public class MagicCubeScreenHandler extends AbstractContainerMenu {
    protected final Inventory playerInventory;
    protected final Level world;
    protected final Container inventory;
    protected final BlockPos pos;
    private final IntMagicCubeProperties magicCubeProperties;

    protected final Slot mainFuelSlot, viceFuelSlot;

    public MagicCubeScreenHandler(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, inventory.player.level, buf.readBlockPos(), new SimpleContainer(8), new IntMagicCubeProperties.Impl());
    }

    public MagicCubeScreenHandler(int syncId, Inventory playerInventory, Level world, BlockPos pos, Container inventory, IntMagicCubeProperties properties) {
        super(SPMMain.MAGIC_CUBE_SCREEN_HANDLER_TYPE, syncId);
        this.playerInventory = playerInventory;
        this.world = world;
        this.inventory = inventory;
        this.pos = pos;
        this.magicCubeProperties = properties;
        this.addDataSlots(properties);

        this.addSlot(new MagicCubeInputSlot(inventory, 0, 56, 18));
        this.addSlot(new MagicCubeInputSlot(inventory, 1, 79, 11));
        this.addSlot(new MagicCubeInputSlot(inventory, 2, 102, 18));
        this.addSlot(new UniversalResultSlot(playerInventory.player, inventory, 3, 56, 66));
        this.addSlot(new UniversalResultSlot(playerInventory.player, inventory, 4, 79, 73));
        this.addSlot(new UniversalResultSlot(playerInventory.player, inventory, 5, 102, 66));
        mainFuelSlot = this.addSlot(new FuelSlot(PEEL, inventory, 6, 12, 32));
        viceFuelSlot = this.addSlot(new FuelSlot(POTATO_POWDER, inventory, 7, 139, 30));

        int k;
        for (k = 0; k < 3; ++k) for (int j = 0; j < 9; ++j)
            this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 99 + k * 18));
        for (k = 0; k < 9; ++k)
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 157));
    }

    @Override
    public boolean stillValid(Player player) {
        BlockState state = this.world.getBlockState(pos);
        return this.inventory.stillValid(player) &&
                state.getBlock() instanceof MagicCubeBlock && state.getValue(MagicCubeBlock.ACTIVATED);
    }

    @Environment(EnvType.CLIENT)
    public short getMainFuelTime() {
        return magicCubeProperties.getMainFuelTime();
    }

    @Environment(EnvType.CLIENT)
    public short getViceFuelTime() {
        return magicCubeProperties.getViceFuelTime();
    }

    @Override
    public ItemStack quickMoveStack(Player playerEntity, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index > 7) {
                if (this.viceFuelSlot.mayPlace(itemStack)) {
                    if (!this.moveItemStackTo(itemStack2, 7, 8, false))
                        return ItemStack.EMPTY;
                } else if (this.mainFuelSlot.mayPlace(itemStack2)) {
                    if (!this.moveItemStackTo(itemStack2, 6, 7, false))
                        return ItemStack.EMPTY;
                } else if (RAW_SWEET_POTATOES.contains(itemStack.getItem()) && itemStack.getCount() == 1) {
                    if (!this.moveItemStackTo(itemStack2, 0, 3, false))
                        return ItemStack.EMPTY;
                } else if (index < 35) {
                    if (!this.moveItemStackTo(itemStack2, 35, 44, false))
                        return ItemStack.EMPTY;
                } else if (index < 44) {
                    if (!this.moveItemStackTo(itemStack2, 8, 35, false))
                        return ItemStack.EMPTY;
                } else if (!this.moveItemStackTo(itemStack2, 8, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else { // index in 0..6
                if (!this.moveItemStackTo(itemStack2, 8, 44, true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemStack2, itemStack);
            }
            if (itemStack2.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(playerEntity, itemStack2);
        } return itemStack;
    }

    private static class FuelSlot extends Slot {
        private final Item item;

        public FuelSlot(Item item, Container inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.item = item;
        }

        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() == item;
        }

        public int getMaxStackSize() {
            return 64;
        }
    }

}
