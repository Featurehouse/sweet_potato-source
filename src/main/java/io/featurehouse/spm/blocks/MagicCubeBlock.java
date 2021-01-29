package io.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import io.featurehouse.spm.blocks.entities.MagicCubeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.world.BlockView;

public class MagicCubeBlock extends AbstractBlockWithEntity {
    public static BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof MagicCubeBlockEntity;
    }

    public MagicCubeBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(ACTIVATED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MagicCubeBlockEntity();
    }
}
