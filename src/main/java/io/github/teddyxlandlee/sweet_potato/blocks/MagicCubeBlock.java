package io.github.teddyxlandlee.sweet_potato.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.MagicCubeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;

public class MagicCubeBlock extends AbstractBlockWithEntity<MagicCubeBlockEntity> {
    public static BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    @Override
    public BlockEntityType<MagicCubeBlockEntity> getBlockEntityType() {
        return SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE;
    }

    public MagicCubeBlock(Settings settings) {
        super(settings);
        //setDefaultState(getDefaultState().with(ACTIVATED, false));
        setDefaultState(this.getStateManager().getDefaultState().with(ACTIVATED, false));
    }

    @Override
    public MagicCubeBlockEntity createTileEntity(BlockPos pos, BlockState state) {
        return new MagicCubeBlockEntity(pos, state);
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
}
