package org.featurehouse.annotation;

import org.featurehouse.spm.util.registries.RegistryHelper;

/**
 * Annotates methods, fields, etc. from Fabric API
 * registry instead of vanilla registry.
 * @see RegistryHelper
 * @see NonMinecraftNorFabric
 * @see net.minecraft.util.registry.Registry#register
 */
public @interface FabricApiRegistry {
}
