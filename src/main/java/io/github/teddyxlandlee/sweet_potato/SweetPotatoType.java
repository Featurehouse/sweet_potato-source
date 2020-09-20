package io.github.teddyxlandlee.sweet_potato;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;

public enum SweetPotatoType {
    PURPLE,
    RED,
    WHITE;

    public ItemConvertible getRaw() {
        switch(this) {
            case PURPLE:
                return ExampleMod.PURPLE_POTATO;
            case RED:
                return ExampleMod.RED_POTATO;
            case WHITE:
                return ExampleMod.WHITE_POTATO;
        }
        return null;
    }
    public Block getCrop() {
        switch(this) {
            case PURPLE:
                return ExampleMod.PURPLE_POTATO_CROP;
            case RED:
                return ExampleMod.RED_POTATO_CROP;
            case WHITE:
                return ExampleMod.WHITE_POTATO_CROP;
        }
        return null;
    }
}
