package org.featurehouse.mcmod.spm.world.levelmeta;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SaveProperties;

public class SPMLevelPropertiesHelper {
    public static final int DATA_VERSION = 17;  // 1.3.0.

    public static int getLevelSPMDataVersion(MinecraftServer server) {
        return getLevelSPMDataVersion(server.getSaveProperties());
    }

    public static int getLevelSPMDataVersion(SaveProperties properties) {
        return getLevelSPMDataVersion((SPMLevelProperties) properties);
    }

    /* Zero: wasn't loaded */
    public static int getLevelSPMDataVersion(SPMLevelProperties spmLevelProperties) {
        NbtCompound tag = spmLevelProperties.sweetPotato_getSPMMetaRaw();
        return tag.getInt("DataVersion");
    }

    public static void setLevelSPMDataVersion(SPMLevelProperties spmLevelProperties, int dataVersion) {
        spmLevelProperties.sweetPotato_getSPMMetaRaw().putInt("DataVersion", dataVersion);
    }

    public static void setCurrentSPMDataVersion(SPMLevelProperties spmLevelProperties) {
        setLevelSPMDataVersion(spmLevelProperties, DATA_VERSION);
    }
}
