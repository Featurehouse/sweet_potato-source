package io.featurehouse.spm.screen;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.util.properties.magiccube.IntMagicCubeProperties;
import io.featurehouse.spm.util.properties.magiccube.NullMagicCubeProperties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;

public class MagicCubeScreenHandler extends ScreenHandler {
    public MagicCubeScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, inventory.player.world, new SimpleInventory(8), new NullMagicCubeProperties());
    }

    public MagicCubeScreenHandler(int syncId, PlayerInventory playerInventory, World world, Inventory inventory, IntMagicCubeProperties properties) {
        super(SPMMain.MAGIC_CUBE_SCREEN_HANDLER_TYPE, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
