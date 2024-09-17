package org.checkerframework.checker.dividebyzero.qual;

import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SubtypeOf({Top.class})
@Target({ElementType.TYPE, ElementType.TYPE_PARAMETER})
public @interface Negative {
}
