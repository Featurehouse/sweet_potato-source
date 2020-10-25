package io.github.teddyxlandlee.sweet_potato.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.blocks.MagicCubeBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicCubeBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable {
    private byte fireChanged;

    public MagicCubeBlockEntity() {
        this(SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE, 5);
    }

    public MagicCubeBlockEntity(BlockEntityType<?> type, int size) {
        super(type, size);
    }

    @Override
    public void tick() {
        assert this.world != null;
        // Magic Cube Activation
        //this.world.setBlockState(this.pos, StateHelper.calcState(this.world, this.pos));
        StateHelper.changeIfChange(this.world, this.pos);
        this.fireChanged = StateHelper.fireCount(this.world, this.pos);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.sweet_potato.magic_cube");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null; // INDEED TODO
    }

    @interface FireBelow {
    }

    static class StateHelper {
        private StateHelper() {}

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
