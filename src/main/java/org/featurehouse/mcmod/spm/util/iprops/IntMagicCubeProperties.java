package org.featurehouse.mcmod.spm.util.iprops;

import net.minecraft.world.inventory.ContainerData;
import org.featurehouse.mcmod.spm.blocks.entities.MagicCubeBlockEntity;

public interface IntMagicCubeProperties extends ContainerData {
    /**
     * <h2>Main Fuel Time</h2>
     * <div>200: Start up.</div>
     * <div>199-- => 1 : Running.</div>
     * <div>0 : Done. Shall output.</div>
     * <div>-1 : Not Processing.</div>
     * <div>-2, -3 or below: ERROR</div>
     * @see MagicCubeBlockEntity#isProcessing()
     * @see MagicCubeBlockEntity#shouldOutput()
     */
    short getMainFuelTime();    // 200 at most
    /**
     * <h2>Vice Fuel Time</h2>
     * <div>401: Start up.</div>
     * <div>400 => 1. Decrement if {@link MagicCubeBlockEntity#isProcessing()}.</div>
     * <div>Decrement to 0. 0 equals to stop and check.</div>
     * <div>-1, -2, -3 or below: ERROR</div>
     * @see MagicCubeBlockEntity#withViceFuel()
     * @see MagicCubeBlockEntity#shouldUpdateViceFuel()
     */
    short getViceFuelTime();  // 400 at most

    void setMainFuelTime(short time);

    void setViceFuelTime(short time);

    /* *--* IMPLEMENTATIONS *--*/

    @Override
    @Deprecated
    default int get(int index) {
        return switch (index) {
            case 0 -> getMainFuelTime();
            case 1 -> getViceFuelTime();
            default -> -2;
        };
    }

    @Override
    @Deprecated
    default void set(int index, int value) {
        switch (index) {
            case 0 -> setMainFuelTime((short) value);
            case 1 -> setViceFuelTime((short) value);
        }
    }

    @Override
    @Deprecated
    default int getCount() {
        return 2;
    }

    class Impl implements IntMagicCubeProperties {
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
}
