package com.github.teddyxlandlee.sweet_potato.blocks;

import com.github.teddyxlandlee.debug.Debug;
import com.github.teddyxlandlee.debug.PartType;
import com.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GrinderBlock extends BlockWithEntity {
    public GrinderBlock(AbstractBlock.Settings settings) {
        super(settings);
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
                Debug.debug(this.getClass(), PartType.METHOD, "onUse",
                        "The block entity IS grinder's");

                player.openHandledScreen((GrinderBlockEntity) blockEntity);
                Debug.debug(this.getClass(), PartType.METHOD, "onUse",
                        "Successfully opened handled screen");
            }

            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        Debug.debug(this.getClass(), PartType.METHOD, "createScreenHandlerFactory",
                "Successfully get block entity from world");
        return blockEntity instanceof GrinderBlockEntity ? (GrinderBlockEntity) blockEntity : null;
    }
}
