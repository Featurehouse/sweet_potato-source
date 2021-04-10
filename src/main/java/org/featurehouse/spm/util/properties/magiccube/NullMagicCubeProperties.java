package org.featurehouse.spm.util.properties.magiccube;

public class NullMagicCubeProperties implements IntMagicCubeProperties {
    private short mainFuelTime = -3;
    private short viceFuelTime = -3;

    @Override
    public short getMainFuelTime() {
        return mainFuelTime;
    }

    @Override
    public short getViceFuelTime() {
        return viceFuelTime;
    }

    @Override
    public void setMainFuelTime(short time) {
        mainFuelTime = time;
    }

    @Override
    public void setViceFuelTime(short time) {
        viceFuelTime = time;
    }
}
