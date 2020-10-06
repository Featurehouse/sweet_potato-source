package io.github.teddyxlandlee.sweet_potato.util.network;

public interface GrinderPropertiesAccessor {
    int getGrindTime();
    int getGrindTimeTotal();
    float getIngredientData();

    void setGrindTime(int grindTime);
    void setGrindTimeTotal(int grindTimeTotal);
    void setIngredientData(float ingredientData);
}
