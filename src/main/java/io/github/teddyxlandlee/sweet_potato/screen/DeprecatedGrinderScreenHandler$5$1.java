package io.github.teddyxlandlee.sweet_potato.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import io.github.teddyxlandlee.debug.Debug;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import io.github.teddyxlandlee.sweet_potato.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import javax.annotation.Nullable;
import java.util.Objects;

@Deprecated
public class DeprecatedGrinderScreenHandler$5$1 extends SyncedGuiDescription {
    //private final Inventory inventory;
    //private PropertyDelegate propertyDelegate;
    //protected final World world;

    public DeprecatedGrinderScreenHandler$5$1(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx, PacketByteBuf buf) {
        /* Need to test: this or super */
        super(type, syncId, playerInventory, getBlockInventory(ctx, 2), null);
        this.propertyDelegate = ((GrinderBlockEntity) (Objects.requireNonNull(this.world.getBlockEntity(buf.readBlockPos())))).propertyDelegate;
    }

    public DeprecatedGrinderScreenHandler$5$1(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(type, syncId, playerInventory, inventory, propertyDelegate);
        checkSize(inventory, 2);

        WGridPanel root = (WGridPanel) this.getRootPanel();

        //this.world = playerInventory.player.world;
        root.add(WItemSlot.of(blockInventory, 0), 40, 35);
        //this.addSlot(new Slot(inventory, 0, 40, 35));
        //this.addSlot(new GrindingResultSlot(playerInventory.player, inventory, 1, 116, 35));
        root.add(new WItemSlot(
                inventory, 1, 1, 1, true
        ).setInsertingAllowed(false), 116, 35);

        root.add(new WPlayerInvPanel(playerInventory), 18, 84);
        //this.createPlayerInventory(playerInventory);

        //this.addProperties(propertyDelegate); // already added in super
        //this.addSlot(new Slot(blockInventory, 0, 40, 35));

        Debug.debug(this, "Successfully created Grinder Screen Handler by Block Entity");
    }

    public DeprecatedGrinderScreenHandler$5$1(int i, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, i, playerInventory, ScreenHandlerContext.EMPTY, packetByteBuf);
    }

    //@Override
    //public boolean canUse(PlayerEntity player) {
    //    return this.blockInventory.canPlayerUse(player);
    //}

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {  // Not void stack
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 1) {
                if (!this.insertItem(itemStack2, 3, 39, true))
                    return ItemStack.EMPTY;
                slot.onStackChanged(itemStack2, itemStack);
            } else if (index != 0) {
                if (Util.grindable(itemStack2)) {
                    if (!this.insertItem(itemStack2, 0, 1, false))
                        return ItemStack.EMPTY;
                } else if (index >= 2 && index < 29) {
                    if (!this.insertItem(itemStack2, 29, 38, false))
                        return ItemStack.EMPTY;
                } else if (index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false))
                    return ItemStack.EMPTY;
            } else if (!this.insertItem(itemStack2, 2, 38, false))
                return ItemStack.EMPTY;

            if (itemStack2.isEmpty())
                slot.setStack(ItemStack.EMPTY);
            else
                slot.markDirty();

            if (itemStack2.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Environment(EnvType.CLIENT)
    @NonMinecraftNorFabric
    public int getGrindProgress() {
        int grindTime = this.propertyDelegate.get(0);
        int grindTimeTotal = this.propertyDelegate.get(1);
        return grindTimeTotal != 0 && grindTime != 0 ? grindTime * 22 / grindTimeTotal : 0;
    }

    @NonMinecraftNorFabric
    public float getIngredientData() {
        return this.propertyDelegate.get(2) / 10.0F;
    }
}
