package io.featurehouse.spm.util.properties.grinder;

public class NullGrinderProperties implements IntGrinderProperties {
    public NullGrinderProperties() {}

    int grindTime;
    int grindTimeTotal;
    double ingredientData;

    @Override
    public int getGrindTime() {
        return grindTime;
    }

    @Override
    public int getGrindTimeTotal() {
        return grindTimeTotal;
    }

    @Override
    public double getIngredientData() {
        return ingredientData;
    }

    @Override
    public void setGrindTime(int grindTime) {
        this.grindTime = grindTime;
    }

    @Override
    public void setGrindTimeTotal(int grindTimeTotal) {
        this.grindTimeTotal = grindTimeTotal;
    }

    @Override
    public void setIngredientData(double ingredientData) {
        this.ingredientData = ingredientData;
    }

    @Deprecated
    static class GrinderProperties {
        int grindTime;
        int grindTimeTotal;
        double ingredientData;

        GrinderProperties(int grindTime, int grindTimeTotal, double ingredientData) {
            this.grindTime = grindTime;
            this.grindTimeTotal = grindTimeTotal;
            this.ingredientData = ingredientData;
        }

        @Deprecated
        GrinderProperties(int... values) {
            this(values[0], values[1], (values[2] / 10.0D));
            if (values.length != 3) {
                throw new UnsupportedOperationException("Values shall be three");
            }
        }
    }
}
