package org.featurehouse.mcmod.spm.linkage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface SPMLinkageClient {
    void initClient();
}
