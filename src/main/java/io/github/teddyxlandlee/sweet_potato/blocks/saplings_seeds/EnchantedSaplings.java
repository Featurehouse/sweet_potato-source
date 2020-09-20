package io.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class EnchantedSaplings extends SaplingBlock {
    private final SaplingGenerator generator;

    public EnchantedSaplings(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
        this.generator = generator;
        this.setDefaultState(this.getStateManager().getDefaultState().with(STAGE, 0));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(3) == 0) {
            this.generate(world, pos, state, random);
        }
    }

    public void generate(ServerWorld serverWorld, BlockPos blockPos, BlockState blockState, Random random) {
        if (blockState.get(STAGE) == 0) {
            serverWorld.setBlockState(blockPos, blockState.cycle(STAGE), 4);
        } else {
            this.generator.generate(serverWorld, serverWorld.getChunkManager().getChunkGenerator(), blockPos, blockState, random);
        }
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return (double)world.random.nextFloat() < 0.8D;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.generate(world, pos, state, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

}
