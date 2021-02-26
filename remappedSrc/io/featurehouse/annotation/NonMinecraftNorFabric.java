package io.featurehouse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotates the registries, etc. that does not from
 * vanilla Minecraft or Fabric API.
 * @see io.featurehouse.spm.util.Util#registerGrindableItem
 * @see FabricApiRegistry
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
public @interface NonMinecraftNorFabric {
}
