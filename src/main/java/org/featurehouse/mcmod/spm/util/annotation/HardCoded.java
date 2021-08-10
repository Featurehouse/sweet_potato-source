package org.featurehouse.mcmod.spm.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Hardcoded elements, instead of these from JSON, etc.
 * @see org.featurehouse.mcmod.spm.blocks.entities.GrinderBlockEntity
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface HardCoded {
}
