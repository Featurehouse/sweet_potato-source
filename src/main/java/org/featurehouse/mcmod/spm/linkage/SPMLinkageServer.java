package org.featurehouse.mcmod.spm.linkage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@FunctionalInterface
@Environment(EnvType.SERVER)
public interface SPMLinkageServer {
    void initServer();
}
