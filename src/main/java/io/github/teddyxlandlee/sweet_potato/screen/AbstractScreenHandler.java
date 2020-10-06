package io.github.teddyxlandlee.sweet_potato.screen;

import bilibili.ywsuoyi.gui.screenHandler;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.DeprecatedGrinderBlockEntity;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

@Deprecated
public abstract class AbstractScreenHandler extends screenHandler {
    public PropertyDelegate propertyDelegate;

    public AbstractScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PacketByteBuf packetByteBuf) {
        super(type, syncId, playerInventory, inventory, packetByteBuf);
        if (this.e instanceof DeprecatedGrinderBlockEntity)
            this.propertyDelegate = ((DeprecatedGrinderBlockEntity) e).propertyDelegate;
    }

    public AbstractScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(type, syncId, playerInventory, inventory, null);
    }

    @Override
    public void init() {}

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public Slot addSlot(Slot slot, RenderType type) {
        return super.addSlot(slot, type.getValue());
    }

    enum RenderType {
        NO_AUTO_RENDER(0),
        DEFAULT_SLOT(1),
        RESULT_SLOT(2);

        private final int value;

        RenderType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
