package org.featurehouse.spm.blocks;

import org.featurehouse.spm.SPMMain;
import org.featurehouse.spm.screen.SeedUpdaterScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

// 还原：SmithingTableBlock
public class SeedUpdaterBlock extends CraftingTableBlock {
    private static final TranslatableText SCREEN_TITLE = new TranslatableText("container.sweet_potato.seed_updating");
    protected static final VoxelShape SHAPE = Block.createCuboidShape(
            0.0D, 0.0D, 0.0D,
            16.0D, 12.0D, 16.0D
    );

    //public static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    //@Override
    //protected void appendProperties(BooleanStateManager.Builder<Block, BlockState> builder) {
    //    builder.add(WATERLOGGED);
    //}

    public SeedUpdaterBlock(Settings settings) {
        super(settings);
        //setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    //public FluidState getFluidState(BlockState state) {
    //    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    //}

    //public BlockState getPlacementState(ItemPlacementContext context) {
    //    FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
    //    return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    //}



    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inv, player) -> new SeedUpdaterScreenHandler(
                syncId, inv, ScreenHandlerContext.create(world, pos)
        ), SCREEN_TITLE);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if (world.isClient)
            return ActionResult.SUCCESS;
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        player.incrementStat(SPMMain.INTERACT_WITH_AGRO);
        return ActionResult.CONSUME;
    }
}
