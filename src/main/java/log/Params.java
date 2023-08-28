package log;

import org.slf4j.event.Level;

/**
 * Params-holder to be used for dynamic Logger configuration in the util class {@link Log4jUtils}
 * holds full classname, log level (one of )
 */
public class Params {

  public static final String DEFAULT_PATTERN = "%d %-5p [%c{1}] %m%n";
  public static final String DEFAULT_LEVEL = Level.DEBUG.name();
  public static final Params DEFAULTS = Params.builder().build();
  private final String logLevel, logPattern;

  Params(ParamsBuilder builder) {
    this.logLevel = builder.getLogLevel();
    this.logPattern = builder.getLogPattern();
  }

  public String getLogLevel() {
    return logLevel;
  }

  public String getLogPattern() {
    return logPattern;
  }

  public static ParamsBuilder builder() {
    return new ParamsBuilder();
  }

  static class ParamsBuilder {

    private String logLevel = DEFAULT_LEVEL, logPattern = DEFAULT_PATTERN;

    public ParamsBuilder level(Level logLevel) {
      this.logLevel = logLevel.name();
      return this;
    }

    public ParamsBuilder pattern(String logPattern) {
      this.logPattern = logPattern;
      return this;
    }

    public String getLogLevel() {
      return logLevel;
    }

    public String getLogPattern() {
      return logPattern;
    }

    public Params build() {
      return new Params(this);
    }
  }
}
