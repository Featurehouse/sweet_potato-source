package org.featurehouse.mcmod.spm.sweetpotato;

import org.featurehouse.mcmod.spm.util.GrindingUtils;
import org.featurehouse.mcmod.spm.util.registries.ComposterHelper;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemConvertible;

import java.util.Objects;
import java.util.OptionalDouble;

public record SweetPotatoComponent(int hunger, float sat, float compost, OptionalDouble grindData, boolean alwaysEdible) {
    public SweetPotatoComponent(int hunger, float sat, float compost, OptionalDouble grindData) {
        this(hunger, sat, compost, grindData, false);
    }

    public FoodComponent asFoodComponent() {
        FoodComponent.Builder builder = new FoodComponent.Builder()
                .hunger(this.hunger)
                .saturationModifier(this.sat);
        return this.alwaysEdible ? (builder.alwaysEdible().build()) : (builder.build());
    }

    public void registerCompostableItem(SweetPotatoType type, SweetPotatoStatus status) {
        ItemConvertible item = type.get(status);
        if (item != null)
            ComposterHelper.registerCompostableItem(this.compost, Objects.requireNonNull(item));
    }

    public void registerGrindableItem (SweetPotatoType type, SweetPotatoStatus status) {
        ItemConvertible item = type.get(status);
        if (item != null)
            // grindable
            this.grindData.ifPresent(aDouble -> GrindingUtils.registerGrindableItem(aDouble, item));
    }

    @Deprecated
    public static class InvalidComponentException extends Exception {
        public InvalidComponentException() {
            super();
        }

        public InvalidComponentException(String context) {
            super(context);
        }

        public InvalidComponentException(String context, Throwable cause) {
            super(context, cause);
        }

        public InvalidComponentException(Throwable cause) {
            super(cause);
        }
    }
}
