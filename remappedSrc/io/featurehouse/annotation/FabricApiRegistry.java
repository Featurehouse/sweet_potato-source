package io.featurehouse.annotation;

/**
 * Annotates methods, fields, etc. from Fabric API
 * registry instead of vanilla registry.
 * @see io.featurehouse.spm.util.registries.RegistryHelper
 * @see NonMinecraftNorFabric
 * @see net.minecraft.util.registry.Registry#register
 */
public @interface FabricApiRegistry {
}
