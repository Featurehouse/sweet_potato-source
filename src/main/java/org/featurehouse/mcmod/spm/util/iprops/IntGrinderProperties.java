package org.featurehouse.mcmod.spm.util.iprops;

import net.minecraft.world.inventory.ContainerData;

public interface IntGrinderProperties extends ContainerData {
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
        return switch (index) {
            case 0 -> getGrindTime();
            case 1 -> getGrindTimeTotal();
            case 2 -> getIntegerizedIngredientData();
            default -> -1;
        };
    }

    @Override
    default void set(int index, int value) {
        switch (index) {
            case 0 -> setGrindTime(value);
            case 1 -> setGrindTimeTotal(value);
            case 2 -> setIngredientDataIntegerally(value);
        }
    }

    @Override
    default int getCount() {
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
    }
}
