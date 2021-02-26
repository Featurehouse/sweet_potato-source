package io.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.entities.MagicCubeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicCubeBlock extends AbstractBlockWithEntity<MagicCubeBlockEntity> {
    public static BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof MagicCubeBlockEntity;
    }

    @Override
    public BlockEntityType<MagicCubeBlockEntity> getBlockEntityType() {
        return SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (state.getBlock() instanceof MagicCubeBlock && !state.get(ACTIVATED)) return ActionResult.PASS;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MagicCubeBlockEntity && state.getBlock() instanceof MagicCubeBlock && state.get(ACTIVATED)) {
                player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
            }
            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
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
    public MagicCubeBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MagicCubeBlockEntity(pos, state);
    }
}
