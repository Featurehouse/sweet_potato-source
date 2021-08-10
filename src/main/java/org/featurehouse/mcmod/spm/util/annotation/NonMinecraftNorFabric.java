package org.featurehouse.mcmod.spm.util.annotation;

import org.featurehouse.mcmod.spm.util.GrindingUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates the registries, etc. that does not from
 * vanilla Minecraft or Fabric API.
 * @see GrindingUtils#registerGrindableItem
 * @see FabricApiRegistry
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
public @interface NonMinecraftNorFabric {
}
