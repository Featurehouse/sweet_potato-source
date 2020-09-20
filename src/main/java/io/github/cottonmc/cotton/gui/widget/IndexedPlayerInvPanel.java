package io.github.cottonmc.cotton.gui.widget;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

import javax.annotation.Nullable;

@Deprecated
public class IndexedPlayerInvPanel extends WPlainPanel {
    private final WItemSlot inv;
    private final WItemSlot hotbar;
    @Nullable
    private final WWidget label;

    public IndexedPlayerInvPanel(PlayerInventory playerInventory, int startIndex) {
        this(playerInventory, true, startIndex);
    }

    public IndexedPlayerInvPanel(PlayerInventory playerInventory, boolean hasLabel, int startIndex) {
        this(playerInventory, hasLabel ? createDefaultLabel(playerInventory) : null, startIndex);
    }

    public IndexedPlayerInvPanel(PlayerInventory playerInventory, @Nullable WWidget label, int startIndex) {
        int y = 0;
        this.label = label;
        if (label != null) {
            this.add(label, 0, 0, label.getWidth(), label.getHeight());
            y += label.getHeight();
        }
        //this.inv = WItemSlot.ofPlayerStorage(playerInventory);
        this.inv = ofPlayerStorage(playerInventory, startIndex);
        this.hotbar = null;
    }

    protected static WLabel createDefaultLabel(PlayerInventory playerInventory) {
        WLabel label = new WLabel(playerInventory.getDisplayName());
        label.setSize(9*18, 11);
        return label;
    }

    public static WItemSlot ofPlayerStorage(Inventory inventory, int startIndex) {
        return new WItemSlot(inventory, startIndex, 9, 3, false);
    }
}
