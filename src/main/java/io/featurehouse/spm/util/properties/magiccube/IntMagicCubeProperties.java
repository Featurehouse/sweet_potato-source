package io.featurehouse.spm.util.properties.magiccube;

import net.minecraft.screen.PropertyDelegate;

public interface IntMagicCubeProperties extends PropertyDelegate {
    /**
     * <h2>Main Fuel Time</h2>
     * <div>200: Start up.</div>
     * <div>199-- => 1 : Running.</div>
     * <div>0 : Done. Shall output.</div>
     * <div>-1 : Not Processing.</div>
     * <div>-2, -3 or below: ERROR</div>
     * @see #isProcessing()
     * @see #shouldOutput()
     */
    short getMainFuelTime();    // 200 at most
    /**
     * <h2>Vice Fuel Time</h2>
     * <div>401: Start up.</div>
     * <div>400 => 1. Decrement if {@link #isProcessing()}.</div>
     * <div>Decrement to 0. 0 equals to stop and check.</div>
     * <div>-1, -2, -3 or below: ERROR</div>
     * @see #withViceFuel()
     * @see #shouldUpdateViceFuel()
     */
    short getViceFuelTime();  // 400 at most

    void setMainFuelTime(short time);

    void setViceFuelTime(short time);

    default boolean isProcessing() {
        return getMainFuelTime() >= 0;
    }

    default boolean shouldOutput() {
        return getMainFuelTime() == 0;
    }

    default boolean withViceFuel() {
        return getViceFuelTime() > 0;
    }

    default boolean shouldUpdateViceFuel() {
        return getViceFuelTime() == 0;
    }

    /* *--* IMPLEMENTATIONS *--*/

    @Override
    @Deprecated
    default int get(int index) {
        switch (index) {
            case 0:
                return getMainFuelTime();
            case 1:
                return getViceFuelTime();
            default:
                return -2;
        }
    }

    @Override
    @Deprecated
    default void set(int index, int value) {
        switch (index) {
            case 0:
                setMainFuelTime((short) value);
                break;
            case 1:
                setViceFuelTime((short) value);
        }
    }

    @Override
    @Deprecated
    default int size() {
        return 2;
    }
}
