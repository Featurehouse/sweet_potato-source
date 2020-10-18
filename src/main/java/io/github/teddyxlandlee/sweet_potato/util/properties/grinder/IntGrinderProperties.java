package io.github.teddyxlandlee.sweet_potato.util.properties.grinder;

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
}
