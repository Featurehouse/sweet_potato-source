//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.featurehouse.spm.blocks.saplings_seeds;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

/**
 * Because of the difficulty of extending {@link SugarCaneBlock},
 * we copied most of the codes from the class, and make
 * {@code EnchantedSugarCaneBlock} extend {@link Block}.
 *
 * @see SugarCaneBlock
 * @see #setDefaultState(BlockState)
 */
public class EnchantedSugarCaneBlock extends Block {
    public static final IntProperty AGE;
    protected static final VoxelShape SHAPE;

    public EnchantedSugarCaneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }

    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isAir(pos.up())) {
            int i;
            for(i = 1; world.getBlockState(pos.down(i)).isOf(this); ++i) {
            }

            if (i < 3) {
                int j = state.get(AGE);
                if (j == 7) {
                    world.setBlockState(pos.up(), this.getDefaultState());
                    world.setBlockState(pos, state.with(AGE, 0), 4);
                } else {
                    world.setBlockState(pos, state.with(AGE, j + 1), 4);
                }
            }
        }

    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (!state.canPlaceAt(world, pos)) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.getBlock() == this) {
            return true;
        } else {
            if (blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isOf(Blocks.DIRT) || blockState.isOf(Blocks.COARSE_DIRT) || blockState.isOf(Blocks.PODZOL) || blockState.isOf(Blocks.SAND) || blockState.isOf(Blocks.RED_SAND)) {
                BlockPos blockPos = pos.down();

                for (Direction direction : Type.HORIZONTAL) {
                    BlockState blockState2 = world.getBlockState(blockPos.offset(direction));
                    FluidState fluidState = world.getFluidState(blockPos.offset(direction));
                    if (fluidState.isIn(FluidTags.WATER) || blockState2.isOf(Blocks.FROSTED_ICE)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        AGE = Properties.AGE_7;
        SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    }
}
