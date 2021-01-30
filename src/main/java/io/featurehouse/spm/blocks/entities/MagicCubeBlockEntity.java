package io.featurehouse.spm.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.MagicCubeBlock;
import io.featurehouse.spm.screen.MagicCubeScreenHandler;
import io.featurehouse.spm.util.properties.magiccube.IntMagicCubeProperties;
import io.featurehouse.spm.util.properties.state.BooleanStateManager;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
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
    private static final int[] TOP_SLOTS = new int[] { 0, 1, 2 };
    private static final int[] BOTTOM_SLOTS = new int[] { 3, 4, 5 };
    private static final int[] SIDE_SLOTS = new int[] { 6, 7 };

    private boolean activationCache = false;

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
                            ).with(property, b)
                    );
                    MagicCubeBlockEntity.this.activationCache = b;
                    if (!b) {
                        MagicCubeBlockEntity.this.mainFuelTime = -1;
                        MagicCubeBlockEntity.this.viceFuelTime = 0;
                    }
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
        this.mainFuelTime = -1;
        this.viceFuelTime = 0;
    }

    @Override
    public void tick() {
        assert this.world != null;
        if (world.getTime() % 20L == 5L) {
            stateHelper.run();
        }
        if (!activationCache) return;

        /*-* * PROPERTIES * *-*/
        if (!this.isProcessing()) {
            // Check inventory
            if (this.inventory.get(6).getItem() == SPMMain.PEEL) {
                boolean bl = false;
                for (int i = 0; i < 3; ++i) {
                    if (this.inventory.get(i).getItem().isIn(SPMMain.RAW_SWEET_POTATOES)) {
                        this.mainFuelTime = 200;
                        bl = true;
                        break;
                    }
                }
                if (bl) {
                    // DECREMENT PEEL, START PROGRESS.
                    this.inventory.get(6).decrement(1);
                }
            }
        }
        // CHECK VICE FUEL
        if (this.shouldUpdateViceFuel()) {
            if (this.inventory.get(7).getItem() == SPMMain.POTATO_POWDER) {
                this.viceFuelTime = 401;
                this.inventory.get(7).decrement(1);
            }
        }

        if (this.shouldOutput()) {
            calculateOutput();
        }

        if (this.isProcessing()) {
            --this.mainFuelTime;
            if (withViceFuel())
                --viceFuelTime;
        }
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.sweet_potato.magic_cube");
    }

    protected void calculateOutput() {

    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new MagicCubeScreenHandler(syncId, playerInventory, world, pos, this, this); // INDEED TODO
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.mainFuelTime = tag.getShort("EnergyTime");
        this.viceFuelTime = tag.getShort("SublimateTime");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putShort("EnergyTime", this.mainFuelTime);
        tag.putShort("SublimateTime", this.viceFuelTime);
        return tag;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        switch (side) {
            case DOWN:
                return BOTTOM_SLOTS;
            case UP:
                return TOP_SLOTS;
            default:
                return SIDE_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return (slot == 6 || slot == 7) && dir == Direction.DOWN;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        Item item = stack.getItem();
        if (slot == 6)
            return item == SPMMain.PEEL;
        if (slot == 7)
            return item == SPMMain.POTATO_POWDER;
        if ((slot >= 3 && slot <= 5) || slot > 7 || slot < 0) return false;
        return item.isIn(SPMMain.RAW_SWEET_POTATOES);
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

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        BlockState blockState;
        return this.world != null && this.world.getBlockEntity(pos) == this
                && (blockState = this.world.getBlockState(pos)).getBlock() instanceof MagicCubeBlock
                && blockState.get(MagicCubeBlock.ACTIVATED);
    }

    @interface FireBelow {}
}
