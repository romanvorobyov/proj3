package log;

import static log.Log4jUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.event.Level.*;

import entities.C1;
import entities.level2.C2;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.slf4j.Logger;

@Suite
@SuiteDisplayName("slf4j custom dynamic file-appender instrumentation")
@SelectPackages("log")
public class LogTest {
  private static final String C1_LOG = "logs/c1.log", C2_LOG = "logs/c2.log";
  private static final String ERROR_LOG = "logs/error.log", TRACE_LOG = "logs/trace.log";

  private static final Logger loggerC1 = getLogger(C1.class);
  private static final Logger loggerC2 = getLogger(C2.class);

  private static final Set<String> c1Log = new HashSet<>(),
      c2Log = new HashSet<>(),
      errors = new HashSet<>(),
      traces = new HashSet<>();

  @BeforeAll
  public static void prepare() throws IOException {
    File folder = new File("logs/");
    if (folder.exists() && folder.isDirectory()) {
      for (File log : Objects.requireNonNull(folder.listFiles())) {
        log.delete();
      }
    }
    String pattern = "%m%n";

    addFile(loggerC1, C1_LOG, Params.builder().level(DEBUG).pattern(pattern).build());
    addFile(loggerC2, C2_LOG, Params.builder().level(WARN).pattern(pattern).build());

    addFile(loggerC1, ERROR_LOG, Params.builder().level(ERROR).pattern(pattern).build());
    addFile(loggerC2, ERROR_LOG, Params.builder().level(ERROR).pattern(pattern).build());

    addFile(loggerC1, TRACE_LOG, Params.builder().level(TRACE).pattern(pattern).build());
    addFile(loggerC2, TRACE_LOG, Params.builder().level(TRACE).pattern(pattern).build());

    new C1().logDemo();
    new C2().logDemo();

    c1Log.addAll(Files.readAllLines(Path.of(C1_LOG)));
    c2Log.addAll(Files.readAllLines(Path.of(C2_LOG)));
    errors.addAll(Files.readAllLines(Path.of(ERROR_LOG)));
    traces.addAll(Files.readAllLines(Path.of(TRACE_LOG)));
  }

  @Test
  public void testErrorLevelForDebugLoggerC1() {
    String c1Error = "C1 error msg";
    assertTrue(errors.contains(c1Error));
    assertTrue(c1Log.contains(c1Error));
    assertTrue(traces.contains(c1Error));
  }

  @Test
  public void testInfoLevelForDebugLoggerC1() {
    String c1Info = "C1 info msg";
    assertFalse(errors.contains(c1Info)); // should NOT be seen in ERROR-log
    assertTrue(c1Log.contains(c1Info)); // SHOULD be seen in DEBUG-log
    assertTrue(traces.contains(c1Info));
  }

  @Test
  public void testInfoLevelForWarnLoggerC2() {
    String c2Info = "C2 info msg";
    assertFalse(errors.contains(c2Info)); // should NOT be seen in ERROR-log
    assertFalse(c2Log.contains(c2Info)); // should NOT be seen in WARN-log
    assertTrue(traces.contains(c2Info));
  }

  @Test
  public void testTraceForDebugLoggerC1() {
    String c1Trace = "C1 trace msg";
    assertFalse(c1Log.contains(c1Trace)); // should NOT be seen in ERROR-log
    assertFalse(errors.contains(c1Trace)); // should NOT be seen in DEBUG-log
    assertTrue(traces.contains(c1Trace));
  }
}
