package io.featurehouse.spm.structures.tree.feature;

import io.featurehouse.spm.SPMMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

final class TreeFeatures$BlockStates {
    public static final BlockState OAK_LOG;
    public static final BlockState ENCHANTED_OAK_LEAVES;

    public static final BlockState SPRUCE_LOG;
    public static final BlockState ENCHANTED_SPRUCE_LEAVES;
    public static final BlockState PODZOL;

    public static final BlockState BIRCH_LOG;
    public static final BlockState ENCHANTED_BIRCH_LEAVES;

    static {
        OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        ENCHANTED_OAK_LEAVES = SPMMain.ENCHANTED_OAK_LEAVES.getDefaultState();

        SPRUCE_LOG = Blocks.SPRUCE_LOG.getDefaultState();
        ENCHANTED_SPRUCE_LEAVES = SPMMain.ENCHANTED_SPRUCE_LEAVES.getDefaultState();
        PODZOL = Blocks.PODZOL.getDefaultState();

        BIRCH_LOG = Blocks.BIRCH_LOG.getDefaultState();
        ENCHANTED_BIRCH_LEAVES = SPMMain.ENCHANTED_BIRCH_LEAVES.getDefaultState();
    }
}
