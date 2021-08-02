package org.featurehouse.mcmod.spm.linkage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
@FunctionalInterface
public interface SPMLinkageServer {
    void initServer();
}
