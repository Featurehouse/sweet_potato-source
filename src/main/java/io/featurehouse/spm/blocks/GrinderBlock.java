package io.featurehouse.spm.blocks;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.entities.GrinderBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class GrinderBlock extends BlockWithEntity {
    public static BooleanProperty GRINDING = BooleanProperty.of("grinding");

    public GrinderBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(GRINDING, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new GrinderBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        //super.onUse(state, world, pos, player, hand, hitResult);  // super returns PASS.
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GrinderBlockEntity) {
                player.openHandledScreen((GrinderBlockEntity) blockEntity);
                player.incrementStat(SPMMain.INTERACT_WITH_GRINDER);
            }

            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof GrinderBlockEntity ? (GrinderBlockEntity) blockEntity : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GrinderBlockEntity) {
                ((GrinderBlockEntity) blockEntity).setCustomName(itemStack.getName());
            }
        }

    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GRINDING);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GrinderBlockEntity) {
                ItemScatterer.spawn(world, pos, (GrinderBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(GRINDING)) {
            double x = pos.getX() + 0.5D, y = pos.getY(), z = pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D)
                world.playSound(x, y, z, SPMMain.GRINDER_GRIND, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
    }
}
