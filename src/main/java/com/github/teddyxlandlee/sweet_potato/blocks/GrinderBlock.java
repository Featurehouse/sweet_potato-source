package com.github.teddyxlandlee.sweet_potato.blocks;

import bilibili.ywsuoyi.block.blockWithEntity;
import com.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class GrinderBlock extends blockWithEntity {
    public GrinderBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new GrinderBlockEntity();
    }
}
