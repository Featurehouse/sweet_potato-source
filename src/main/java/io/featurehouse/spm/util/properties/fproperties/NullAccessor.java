package io.featurehouse.spm.util.properties.fproperties;

@Deprecated
public class NullAccessor implements GrinderPropertiesAccessor {
    private int grindTime;
    private int grindTimeTotal;
    private float ingredientData;

    public NullAccessor() {
        this.grindTime = -1;
        this.grindTimeTotal = 0;
        this.ingredientData = 0.0f;
    }

    @Override
    public int getGrindTime() {
        return grindTime;
    }

    @Override
    public int getGrindTimeTotal() {
        return grindTimeTotal;
    }

    @Override
    public float getIngredientData() {
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
    public void setIngredientData(float ingredientData) {
        this.ingredientData = ingredientData;
    }
}
