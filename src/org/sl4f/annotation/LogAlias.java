package org.sl4f.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The LogAlias annotation allows specifying an alias for the logger name.
 * When applied to a class, it assigns an alternate name to the logger associated with that class.
 * This annotation is retained at runtime and can be applied only to types (classes).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LogAlias {
    String value();
}
