package io.github.teddyxlandlee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CopiedFrom {
    Class<?> value();
}
