package org.sl4f;

/**
 * The LogLevel enum represents different levels of severity for log messages.
 * It includes levels from most critical (CRITICAL) to least critical (INFO).
 * These levels can be used to categorize log messages and determine their importance.
 */
public enum LogLevel {
    CRITICAL, // Most critical level
    ERROR,    // Error level
    WARN,     // Warning level
    TRACE,    // Trace level
    INFO      // Information level (least critical)
}
