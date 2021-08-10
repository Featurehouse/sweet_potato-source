package org.featurehouse.mcmod.spm.linkage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@FunctionalInterface
@Environment(EnvType.SERVER)
public interface SPMLinkageServer {
    void initServer();

    static void load() {
        FabricLoader.getInstance().getEntrypoints("sweet_potato.server", SPMLinkageServer.class)
                .forEach(SPMLinkageServer::initServer);
    }
}
