//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package bilibili.ywsuoyi.gui;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public abstract class YwsuoyiScreenHandler extends ScreenHandler {
    public Inventory inventory;
    public Inventory playerInv;
    public BlockEntity e;
    public DefaultedList<Integer> autorender;

    public YwsuoyiScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PacketByteBuf packetByteBuf) {
        super(type, syncId);
        this.autorender = DefaultedList.of();
        this.inventory = inventory;
        this.playerInv = playerInventory;
        if (packetByteBuf != null) {
            this.e = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());
        }

        inventory.onOpen(playerInventory.player);
        this.init();
    }

    public YwsuoyiScreenHandler(ScreenHandlerType<?> oven, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(oven, syncId, playerInventory, inventory, (PacketByteBuf)null);
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        return super.onButtonClick(player, id);
    }

    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void addPlayerInv(int x, int y) {
        int n;
        for(n = 0; n < 3; ++n) {
            for(int m = 0; m < 9; ++m) {
                this.addSlot(new Slot(this.playerInv, m + n * 9 + 9, x + m * 18, n * 18 + y));
            }
        }

        for(n = 0; n < 9; ++n) {
            this.addSlot(new Slot(this.playerInv, n, x + n * 18, 58 + y));
        }

    }

    public void addrectInv(int x, int y, int row, int list) {
        for(int n = 0; n < row; ++n) {
            for(int m = 0; m < list; ++m) {
                this.addSlot(new Slot(this.inventory, m + n * list, x + m * 18, y + n * 18));
            }
        }

    }

    public Slot addSlot(Slot slot, int type) {
        this.autorender.add(type);
        return super.addSlot(slot);
    }

    public Slot addSlot(Slot slot) {
        this.autorender.add(1);
        return super.addSlot(slot);
    }

    public abstract void init();
}
