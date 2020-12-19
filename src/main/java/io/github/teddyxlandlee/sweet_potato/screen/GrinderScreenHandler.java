package io.github.teddyxlandlee.sweet_potato.screen;

import io.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.util.inventory.GrindingResultSlot;
import io.github.teddyxlandlee.sweet_potato.util.Util;
import io.github.teddyxlandlee.sweet_potato.util.properties.grinder.IntGrinderProperties;
import io.github.teddyxlandlee.sweet_potato.util.properties.grinder.NullGrinderProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GrinderScreenHandler extends ScreenHandler {
    @Deprecated
    Logger logger = LogManager.getLogger();

    private final Inventory inventory;
    private final IntGrinderProperties properties;

    protected World world;

    @Deprecated
    public GrinderScreenHandler(int i, PlayerInventory playerInventory, @Deprecated PacketByteBuf buf) {
        this(i, playerInventory, playerInventory.player.world, new SimpleInventory(2), new NullGrinderProperties());
    }

    /**
     * From: Registry
     */
    public GrinderScreenHandler(int i, PlayerInventory playerInventory) {
        this(i, playerInventory, playerInventory.player.world, new SimpleInventory(2), new NullGrinderProperties());
    }

    /**
     * From: Grinder Block Entity
     */
    public GrinderScreenHandler(int syncId, PlayerInventory playerInventory, World world, Inventory inventory, IntGrinderProperties properties) {
        super(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, syncId);
        this.inventory = inventory;
        this.properties = properties;
        this.addProperties(properties);
        this.world = world;

        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new GrindingResultSlot(playerInventory.player, inventory, 1, 116, 35));

        this.createPlayerInventory(playerInventory);
    }

    /*protected GrinderScreenHandler(int syncId, PlayerInventory playerInventory, World world, Inventory inventory, GrinderPropertiesAccessor accessor, PacketByteBuf buf) {
        super(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, syncId);
        //this.playerInventory = playerInventory;
        this.inventory = inventory;
        if (buf != null) {
            BlockEntity e = playerInventory.player.world.getBlockEntity(buf.readBlockPos());
            if (e instanceof GrinderBlockEntity) {
                this.propertiesAccessor = ((GrinderBlockEntity) e).propertiesAccessor;
                Debug.debug(this, "propertiesAccessor created by buf");
            } else {
                this.propertiesAccessor = null;
                logger.fatal("non-grinder block entity found", new UnsupportedOperationException("non-grinder block entity at buf"));
            }
        } else {
            this.propertiesAccessor = accessor;
            Debug.debug(this, "propertiesAccessor created by block entity");
        }
        inventory.onOpen(playerInventory.player);
        this.addProperties(accessor);
        this.world = world;

        this.addSlot(new Slot(inventory, 0, 40, 35));
        this.addSlot(new GrindingResultSlot(playerInventory.player, inventory, 1, 116, 35));

        this.createPlayerInventory(playerInventory);
        //TODO: Make client-side block entity available
    }*/

    @NonMinecraftNorFabric
    private void createPlayerInventory(PlayerInventory playerInventory) {
        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

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
    public int getGrindProgress() {
        int grindTime = properties.getGrindTime();
        int grindTimeTotal = properties.getGrindTimeTotal();
        return grindTimeTotal != 0 && grindTime != 0 ? grindTime * 22 / grindTimeTotal : 0;
    }

    @Environment(EnvType.CLIENT)
    public double getIngredientData() {
        return properties.getIngredientData();
    }
}
