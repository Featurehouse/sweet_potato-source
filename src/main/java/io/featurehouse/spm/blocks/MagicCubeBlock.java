package io.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import com.google.common.collect.ImmutableList;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.entities.MagicCubeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class MagicCubeBlock extends AbstractBlockWithEntity {
    public static BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    @Override
    public List<Identifier> incrementWhileOnUse(BlockState state, World world, BlockPos pos, ServerPlayerEntity serverPlayerEntity, Hand hand, BlockHitResult blockHitResult) {
        return ImmutableList.of(SPMMain.INTERACT_WITH_MAGIC_CUBE);
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof MagicCubeBlockEntity;
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
    public BlockEntity createBlockEntity(BlockView world) {
        return new MagicCubeBlockEntity();
    }
}
