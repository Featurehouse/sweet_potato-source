package io.featurehouse.spm.util.properties.grinder;

import io.featurehouse.annotation.Unused_InsteadOf;

import org.jetbrains.annotations.NotNull;

public class NullGrinderProperties implements IntGrinderProperties {
    public NullGrinderProperties() {}

    GrinderProperties properties = new GrinderProperties(0, 0, 0.0D);

    @Override
    public int getGrindTime() {
        return properties.grindTime;
    }

    @Override
    public int getGrindTimeTotal() {
        return properties.grindTimeTotal;
    }

    @Override
    public double getIngredientData() {
        return properties.ingredientData;
    }

    @Override
    public void setGrindTime(int grindTime) {
        properties.grindTime = grindTime;
    }

    @Override
    public void setGrindTimeTotal(int grindTimeTotal) {
        properties.grindTimeTotal = grindTimeTotal;
    }

    @Override
    public void setIngredientData(double ingredientData) {
        properties.ingredientData = ingredientData;
    }

    static class GrinderProperties {
        int grindTime;
        int grindTimeTotal;
        double ingredientData;

        GrinderProperties(int grindTime, int grindTimeTotal, double ingredientData) {
            this.grindTime = grindTime;
            this.grindTimeTotal = grindTimeTotal;
            this.ingredientData = ingredientData;
        }

        @Unused_InsteadOf
        @Deprecated
        GrinderProperties(@NotNull int... values) {
            this(values[0], values[1], (values[2] / 10.0D));
            if (values.length != 3) {
                throw new UnsupportedOperationException("Values shall be three");
            }
        }
    }
}
