package org.sl4f.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The GenerateCriticalFile annotation indicates whether critical files should be generated for errors.
 * When applied to a class, it specifies that critical errors occurring within that class should trigger
 * the generation of log files containing details of the error.
 * This annotation is retained at runtime and can be applied only to types (classes).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenerateCriticalFile {
}
