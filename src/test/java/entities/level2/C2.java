package entities.level2;

import log.Log4jUtils;
import org.slf4j.Logger;

public class C2 {
  private static final Logger LOG = Log4jUtils.getLogger(C2.class);

  public void logDemo() {
    LOG.error("C2 error msg");
    LOG.warn("C2 warn msg");
    LOG.info("C2 info msg");
    LOG.debug("C2 debug msg");
    LOG.trace("C2 trace msg");
  }
}
