package io.github.teddyxlandlee.annotation;

import java.lang.annotation.*;

/**
 * Indicates that a feature is incubating. This means that the feature is currently a work-in-progress and may
 * change at any time.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.PACKAGE,
        ElementType.TYPE,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.METHOD
})
public @interface Incubating {
}