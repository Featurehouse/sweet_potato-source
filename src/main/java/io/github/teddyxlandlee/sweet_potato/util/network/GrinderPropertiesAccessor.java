package io.github.teddyxlandlee.sweet_potato.util.network;

import io.github.teddyxlandlee.sweet_potato.util.FloatIntegerizer;
import net.minecraft.screen.PropertyDelegate;

public interface GrinderPropertiesAccessor extends PropertyDelegate {
    int getGrindTime();
    int getGrindTimeTotal();
    float getIngredientData();

    void setGrindTime(int grindTime);
    void setGrindTimeTotal(int grindTimeTotal);
    void setIngredientData(float ingredientData);

    default int get(int index) {
        switch (index) {
            case 0:
                return getGrindTime();
            case 1:
                return getGrindTimeTotal();
            case 2:
                // ingredientData: float
                return FloatIntegerizer.fromFloat(getIngredientData());
            default:
                return 0;
        }
    }

    default void set(int index, int value) {
        switch (index) {
            case 0:
                setGrindTime(value);
            case 1:
                setGrindTimeTotal(value);
            case 2:
                // ingredientData: float
                setIngredientData(FloatIntegerizer.toFloat(value));
        }
    }

    @Override
    default int size() {
        return 3;
    }
}
