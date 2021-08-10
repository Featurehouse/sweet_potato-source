package org.featurehouse.mcmod.spm.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotates methods, fields, etc. from Fabric API
 * registry instead of vanilla registry.
 * @see org.featurehouse.mcmod.spm.util.registries.RegistryHelper
 * @see NonMinecraftNorFabric
 * @see net.minecraft.util.registry.Registry#register
 */
@Retention(RetentionPolicy.SOURCE)
public @interface FabricApiRegistry {
}
