package org.featurehouse.annotation;

import org.featurehouse.spm.util.Util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotates the registries, etc. that does not from
 * vanilla Minecraft or Fabric API.
 * @see Util#registerGrindableItem
 * @see FabricApiRegistry
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
public @interface NonMinecraftNorFabric {
}
