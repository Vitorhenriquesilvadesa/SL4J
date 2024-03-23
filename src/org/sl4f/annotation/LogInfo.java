package org.sl4f.annotation;


import org.sl4f.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The LogInfo annotation specifies the logging level and verbosity of exceptions.
 * When applied to a class, it defines the logging level for messages and whether
 * exceptions should be logged with verbose details.
 * This annotation is retained at runtime and can be applied only to types (classes).
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo {
    LogLevel level() default LogLevel.INFO;

    boolean verbose() default true;
}

