package org.featurehouse.mcmod.spm.world.levelmeta;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.WorldData;

public class SPMLevelPropertiesHelper {
    public static final int DATA_VERSION = 15;  // 1.2.0.

    public static int getLevelSPMDataVersion(MinecraftServer server) {
        return getLevelSPMDataVersion(server.getWorldData());
    }

    public static int getLevelSPMDataVersion(WorldData properties) {
        return getLevelSPMDataVersion((SPMLevelProperties) properties);
    }

    /* Zero: wasn't loaded */
    public static int getLevelSPMDataVersion(SPMLevelProperties spmLevelProperties) {
        CompoundTag tag = spmLevelProperties.sweetPotato_getSPMMetaRaw();
        return tag.getInt("DataVersion");
    }

    public static void setLevelSPMDataVersion(SPMLevelProperties spmLevelProperties, int dataVersion) {
        spmLevelProperties.sweetPotato_getSPMMetaRaw().putInt("DataVersion", dataVersion);
    }

    public static void setCurrentSPMDataVersion(SPMLevelProperties spmLevelProperties) {
        setLevelSPMDataVersion(spmLevelProperties, DATA_VERSION);
    }
}
