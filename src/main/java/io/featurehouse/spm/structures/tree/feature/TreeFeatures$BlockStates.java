package io.featurehouse.spm.structures.tree.feature;

import io.featurehouse.spm.SPMMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

final class TreeFeatures$BlockStates {
    public static final BlockState OAK_LOG;
    public static final BlockState ENCHANTED_OAK_LEAVES;

    static {
        OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        ENCHANTED_OAK_LEAVES = SPMMain.ENCHANTED_OAK_LEAVES.getDefaultState();
    }
}
