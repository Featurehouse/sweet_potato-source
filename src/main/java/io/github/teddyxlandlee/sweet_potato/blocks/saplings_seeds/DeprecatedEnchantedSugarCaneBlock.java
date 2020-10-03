package io.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import java.util.Random;


/**
 * <p>Vanilla Sugar Cane has 16 ages (from 0 to 15).<br />
 * To increase Enchanted Sugar Cane's speed, we decide
 * to shorten it to 0~7. But the block state is FINAL.
 * We need to edit accessibility file.</p>
 * @since beta-1.0.0
 * @deprecated
 */
@Deprecated
public class DeprecatedEnchantedSugarCaneBlock extends SugarCaneBlock {
    public static final IntProperty REAL_AGE = Properties.AGE_7;

    public DeprecatedEnchantedSugarCaneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(REAL_AGE, 0));
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isAir(pos.up())) {
            int i;
            for(i = 1; world.getBlockState(pos.down(i)).isOf(this); ++i) {
            }

            if (i < 3) {
                int j = state.get(REAL_AGE);
                if (j == 7) {
                    world.setBlockState(pos.up(), this.getDefaultState());
                    world.setBlockState(pos, state.with(REAL_AGE, 0), 4);
                } else {
                    world.setBlockState(pos, state.with(REAL_AGE, j + 1), 4);
                }
            }
        }

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(REAL_AGE);
    }
}
