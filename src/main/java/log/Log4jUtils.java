package log;

import java.nio.file.Paths;
import java.util.Objects;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.LoggerFactory;

public class Log4jUtils {

  /**
   * Get slf4j logger by class of the objects being logged. It's just an alias to get it as usual
   * for slf4j: {@link LoggerFactory#getLogger} by class
   *
   * @param clazz - class of the objects being logged
   * @return slf4j logger
   */
  public static org.slf4j.Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }

  /**
   * Add to the given logger a file of a given name and default parameters set in {@link Params}
   *
   * @param slf4jLogger - slf4j logger
   * @param filePath - path to file (will add if exists, create if otherwise)
   */
  public static void addFile(org.slf4j.Logger slf4jLogger, String filePath) {
    addFile(slf4jLogger, filePath, Params.DEFAULTS);
  }

  /**
   * Add to the given logger a file appender of a given filePath and params. Not-defined params have
   * values defined in DEFAULT_** constants of {@link Params}.
   *
   * @param slf4jLogger - slf4j logger
   * @param filePath - path to file (will add if exists, create if otherwise)
   * @param params - params-holder object, built with {@link Params})
   */
  public static void addFile(org.slf4j.Logger slf4jLogger, String filePath, Params params) {

    Objects.requireNonNull(slf4jLogger, "slf4jLogger is null");
    Objects.requireNonNull(params, "params is null");
    Objects.requireNonNull(filePath, "filePath is null");
    validateFilePath(filePath);

    String classname = slf4jLogger.getName();
    org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger(classname);
    log4jLogger.addAppender(newFileAppender(classname + "_FileApp", filePath, params));
  }

  /**
   * Sets for the given logger a console appender with default parameters set in {@link Params}
   *
   * @param slf4jLogger - slf4j logger
   */
  public static void addConsole(org.slf4j.Logger slf4jLogger) {
    addConsole(slf4jLogger, Params.DEFAULTS);
  }

  /**
   * Sets the console appender with given params. Not-defined params have values defined in
   * DEFAULT_** constants of {@link Params}.
   *
   * @param slf4jLogger - slf4j logger
   * @param params - params-holder object, built with {@link Params})
   */
  public static void addConsole(org.slf4j.Logger slf4jLogger, Params params) {

    Objects.requireNonNull(slf4jLogger, "slf4jLogger is null");
    Objects.requireNonNull(params, "params is null");

    String classname = slf4jLogger.getName();
    org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger(classname);
    log4jLogger.addAppender(newConsoleAppender(classname + "_ConsApp", params));
  }

  private static FileAppender newFileAppender(String name, String filePath, Params params) {

    FileAppender appender = new FileAppender();
    appender.setName(name);
    appender.setFile(filePath);
    appender.setLayout(new PatternLayout(params.getLogPattern()));
    appender.setThreshold(Level.toLevel(params.getLogLevel()));
    appender.setAppend(true);
    appender.activateOptions();
    return appender;
  }

  private static ConsoleAppender newConsoleAppender(String name, Params params) {

    ConsoleAppender appender = new ConsoleAppender();
    appender.setName(name);
    appender.setLayout(new PatternLayout(params.getLogPattern()));
    appender.setThreshold(Level.toLevel(params.getLogLevel()));
    appender.activateOptions();
    return appender;
  }

  public static void validateFilePath(String filePath) {
    Paths.get(filePath);
  }
}
