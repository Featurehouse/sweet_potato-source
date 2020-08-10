package com.github.teddyxlandlee.sweet_potato.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicCubeBlock extends Block implements Tickable {
    public static BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    private World world;
    private BlockPos pos;

    public MagicCubeBlock(Settings settings) {
        super(settings);
        //setDefaultState(getDefaultState().with(ACTIVATED, false));
        setDefaultState(this.getStateManager().getDefaultState().with(ACTIVATED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        //world.setBlockState(pos, calcState(world, pos));
        this.world = world;
        this.pos = pos;
    }

    private BlockState calcState(World world, BlockPos pos) {
        BlockPos downPos = pos.down();
        BlockPos[] posArray = new BlockPos[]{
                downPos,
                downPos.east(), downPos.south(), downPos.west(), downPos.north(),
                downPos.east().north(), downPos.east().south(),
                downPos.west().north(), downPos.west().south()
        };  // down the block 3*3 area
        for (BlockPos pos1: posArray) {
            if (world.getBlockState(pos1).getBlock() == Blocks.SOUL_FIRE) {
                return getDefaultState().with(ACTIVATED, true);
            }
        }
        return getDefaultState().with(ACTIVATED, false);
    }

    //@Override
    //public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
    //    return super.getStateForNeighborUpdate(state, direction, calcState((World)world, pos), world, pos, posFrom);
    //}
    //public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    //    world.setBlockState(pos, calcState(world, pos));
    //}

    @Override
    public void tick() {
        this.world.setBlockState(this.pos, this.calcState(this.world, this.pos));
    }
}
