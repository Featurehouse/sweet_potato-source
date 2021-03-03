package org.featurehouse.annotation;

import org.featurehouse.spm.blocks.entities.GrinderBlockEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Hardcoded elements, instead of these from JSON, etc.
 * @see GrinderBlockEntity
 */
@Target({ElementType.METHOD})
public @interface HardCoded {
}
