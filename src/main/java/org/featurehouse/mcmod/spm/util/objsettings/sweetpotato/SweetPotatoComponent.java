package org.featurehouse.mcmod.spm.util.objsettings.sweetpotato;

import org.featurehouse.mcmod.spm.util.registries.GrindingUtils;
import org.featurehouse.mcmod.spm.util.registries.ComposterHelper;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemConvertible;

import java.util.Objects;
import java.util.OptionalDouble;

public class SweetPotatoComponent {
    private final int hunger;
    private final float saturationModifier;
    private final float compost;
    private final OptionalDouble grindData;
    private final boolean alwaysEdible;

    public SweetPotatoComponent(int hunger, float sat, float compost, OptionalDouble grindData, boolean alwaysEdible) {
        this.hunger = hunger;
        this.saturationModifier = sat / hunger / 2.0F;   // saturation = food * saturationModifier * 2.0F
        this.grindData = grindData;
        this.compost = compost;
        this.alwaysEdible = alwaysEdible;
    }

    public SweetPotatoComponent(int hunger, float sat, float compost, OptionalDouble grindData) {
        this(hunger, sat, compost, grindData, false);
    }

    public FoodComponent asFoodComponent() {
        FoodComponent.Builder builder = new FoodComponent.Builder()
                .hunger(this.hunger)
                .saturationModifier(this.saturationModifier);
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

    public int getHunger() {
        return hunger;
    }

    public float getSaturationModifier() {
        return saturationModifier;
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
}
