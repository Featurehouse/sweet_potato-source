package io.featurehouse.spm.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.featurehouse.annotation.OperationBeforeDeveloping;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.MagicCubeBlock;
import io.featurehouse.spm.screen.MagicCubeScreenHandler;
import io.featurehouse.spm.util.properties.magiccube.IntMagicCubeProperties;
import io.featurehouse.spm.util.properties.state.BooleanStateManager;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.Blocks.SOUL_FIRE;

public class MagicCubeBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable, SidedInventory, ExtendedScreenHandlerFactory, IntMagicCubeProperties {
    //protected StateHelperV1 stateHelper;
    protected BooleanStateManager stateHelper;
    protected short mainFuelTime;
    protected short viceFuelTime;

    public MagicCubeBlockEntity() {
        this(SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE, 8);
    }

    public MagicCubeBlockEntity(BlockEntityType<?> type, int size) {
        super(type, size);
        this.stateHelper = new BooleanStateManager(MagicCubeBlock.ACTIVATED) {
            public boolean shouldChange(boolean newOne) {
                assert MagicCubeBlockEntity.this.world != null;
                return MagicCubeBlockEntity.this.world.getBlockState(pos).get(property) != newOne;
            }

            @Override
            public void run() {
                assert MagicCubeBlockEntity.this.world != null;
                boolean b;
                if (this.shouldChange(b = this.fireCount() > 0)) {
                    MagicCubeBlockEntity.this.world.setBlockState(
                            MagicCubeBlockEntity.this.pos,
                            MagicCubeBlockEntity.this.world.getBlockState(
                                    MagicCubeBlockEntity.this.pos
                            ).with(property, b));
                }
            }

            @FireBelow
            byte fireCount() {
                BlockPos[] blockPosList = calcPos(MagicCubeBlockEntity.this.getPos());

                assert MagicCubeBlockEntity.this.world != null;
                byte b = 0;
                for (BlockPos eachPos: blockPosList) {
                    if (MagicCubeBlockEntity.this.world.getBlockState(eachPos).getBlock() == SOUL_FIRE)
                        ++b;
                }
                return b;
            }
        };
    }

    @Override
    public void tick() {
        assert this.world != null;
        if (world.getTime() % 20L == 5L) {
            stateHelper.run();
        }
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.sweet_potato.magic_cube");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new MagicCubeScreenHandler(syncId, playerInventory, world, pos, this, this); // INDEED TODO
    }

    @OperationBeforeDeveloping
    @Override
    public int[] getAvailableSlots(Direction side) {
        switch (side) {
            case DOWN:
                return new int[] { 3, 4, 5 };
            case UP:
                return new int[] { 0, 1, 2 };
            default:
                return new int[] { 6, 7 };
        }
    }

    @OperationBeforeDeveloping
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @OperationBeforeDeveloping
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return (slot == 6 || slot == 7) && dir == Direction.DOWN;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public short getMainFuelTime() {
        return mainFuelTime;
    }

    @Override
    public short getViceFuelTime() {
        return viceFuelTime;
    }

    @Override
    public void setMainFuelTime(short time) {
        mainFuelTime = time;
    }

    @Override
    public void setViceFuelTime(short time) {
        viceFuelTime = time;
    }

    @interface FireBelow {}
}
