package org.sl4f;

import org.sl4f.annotation.GenerateCriticalFile;
import org.sl4f.annotation.LogAlias;
import org.sl4f.annotation.LogInfo;
import org.sl4f.annotation.NotDebugLog;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * The Logger class provides a framework for logging messages with different levels of severity.
 * It supports logging to the console and optionally to files for critical errors.
 * <p>
 * Logging levels supported are: INFO, TRACE, WARN, ERROR, and CRITICAL.
 * By default, critical errors lead to program termination.
 * <p>
 * Log messages can be customized and formatted, and can include caller information if enabled.
 * It supports toggling global debug mode, and defining whether critical files should be generated.
 * <p>
 * The Logger class is intended to be subclassed to provide customized logging behavior.
 * It provides protected methods for logging at different levels of severity and handling exceptions.
 * <p>
 * The Logger class also supports annotations for customizing logging behavior:
 * - LogAlias: for specifying an alias for the logger name
 * - LogInfo: for specifying the logging level and verbosity of exceptions
 * - NotDebugLog: for excluding classes from debug logging
 * - GenerateCriticalFile: for indicating whether critical errors should generate log files
 *
 * @author [Vitor Henrique]
 * @version [1.0.0]
 * @since [1.0.0]
 */
public abstract class Logger {

    /**
     * Indicates whether global debug mode is enabled.
     */
    public static boolean globalDebugDefinition = true;

    /**
     * Indicates whether critical files should be generated for errors.
     */
    public static boolean generateCriticalFiles = true;

    /**
     * Indicates whether file tracking is enabled.
     */
    public static boolean enableFileTracking = false;
    private final String name;
    private final DateFormat dateFormat;
    private LogLevel logLevel;
    private boolean isVerboseException;
    private boolean isDebugging;
    private boolean generateCriticalFile;


    /**
     * Constructs a Logger object.
     * Initializes logger properties based on annotations present on the subclass.
     */
    protected Logger() {
        if (getClass().isAnnotationPresent(LogAlias.class)) {
            String alias = getClass().getDeclaredAnnotation(LogAlias.class).value();
            this.name = alias.isEmpty() ? getClass().getSimpleName() : alias;
        } else {
            this.name = getClass().getSimpleName();
        }
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        defineProperties(getClass());
    }


    /**
     * Initializes logger properties based on annotations present on the subclass.
     *
     * @param klass the class of the subclass
     * @param <T>   the type of the subclass
     */
    private <T> void defineProperties(Class<T> klass) {
        if (klass.isAnnotationPresent(LogInfo.class)) {
            LogInfo info = klass.getDeclaredAnnotation(LogInfo.class);
            this.logLevel = info.level();
            this.isVerboseException = info.verbose();
        } else {
            this.logLevel = LogLevel.INFO;
            this.isVerboseException = true;
        }

        this.generateCriticalFile = klass.isAnnotationPresent(GenerateCriticalFile.class);

        this.isDebugging = !klass.isAnnotationPresent(NotDebugLog.class) && globalDebugDefinition;
    }


    /**
     * Formats the timestamp for logging messages.
     *
     * @return the formatted timestamp
     */
    private String getFormattedTimestamp() {
        return dateFormat.format(new Date());
    }


    /**
     * Formats a log message with specified level, message, and color.
     *
     * @param level   the log level
     * @param message the log message
     * @param color   the color for the message
     * @return the formatted log message
     */
    private String formatLogMessage(String level, String message, String color) {
        int maxSpacesLength = Arrays.stream(LogLevel.values()).mapToInt(_level -> _level.name().length()).max().orElse(0);

        int currentSpacesLength = level.length();

        String formattedName = " ".repeat(1 + (maxSpacesLength - currentSpacesLength)) + LogColor.BOLD + name + LogColor.RESET;

        return String.format("[%s] %s %s: %s", getFormattedTimestamp(), color + level + LogColor.RESET, formattedName, message);
    }


    /**
     * Formats an exception message.
     *
     * @param throwable the Throwable object
     * @return the formatted exception message
     */
    private String formatExceptionMessage(Throwable throwable) {
        if (isVerboseException) {
            StringBuilder exceptionMessage = new StringBuilder();
            exceptionMessage.append(throwable.getClass().getName()).append(": ").append(throwable.getMessage()).append("\n");
            for (StackTraceElement element : throwable.getStackTrace()) {
                exceptionMessage.append("\t").append(element.toString()).append("\n");
            }
            return exceptionMessage.toString();
        } else {
            return throwable.getClass().getSimpleName() + (throwable.getMessage() != null ? ": " : "") + throwable.getMessage();
        }
    }


