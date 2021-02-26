package io.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import com.google.common.collect.ImmutableList;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.entities.MagicCubeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
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
import java.util.Random;
import java.util.function.BiPredicate;

public class MagicCubeBlock extends AbstractBlockWithEntity {
    public static BooleanProperty ACTIVATED = BooleanProperty.of("activated");
    public static final BiPredicate<World, BlockPos> ACTIVATION_PREDICATE = (world, blockPos) -> {
        BlockState state = world.getBlockState(blockPos);
        return state.getBlock() instanceof MagicCubeBlock && state.get(ACTIVATED);
    };

    @Override
    public List<Identifier> incrementWhileOnUse(BlockState state, World world, BlockPos pos, ServerPlayerEntity serverPlayerEntity, Hand hand, BlockHitResult blockHitResult) {
        return ImmutableList.of(SPMMain.INTERACT_WITH_MAGIC_CUBE);
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof MagicCubeBlockEntity;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        if (!state.isOf(newState.getBlock())) {
            world.playSound(null, pos, SPMMain.MAGIC_CUBE_DEACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (state.getBlock() instanceof MagicCubeBlock && !state.get(ACTIVATED)) return ActionResult.PASS;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MagicCubeBlockEntity && state.getBlock() instanceof MagicCubeBlock && state.get(ACTIVATED)) {
                player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
                incrementWhileOnUse(state, world, pos, (ServerPlayerEntity) player, hand, hit).forEach(player::incrementStat);
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

    @Environment(EnvType.CLIENT) @Deprecated //@Override
    /*
    * @deprecated this method has bug so we paused it.
    */
    public void randomDisplayTick1(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if (ACTIVATION_PREDICATE.test(world, pos) && random.nextInt(24) == 0) {
            world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SPMMain.MAGIC_CUBE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }
    }
}
