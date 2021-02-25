package io.featurehouse.spm;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;

import java.util.List;
import java.util.OptionalDouble;

public enum SweetPotatoType {
    PURPLE(
            new SweetPotatoComponent(3, 6.0f, 0.35f, OptionalDouble.of(3.0D)),
            new SweetPotatoComponent(8, 9.6f, 0.10f, OptionalDouble.empty()),
            new SweetPotatoComponent(7, 8.6f, 0.60f, OptionalDouble.of(5.0D), true)),
    RED(
            new SweetPotatoComponent(4, 5.0f, 0.30f, OptionalDouble.of(2.6D)),
            new SweetPotatoComponent(7, 9.0f, 0.10f, OptionalDouble.empty()),
            new SweetPotatoComponent(6, 8.0f, 0.55f, OptionalDouble.of(5.0D), true)
    ),
    WHITE(
            new SweetPotatoComponent(2, 4.0f, 0.25f, OptionalDouble.of(2.2D)),
            new SweetPotatoComponent(7, 9.3f, 0.10f, OptionalDouble.empty()),
            new SweetPotatoComponent(6, 8.3f, 0.50f, OptionalDouble.of(5.0D), true)
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
                return SPMMain.PURPLE_POTATO;
            case RED:
                return SPMMain.RED_POTATO;
            case WHITE:
                return SPMMain.WHITE_POTATO;
        }
        return null;
    }

    public ItemConvertible getBaked() {
        switch (this) {
            case PURPLE:
                return SPMMain.BAKED_PURPLE_POTATO;
            case RED:
                return SPMMain.BAKED_RED_POTATO;
            case WHITE:
                return SPMMain.BAKED_WHITE_POTATO;
        }
        return null;
    }

    public Block getCrop() {
        switch (this) {
            case PURPLE:
                return SPMMain.PURPLE_POTATO_CROP;
            case RED:
                return SPMMain.RED_POTATO_CROP;
            case WHITE:
                return SPMMain.WHITE_POTATO_CROP;
        }
        return null;
    }

    public ItemConvertible getEnchanted() {
        switch (this) {
            case PURPLE:
                return SPMMain.ENCHANTED_PURPLE_POTATO;
            case RED:
                return SPMMain.ENCHANTED_RED_POTATO;
            case WHITE:
                return SPMMain.ENCHANTED_WHITE_POTATO;
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

    public List<SweetPotatoType> getOtherTwo() {
        List<SweetPotatoType> list = ObjectArrayList.wrap(values());
        list.remove(this);
        return list;
    }
}
