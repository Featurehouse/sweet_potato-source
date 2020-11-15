package io.github.teddyxlandlee.sweet_potato.blocks;

import io.github.teddyxlandlee.sweet_potato.blocks.entities.MagicCubeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.world.BlockView;

public class MagicCubeBlock extends BlockWithEntity {
    public static BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    public MagicCubeBlock(Settings settings) {
        super(settings);
        //setDefaultState(getDefaultState().with(ACTIVATED, false));
        setDefaultState(this.getStateManager().getDefaultState().with(ACTIVATED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    //@Override
    //public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
    //    return super.getStateForNeighborUpdate(state, direction, calcState((World)world, pos), world, pos, posFrom);
    //}
    //public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    //    world.setBlockState(pos, calcState(world, pos));
    //}

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MagicCubeBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
