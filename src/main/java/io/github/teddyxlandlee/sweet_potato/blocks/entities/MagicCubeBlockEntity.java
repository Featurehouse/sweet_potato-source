package io.github.teddyxlandlee.sweet_potato.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import io.github.teddyxlandlee.annotation.OperationBeforeDeveloping;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.blocks.MagicCubeBlock;
import io.github.teddyxlandlee.sweet_potato.util.BooleanStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class MagicCubeBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable, SidedInventory {
    protected StateHelperV1 stateHelper;
    private byte fireChanged;

    public MagicCubeBlockEntity() {
        this(SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE, 5);
    }

    public MagicCubeBlockEntity(BlockEntityType<?> type, int size) {
        super(type, size);
        this.stateHelper = new StateHelperV1(this.world, this.pos);
    }

    @Deprecated
    public void deprecatedTick() {
        assert this.world != null;
        // Magic Cube Activation
        //this.world.setBlockState(this.pos, BooleanStateManager.calcState(this.world, this.pos));
        StateHelperV0.changeIfChange(this.world, this.pos);
        this.fireChanged = StateHelperV0.fireCount(this.world, this.pos);
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

    static final class StateHelperV1 extends BooleanStateManager {
        List<BlockPos> blockPosList;

        StateHelperV1(@Nullable World world, BlockPos pos) {
            super(world, pos);
            BlockPos downPos = pos.down();
            blockPosList = Arrays.asList(downPos,
                    downPos.east(), downPos.south(), downPos.west(), downPos.north(),
                    downPos.east().north(), downPos.east().south(),
                    downPos.west().north(), downPos.west().south()
            );
        }

        public void run() {
            assert world != null;
            boolean b;
            if (shouldChange(b = fireCount() > 0)) {
                world.setBlockState(pos, world.getBlockState(pos).with(MagicCubeBlock.ACTIVATED, b));
            }
        }

        @FireBelow
        byte fireCount() {
            assert world != null;
            byte b = 0;
            for (BlockPos blockPos : blockPosList) {
                if (world.getBlockState(blockPos).getBlock() == Blocks.SOUL_FIRE)
                    ++b;
            }
            return b;
        }
    }

    static class StateHelperV0 {
        private StateHelperV0() {}

        protected static boolean stateChanged(BlockState oldState, BlockState newState) {
            if (oldState.getBlock() != SPMMain.MAGIC_CUBE || newState.getBlock() != SPMMain.MAGIC_CUBE)
                throw new UnsupportedOperationException("Non-magic cube operates");
            return oldState.get(MagicCubeBlock.ACTIVATED) == newState.get(MagicCubeBlock.ACTIVATED);
        }

        protected static boolean stateChanged(World world, BlockPos pos, BlockState newState) {
            return stateChanged(world.getBlockState(pos), newState);
        }

        protected static void changeIfChange(World world, BlockPos pos) {
            BlockState newState = calcState(world, pos);
            if (stateChanged(world, pos, newState))
                world.setBlockState(pos, newState);
        }

        @NonMinecraftNorFabric
        protected static BlockState calcState(World world, BlockPos pos) {
            if (fireCount(world, pos) > 0) {
                return SPMMain.MAGIC_CUBE.getDefaultState().with(MagicCubeBlock.ACTIVATED, true);
            }
            return SPMMain.MAGIC_CUBE.getDefaultState().with(MagicCubeBlock.ACTIVATED, false);
        }

        @FireBelow
        @NonMinecraftNorFabric
        protected static byte fireCount(World world, BlockPos pos) {
            byte fire = 0;
            BlockPos downPos = pos.down();
            BlockPos[] posArray = new BlockPos[]{
                    downPos,
                    downPos.east(), downPos.south(), downPos.west(), downPos.north(),
                    downPos.east().north(), downPos.east().south(),
                    downPos.west().north(), downPos.west().south()
            };  // down the block 3*3 area
            for (BlockPos eachPos: posArray) {
                if (world.getBlockState(eachPos).getBlock() == Blocks.SOUL_FIRE) {
                    ++fire;
                }
            }
            return fire;
        }
    }
}
