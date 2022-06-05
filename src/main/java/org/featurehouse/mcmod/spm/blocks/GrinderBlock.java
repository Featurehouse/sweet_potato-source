package org.featurehouse.mcmod.spm.blocks;

import org.featurehouse.mcmod.spm.lib.block.entity.AbstractBlockWithEntity;
import com.google.common.collect.ImmutableList;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.blocks.entities.GrinderBlockEntity;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class GrinderBlock extends AbstractBlockWithEntity<GrinderBlockEntity> {
    public static BooleanProperty GRINDING = BooleanProperty.create("grinding");

    public GrinderBlock(BlockBehaviour.Properties settings) {
        super(settings);
        registerDefaultState(this.getStateDefinition().any().setValue(GRINDING, false));
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof GrinderBlockEntity;
    }

    @Override
    public GrinderBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GrinderBlockEntity(pos, state);
    }

    @Override
    public List<ResourceLocation> incrementWhileOnUse(BlockState state, Level world, BlockPos pos, ServerPlayer serverPlayerEntity, InteractionHand hand, BlockHitResult blockHitResult) {
        return ImmutableList.of(SPMMain.INTERACT_WITH_GRINDER);
    }

    @Override
    public BlockEntityType<GrinderBlockEntity> getBlockEntityType() {
        return SPMMain.GRINDER_BLOCK_ENTITY_TYPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GRINDING);
    }
}
