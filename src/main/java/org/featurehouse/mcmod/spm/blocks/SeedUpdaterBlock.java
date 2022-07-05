package org.featurehouse.mcmod.spm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.screen.SeedUpdaterScreenHandler;

// 还原：SmithingTableBlock
public class SeedUpdaterBlock extends CraftingTableBlock {
    private static final TranslatableComponent SCREEN_TITLE = new TranslatableComponent("container.sweet_potato.seed_updating");
    protected static final VoxelShape SHAPE = Block.box(
            0.0D, 0.0D, 0.0D,
            16.0D, 12.0D, 16.0D
    );

    //public static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    //@Override
    //protected void appendProperties(BooleanStateManager.Builder<Block, BlockState> builder) {
    //    builder.add(WATERLOGGED);
    //}

    public SeedUpdaterBlock(Properties settings) {
        super(settings);
        //setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
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
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider((syncId, inv, player) -> new SeedUpdaterScreenHandler(
                syncId, inv, ContainerLevelAccess.create(world, pos)
        ), SCREEN_TITLE);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (world.isClientSide)
            return InteractionResult.SUCCESS;
        player.openMenu(state.getMenuProvider(world, pos));
        player.awardStat(SPMMain.INTERACT_WITH_AGRO);
        return InteractionResult.CONSUME;
    }
}
