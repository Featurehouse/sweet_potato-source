package org.featurehouse.mcmod.spm.blocks.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnchantedSaplings extends SaplingBlock {
    private final AbstractTreeGrower generator;

    public EnchantedSaplings(AbstractTreeGrower generator, Properties settings) {
        super(generator, settings);
        this.generator = generator;
        this.registerDefaultState(this.getStateDefinition().any().setValue(STAGE, 0));
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (world.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(3) == 0) {
            this.advanceTree(world, pos, state, random);
        }
    }

    public void advanceTree(ServerLevel serverWorld, BlockPos blockPos, BlockState blockState, Random random) {
        if (blockState.getValue(STAGE) == 0) {
            serverWorld.setBlock(blockPos, blockState.cycle(STAGE), 4);
        } else {
            this.generator.growTree(serverWorld, serverWorld.getChunkSource().getGenerator(), blockPos, blockState, random);
        }
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return (double)world.random.nextFloat() < 0.8D;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        this.advanceTree(world, pos, state, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

}
