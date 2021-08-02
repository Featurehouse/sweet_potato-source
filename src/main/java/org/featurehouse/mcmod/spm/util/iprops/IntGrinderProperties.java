package org.featurehouse.mcmod.spm.util.iprops;

import net.minecraft.screen.PropertyDelegate;

public interface IntGrinderProperties extends PropertyDelegate {
    int getGrindTime();
    int getGrindTimeTotal();
    double getIngredientData();

    default int getIntegerizedIngredientData() {
        return (int) ((long) (getIngredientData() * 10));
    }

    void setGrindTime(int grindTime);
    void setGrindTimeTotal(int grindTimeTotal);
    void setIngredientData(double ingredientData);

    default void setIngredientDataIntegerally(int integerizedIngredientData) {
        this.setIngredientData((integerizedIngredientData) / 10.0D);
    }

    @Override
    default int get(int index) {
        switch (index) {
            case 0:
                return getGrindTime();
            case 1:
                return getGrindTimeTotal();
            case 2:
                return getIntegerizedIngredientData();
            default:
                return -1;
        }
    }

    @Override
    default void set(int index, int value) {
        switch (index) {
            case 0:
                setGrindTime(value);
                break;
            case 1:
                setGrindTimeTotal(value);
                break;
            case 2:
                setIngredientDataIntegerally(value);
        }
    }

    @Override
    default int size() {
        return 3;
    }

    class Impl implements IntGrinderProperties {
        public Impl() {}

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
}
