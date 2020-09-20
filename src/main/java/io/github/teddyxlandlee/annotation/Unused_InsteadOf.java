package io.github.teddyxlandlee.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.MODULE, ElementType.PARAMETER, ElementType.TYPE})
@Unused_InsteadOf     // Ridiculous
public @interface Unused_InsteadOf {
    String since() default "";

    boolean forRemoval() default false;
}
