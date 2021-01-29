package io.featurehouse.spm.util.properties.fproperties;

import io.featurehouse.spm.util.FloatIntegerizer;
import net.minecraft.screen.PropertyDelegate;

@Deprecated
public interface GrinderPropertiesAccessor extends PropertyDelegate, TriAccessor<Integer, Integer, Float> {
    int getGrindTime();
    int getGrindTimeTotal();
    float getIngredientData();

    void setGrindTime(int grindTime);
    void setGrindTimeTotal(int grindTimeTotal);
    void setIngredientData(float ingredientData);

    default void setFirst(Integer grindTime) {
        setGrindTime(grindTime);
    }

    default void setSecond(Integer grindTimeTotal) {
        setGrindTimeTotal(grindTimeTotal);
    }

    default void setThird(Float ingredientData) {
        setIngredientData(ingredientData);
    }

    default Integer getFirst() {
        return getGrindTime();
    }

    default Integer getSecond() {
        return getGrindTimeTotal();
    }

    default Float getThird() {
        return getIngredientData();
    }

    // -*- TriAccessor -*- //
    // -*- PropertyDelegate -*- //

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

    default int size() {
        return 3;
    }
}
