package org.sl4f.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The NotDebugLog annotation excludes classes from debug logging.
 * When applied to a class, it indicates that instances of that class should not
 * produce debug log messages even if global debug mode is enabled.
 * This annotation is retained at runtime and can be applied only to types (classes).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface NotDebugLog {
}
