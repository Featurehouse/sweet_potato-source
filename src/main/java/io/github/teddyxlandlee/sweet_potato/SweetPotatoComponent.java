package io.github.teddyxlandlee.sweet_potato;

import io.github.teddyxlandlee.sweet_potato.util.Util;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemConvertible;

import java.util.Objects;
import java.util.OptionalDouble;

public class SweetPotatoComponent {
    protected int hunger;
    protected float sat;
    protected float compost;
    protected OptionalDouble grindData;
    protected boolean alwaysEdible;

    public SweetPotatoComponent(int hunger, float sat, float compost, OptionalDouble grindData) {
        this(hunger, sat, compost, grindData, false);
    }

    public SweetPotatoComponent(int hunger, float sat, float compost, OptionalDouble grindData, boolean alwaysEdible) {
        this.hunger = hunger;
        this.sat = sat;
        this.compost = compost;
        this.grindData = grindData;
        this.alwaysEdible = alwaysEdible;
    }

    public int getHunger() {
        return hunger;
    }

    public float getSat() {
        return sat;
    }

    public float getCompost() {
        return compost;
    }

    public OptionalDouble getGrindData() {
        return grindData;
    }

    public boolean isAlwaysEdible() {
        return alwaysEdible;
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
            Util.registerCompostableItem(this.compost, Objects.requireNonNull(item));
    }

    public void registerGrindableItem (SweetPotatoType type, SweetPotatoStatus status) {
        ItemConvertible item = type.get(status);
        if (item != null)
            // grindable
            this.grindData.ifPresent(aDouble -> Util.registerGrindableItem(aDouble, item));
    }

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
