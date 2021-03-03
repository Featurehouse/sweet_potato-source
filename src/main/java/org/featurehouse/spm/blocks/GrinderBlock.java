package org.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import com.google.common.collect.ImmutableList;
import org.featurehouse.spm.SPMMain;
import org.featurehouse.spm.blocks.entities.GrinderBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class GrinderBlock extends AbstractBlockWithEntity {
    public static BooleanProperty GRINDING = BooleanProperty.of("grinding");

    public GrinderBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(GRINDING, false));
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof GrinderBlockEntity;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new GrinderBlockEntity();
    }

    @Override
    public List<Identifier> incrementWhileOnUse(BlockState state, World world, BlockPos pos, ServerPlayerEntity serverPlayerEntity, Hand hand, BlockHitResult blockHitResult) {
        return ImmutableList.of(SPMMain.INTERACT_WITH_GRINDER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GRINDING);
    }
}
