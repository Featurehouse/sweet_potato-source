package io.featurehouse.spm.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.featurehouse.annotation.OperationBeforeDeveloping;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.MagicCubeBlock;
import io.featurehouse.spm.util.properties.state.BooleanStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.block.Blocks.SOUL_FIRE;

public class MagicCubeBlockEntity extends AbstractLockableContainerBlockEntity implements SidedInventory {
    //protected StateHelperV1 stateHelper;
    protected BooleanStateManager stateHelper;

    public MagicCubeBlockEntity(BlockPos pos, BlockState state) {
        this(SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE, pos, state, 5);
    }

    public MagicCubeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state, size);
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
                            pos, MagicCubeBlockEntity.this.world.getBlockState(pos).with(property, b)
                    );
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
    public void tick(World world, BlockPos pos, BlockState state) {
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
        return null; // INDEED TODO
    }


    @OperationBeforeDeveloping
    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @OperationBeforeDeveloping
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @OperationBeforeDeveloping
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @interface FireBelow {
    }
}