package org.featurehouse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Hardcoded elements, instead of these from JSON, etc.
 * @see org.featurehouse.mcmod.spm.blocks.entities.GrinderBlockEntity
 */
@Target({ElementType.METHOD})
public @interface HardCoded {
}
