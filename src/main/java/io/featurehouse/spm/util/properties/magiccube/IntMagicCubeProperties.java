package io.featurehouse.spm.util.properties.magiccube;

import net.minecraft.screen.PropertyDelegate;

public interface IntMagicCubeProperties extends PropertyDelegate {
    /**
     * <h2>Main Fuel Time</h2>
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
     * <div>399 => 0. Decrement if {@link #isProcessing()}.</div>
     * <div>-1, -2, -3 or below: ERROR</div>
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

    /* *--* IMPLEMENTATIONS *--*/

    @Override
    default int get(int index) {
        return index == 0 ? ((getMainFuelTime() << 16) + getViceFuelTime()) : -2;
    }

    @Override
    default void set(int index, int value) {
        if (index == 0) {
            setMainFuelTime((short) ((value >> 16)));
            setViceFuelTime((short) (value));
        }
    }

    @Override
    default int size() {
        return 1;
    }
}
