package org.featurehouse.mcmod.spm.util.objsettings.sweetpotato;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import org.featurehouse.mcmod.spm.SPMMain;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.Stream;

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
        return switch (status) {
            case RAW -> raw;
            case BAKED -> baked;
            case ENCHANTED -> enchanted;
            default -> null;
        };
    }

    public ItemConvertible getRaw() {
        return switch (this) {
            case PURPLE -> SPMMain.PURPLE_POTATO;
            case RED -> SPMMain.RED_POTATO;
            case WHITE -> SPMMain.WHITE_POTATO;
        };
    }

    public ItemConvertible getBaked() {
        return switch (this) {
            case PURPLE -> SPMMain.BAKED_PURPLE_POTATO;
            case RED -> SPMMain.BAKED_RED_POTATO;
            case WHITE -> SPMMain.BAKED_WHITE_POTATO;
        };
    }

    public Block getCrop() {
        return switch (this) {
            case PURPLE -> SPMMain.PURPLE_POTATO_CROP;
            case RED -> SPMMain.RED_POTATO_CROP;
            case WHITE -> SPMMain.WHITE_POTATO_CROP;
        };
    }

    public ItemConvertible getEnchanted() {
        return switch (this) {
            case PURPLE -> SPMMain.ENCHANTED_PURPLE_POTATO;
            case RED -> SPMMain.ENCHANTED_RED_POTATO;
            case WHITE -> SPMMain.ENCHANTED_WHITE_POTATO;
        };
    }

    public ItemConvertible get(SweetPotatoStatus status) {
        return switch (status) {
            case RAW -> this.getRaw();
            case BAKED -> this.getBaked();
            case ENCHANTED -> this.getEnchanted();
            case CROP -> this.getCrop();
        };
    }

    /*public List<SweetPotatoType> getOtherTwo() {
        List<SweetPotatoType> list = ObjectArrayList.wrap(values());
        list.remove(this);
        return list;
    }*/

    public Stream<SweetPotatoType> getOtherTwo() {
        return Arrays.stream(values()).filter(type -> this != type);
    }
}
