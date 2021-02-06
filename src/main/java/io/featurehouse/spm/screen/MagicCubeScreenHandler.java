package io.featurehouse.spm.screen;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.MagicCubeBlock;
import io.featurehouse.spm.util.inventory.MagicCubeInputSlot;
import io.featurehouse.spm.util.inventory.UniversalResultSlot;
import io.featurehouse.spm.util.properties.magiccube.IntMagicCubeProperties;
import io.featurehouse.spm.util.properties.magiccube.NullMagicCubeProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static io.featurehouse.spm.SPMMain.*;

public class MagicCubeScreenHandler extends ScreenHandler {
    protected final PlayerInventory playerInventory;
    protected final World world;
    protected final Inventory inventory;
    protected final BlockPos pos;
    private final IntMagicCubeProperties magicCubeProperties;

    protected final Slot mainFuelSlot, viceFuelSlot;

    public MagicCubeScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.world, buf.readBlockPos(), new SimpleInventory(8), new NullMagicCubeProperties());
    }

    public MagicCubeScreenHandler(int syncId, PlayerInventory playerInventory, World world, BlockPos pos, Inventory inventory, IntMagicCubeProperties properties) {
        super(SPMMain.MAGIC_CUBE_SCREEN_HANDLER_TYPE, syncId);
        this.playerInventory = playerInventory;
        this.world = world;
        this.inventory = inventory;
        this.pos = pos;
        this.magicCubeProperties = properties;
        this.addProperties(properties);

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
    public boolean canUse(PlayerEntity player) {
        BlockState state = this.world.getBlockState(pos);
        return this.inventory.canPlayerUse(player) &&
                state.getBlock() instanceof MagicCubeBlock && state.get(MagicCubeBlock.ACTIVATED);
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
    public ItemStack transferSlot(PlayerEntity playerEntity, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index > 7) {
                if (this.viceFuelSlot.canInsert(itemStack)) {
                    if (!this.insertItem(itemStack2, 7, 8, false))
                        return ItemStack.EMPTY;
                } else if (this.mainFuelSlot.canInsert(itemStack2)) {
                    if (!this.insertItem(itemStack2, 6, 7, false))
                        return ItemStack.EMPTY;
                } else if (itemStack.getItem().isIn(RAW_SWEET_POTATOES) && itemStack.getCount() == 1) {
                    if (!this.insertOne(itemStack2, 0, 3, false))
                        return ItemStack.EMPTY;
                } else if (index < 35) {
                    if (!this.insertItem(itemStack2, 35, 44, false))
                        return ItemStack.EMPTY;
                } else if (index < 44) {
                    if (!this.insertItem(itemStack2, 8, 35, false))
                        return ItemStack.EMPTY;
                } else if (!this.insertItem(itemStack2, 8, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else { // index in 0..6
                if (!this.insertItem(itemStack2, 8, 44, true))
                    return ItemStack.EMPTY;
                slot.onStackChanged(itemStack2, itemStack);
            }
            if (itemStack2.isEmpty())
                slot.setStack(ItemStack.EMPTY);
            else
                slot.markDirty();
            if (itemStack2.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot.onTakeItem(playerEntity, itemStack2);
        } return itemStack;
    }

    static class FuelSlot extends Slot {
        private final Item item;

        public FuelSlot(Item item, Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.item = item;
        }

        public boolean canInsert(ItemStack stack) {
            return stack.getItem() == item;
        }

        public int getMaxItemCount() {
            return 64;
        }
    }

    protected boolean insertOne(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        boolean bl = false;

        Slot slot2;
        ItemStack itemStack;

        if (!stack.isEmpty()) {
            int insIndex = fromLast ? endIndex - 1 : startIndex;
            while (true) {
                if (fromLast) {
                    if (insIndex < startIndex) break;
                } else if (insIndex >= endIndex) break;

                slot2 = this.slots.get(insIndex);
                itemStack = slot2.getStack();
                if (itemStack.isEmpty() && slot2.canInsert(stack)) {
                    if (stack.getCount() > slot2.getMaxItemCount())
                        slot2.setStack(stack.split(slot2.getMaxItemCount()));
                    else
                        slot2.setStack(stack.split(stack.getCount()));
                    slot2.markDirty();
                    bl = true;
                    break;
                }
                if (fromLast) {
                    --insIndex;
                } else {
                    ++insIndex;
                }
            }
        } return bl;
    }
}
