package org.sl4f;

/**
 * The LogColor class provides ANSI escape sequences for text colors in terminal output.
 * It defines constants for resetting color, bold text, and different colors for log levels.
 * These escape sequences can be used to format log messages with different colors.
 * This class is intended for internal use within the logging framework.
 */
class LogColor {
    static final String RESET = "\u001B[0m";            // Reset color
    static final String BOLD = "\u001B[1m";             // Bold text
    static final String COLOR_INFO = "\u001B[36m";      // Cyan color for INFO level
    static final String COLOR_TRACE = "\u001B[37m";     // White color for TRACE level
    static final String COLOR_WARN = "\u001B[33m";      // Yellow color for WARN level
    static final String COLOR_ERROR = "\u001B[31m";     // Red color for ERROR level
    static final String COLOR_CRITICAL = "\u001B[35m";  // Purple color for CRITICAL level
}
