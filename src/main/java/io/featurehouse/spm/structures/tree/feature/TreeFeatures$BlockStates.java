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

    public static final BlockState JUNGLE_LOG;
    public static final BlockState ENCHANTED_JUNGLE_LEAVES;

    public static final BlockState ACACIA_LOG;
    public static final BlockState ENCHANTED_ACACIA_LEAVES;

    public static final BlockState DARK_OAK_LOG;
    public static final BlockState ENCHANTED_DARK_OAK_LEAVES;

    static {
        OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        ENCHANTED_OAK_LEAVES = SPMMain.ENCHANTED_OAK_LEAVES.getDefaultState();

        SPRUCE_LOG = Blocks.SPRUCE_LOG.getDefaultState();
        ENCHANTED_SPRUCE_LEAVES = SPMMain.ENCHANTED_SPRUCE_LEAVES.getDefaultState();
        PODZOL = Blocks.PODZOL.getDefaultState();

        BIRCH_LOG = Blocks.BIRCH_LOG.getDefaultState();
        ENCHANTED_BIRCH_LEAVES = SPMMain.ENCHANTED_BIRCH_LEAVES.getDefaultState();

        JUNGLE_LOG = Blocks.JUNGLE_LOG.getDefaultState();
        ENCHANTED_JUNGLE_LEAVES = SPMMain.ENCHANTED_JUNGLE_LEAVES.getDefaultState();

        ACACIA_LOG = Blocks.ACACIA_LOG.getDefaultState();
        ENCHANTED_ACACIA_LEAVES = SPMMain.ENCHANTED_ACACIA_LEAVES.getDefaultState();

        DARK_OAK_LOG = Blocks.DARK_OAK_LOG.getDefaultState();
        ENCHANTED_DARK_OAK_LEAVES = SPMMain.ENCHANTED_DARK_OAK_LEAVES.getDefaultState();
    }
}
