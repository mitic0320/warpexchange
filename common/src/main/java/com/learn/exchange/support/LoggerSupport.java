package com.learn.exchange.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggerSupport {
  /**
   * Subclass could use logger directly.
   */
  protected final Logger logger = LoggerFactory.getLogger(getClass());
}
