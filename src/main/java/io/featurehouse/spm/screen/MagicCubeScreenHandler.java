package io.featurehouse.spm.screen;

import io.featurehouse.annotation.OperationBeforeDeveloping;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.MagicCubeBlock;
import io.featurehouse.spm.util.properties.magiccube.IntMagicCubeProperties;
import io.featurehouse.spm.util.properties.magiccube.NullMagicCubeProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicCubeScreenHandler extends ScreenHandler {
    protected final PlayerInventory playerInventory;
    protected final World world;
    protected final Inventory inventory;
    protected final BlockPos pos;
    private final IntMagicCubeProperties magicCubeProperties;

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
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        BlockState state = this.world.getBlockState(pos);
        return this.inventory.canPlayerUse(player) &&
                state.getBlock() instanceof MagicCubeBlock && state.get(MagicCubeBlock.ACTIVATED);
    }

    @Override
    @OperationBeforeDeveloping
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return super.transferSlot(player, index);
    }

    @Environment(EnvType.CLIENT)
    public short getMainFuelTime() {
        return magicCubeProperties.getMainFuelTime();
    }

    @Environment(EnvType.CLIENT)
    public short getViceFuelTime() {
        return magicCubeProperties.getViceFuelTime();
    }
}
