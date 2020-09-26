package io.github.teddyxlandlee.sweet_potato;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;

import java.util.Optional;

public enum SweetPotatoType {
    PURPLE(
            new SweetPotatoComponent(3, 6.0f, 0.35f, Optional.of(3.0f)),
            new SweetPotatoComponent(8, 9.6f, 0.10f, Optional.empty()),
            new SweetPotatoComponent(7, 8.6f, 0.60f, Optional.of(5.0f))),
    RED(
            new SweetPotatoComponent(4, 5.0f, 0.30f, Optional.of(2.6f)),
            new SweetPotatoComponent(7, 9.0f, 0.10f, Optional.empty()),
            new SweetPotatoComponent(6, 8.0f, 0.55f, Optional.of(5.0f))
    ),
    WHITE(
            new SweetPotatoComponent(2, 4.0f, 0.25f, Optional.of(2.2f)),
            new SweetPotatoComponent(7, 9.3f, 0.10f, Optional.empty()),
            new SweetPotatoComponent(6, 8.3f, 0.50f, Optional.of(5.0f))
    );

    private final SweetPotatoComponent raw;
    private final SweetPotatoComponent baked;
    private final SweetPotatoComponent enchanted;

    SweetPotatoType(SweetPotatoComponent raw, SweetPotatoComponent baked, SweetPotatoComponent enchanted) {
        this.raw = raw;
        this.baked = baked;
        this.enchanted = enchanted;
    }

    public SweetPotatoComponent getComponent(SweetPotatoStatus status) {
        switch (status) {
            case RAW:
                return raw;
            case BAKED:
                return baked;
            case ENCHANTED:
                return enchanted;
            default:
                return null;
        }
    }

    public ItemConvertible getRaw() {
        switch (this) {
            case PURPLE:
                return ExampleMod.PURPLE_POTATO;
            case RED:
                return ExampleMod.RED_POTATO;
            case WHITE:
                return ExampleMod.WHITE_POTATO;
        }
        return null;
    }

    public ItemConvertible getBaked() {
        switch (this) {
            case PURPLE:
                return ExampleMod.BAKED_PURPLE_POTATO;
            case RED:
                return ExampleMod.BAKED_RED_POTATO;
            case WHITE:
                return ExampleMod.BAKED_WHITE_POTATO;
        }
        return null;
    }

    public Block getCrop() {
        switch (this) {
            case PURPLE:
                return ExampleMod.PURPLE_POTATO_CROP;
            case RED:
                return ExampleMod.RED_POTATO_CROP;
            case WHITE:
                return ExampleMod.WHITE_POTATO_CROP;
        }
        return null;
    }

    public ItemConvertible getEnchanted() {
        switch (this) {
            case PURPLE:
                return ExampleMod.ENCHANTED_PURPLE_POTATO;
            case RED:
                return ExampleMod.ENCHANTED_RED_POTATO;
            case WHITE:
                return ExampleMod.ENCHANTED_WHITE_POTATO;
        }
        return null;
    }

    public ItemConvertible get(SweetPotatoStatus status) {
        switch (status) {
            case RAW:
                return this.getRaw();
            case BAKED:
                return this.getBaked();
            case ENCHANTED:
                return this.getEnchanted();
            case CROP:
                return this.getCrop();
        }
        return null;
    }
}
