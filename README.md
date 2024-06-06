# SL4F - Simple Logging for Java

SL4F (Simple Logging for Java) is a lightweight logging framework designed 
to provide easy-to-use logging capabilities for Java applications. It 
offers support for logging messages with different levels of severity, 
customizable log formatting, and optional file logging for critical errors.

## Features

- **Logging Levels:** Supports various logging levels including INFO, TRACE, 
- WARN, ERROR, and CRITICAL, allowing you to categorize log messages based 
- on their severity.
- **Customizable:** Log messages can be customized and formatted according 
- to your requirements.
- **Annotation Support:** Provides annotations for customizing logging 
- behavior, such as specifying logging levels and aliases for loggers.
- **Global Debug Mode:** Allows toggling global debug mode to enable/disable 
- debug logging across the application.
- **Critical Error Handling:** Supports generating log files for critical 
- errors, providing detailed information about the error, timestamp, and stack 
- trace.

## Installation

You can include SL4F in your Java project
using [JAR](https://github.com/Vitorhenriquesilvadesa/SL4J/releases/tag/Release) file:


## Usage
To start using SL4F in your Java application, you can create a subclass of 
the Logger class and customize logging behavior as needed. Here's a simple 
example:

```java
import org.sl4f.Logger;
import org.sl4f.annotation.LogAlias;
import org.sl4f.annotation.LogInfo;

@LogAlias("MyLogger")
@LogInfo(level = LogLevel.INFO)
public class MyCustomLogger extends Logger {
    // Your custom methods and logic here
}
```

Then, you can use your custom logger to log messages at different levels:

```java

import org.sl4f.Logger;
import org.sl4f.annotation.LogAlias;
import org.sl4f.annotation.LogInfo;

@LogAlias("MyLogger")
@LogInfo(level = LogLevel.INFO)
public class MyCustomLogger extends Logger {
    // Your custom methods and logic here
}

MyCustomLogger logger = new MyCustomLogger();

logger.info("This is an informational message.");
logger.warn("This is a warning message.");
logger.error("This is an error message.");
logger.critical("This is a critical error message.");
```

Or use the logger methods inside your class. It is recommended that if you 
have many classes that want to use the logger, for example: ImageLoader, 
AudioLoader, SaveLoader, ComponentLoader; You create a base class, something 
like AssetLoader, which will inherit the Logger.