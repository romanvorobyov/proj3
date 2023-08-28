package entities;

import log.Log4jUtils;
import org.slf4j.Logger;

public class C1 {

  private static final Logger LOG = Log4jUtils.getLogger(C1.class);

  public void logDemo() {
    LOG.error("C1 error msg");
    LOG.warn("C1 warn msg");
    LOG.info("C1 info msg");
    LOG.debug("C1 debug msg");
    LOG.trace("C1 trace msg");
  }
}
