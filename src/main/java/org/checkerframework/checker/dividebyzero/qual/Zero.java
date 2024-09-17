package org.checkerframework.checker.dividebyzero.qual;

import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.TYPE_PARAMETER})
@SubtypeOf({Top.class})
public @interface Zero {
}