    /**
     * Prints a log message with specified level, message, and color.
     *
     * @param level   the log level
     * @param message the log message
     * @param color   the color for the message
     */
    protected final void printMessage(LogLevel level, String message, String color) {
        if (globalDebugDefinition) {
            if (level.ordinal() <= this.logLevel.ordinal()) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                String line = stackTrace[3].toString();

                String tracking = " [Caller: " + getClass().getSimpleName() + " - line: " + line + "]";
                String logMessage = formatLogMessage(level.name(), message, color) + (enableFileTracking ? tracking : "");
                System.out.println(logMessage);
            }
        }
    }


    /**
     * Logs an informational message.
     *
     * @param target the target object to log
     */
    protected final void info(Object target) {
        if (isDebugging) {
            printMessage(LogLevel.INFO, target.toString(), LogColor.COLOR_INFO);
        }
    }


    /**
     * Logs a trace message.
     *
     * @param target the target object to log
     */
    protected final void trace(Object target) {
        if (isDebugging) {
            printMessage(LogLevel.TRACE, target.toString(), LogColor.COLOR_TRACE);
        }
    }


    /**
     * Logs a warning message.
     *
     * @param target the target object to log
     */
    protected final void warn(Object target) {
        if (isDebugging) {
            printMessage(LogLevel.WARN, target.toString(), LogColor.COLOR_WARN);
        }
    }


    /**
     * Logs an error message.
     *
     * @param target the target object to log
     */
    protected final void error(Object target) {
        printMessage(LogLevel.ERROR, target.toString(), LogColor.COLOR_ERROR);
    }


    /**
     * Logs a critical error message and exits the program.
     *
     * @param target the target object to log
     */
    protected final void critical(Object target) {
        printMessage(LogLevel.CRITICAL, target.toString(), LogColor.COLOR_CRITICAL);
        System.exit(-1);
    }


    /**
     * Logs an error message with an associated exception.
     *
     * @param target    the target object to log
     * @param throwable the Throwable object
     */
    protected final void error(Object target, Throwable throwable) {
        printMessage(LogLevel.ERROR, target.toString(), LogColor.COLOR_ERROR);
        logException(throwable);
    }


    /**
     * Logs a critical error message with an associated runtime exception.
     *
     * @param target    the target object to log
     * @param exception the RuntimeException object
     */
    protected final void critical(Object target, RuntimeException exception) {
        printMessage(LogLevel.CRITICAL, target.toString(), LogColor.COLOR_CRITICAL);

        if (this.generateCriticalFile && generateCriticalFiles) {
            generateCriticalFile(exception, target.toString());
        }
        throwException(exception);
    }


    private void throwException(RuntimeException exception) {
        throw exception;
    }


    /**
     * Prints a line break.
     */
    protected final void breakLine() {
        System.out.println();
    }


    /**
     * Logs an exception.
     *
     * @param throwable the Throwable object
     */
    private void logException(Throwable throwable) {
        System.err.println(formatExceptionMessage(throwable));
    }


    /**
     * Gets the log level.
     *
     * @return the log level
     */
    protected final LogLevel getLogLevel() {
        return this.logLevel;
    }


    /**
     * Generates a critical log file for the given exception and message.
     * If the GenerateCriticalFile annotation is present and critical file generation is enabled,
     * this method creates a log file with details of the exception and the associated message.
     * The log file is named with the current timestamp in the format "yyyy-MM-dd_HH-mm-ss.log".
     * It contains information such as the timestamp, message, stack trace of the exception,
     * and additional details including the exception type, message, cause, and source.
     *
     * @param exception the RuntimeException object representing the exception
     * @param message   the log message associated with the critical error
     */
    private void generateCriticalFile(RuntimeException exception, String message) {
        if (generateCriticalFile) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(getFormattedTimestamp().replaceAll(" ", "_").replaceAll(":", "-") + ".log"))) {
                writer.println("Critical Error:");
                writer.println("Timestamp: " + getFormattedTimestamp());
                writer.println("Message: " + message);
                writer.println("Stack Trace:");
                exception.printStackTrace(writer);
                writer.println("\nAdditional Details:");
                writer.println("Exception Type: " + exception.getClass().getName());
                writer.println("Exception Message: " + exception.getMessage());
                writer.println("Exception Cause: " + (exception.getCause() != null ? exception.getCause().toString() : "N/A"));
                writer.println("Exception Source: " + getExceptionSource(exception));
            } catch (IOException e) {
                error(message, e);
            }
        }
    }


    /**
     * Retrieves the source of an exception.
     *
     * @param exception the Exception object
     * @return the source of the exception
     */
    private String getExceptionSource(Exception exception) {
        StackTraceElement[] elements = exception.getStackTrace();
        if (elements.length > 0) {
            return elements[0].toString();
        }
        return "Unknown";
    }
}
