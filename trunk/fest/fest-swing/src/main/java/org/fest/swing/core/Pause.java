/*
 * Created on Dec 19, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.core;

import java.util.concurrent.TimeUnit;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.TimeoutWatch;

import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.concat;

/**
 * Understands waiting for period of time or for a particular condition to be satisfied.
 *
 * @author Alex Ruiz
 */
public final class Pause {

  private static final int DEFAULT_DELAY = 30000;
  private static final int SLEEP_INTERVAL = 10;

  /**
   * Waits until the given condition is <code>true</code>.
   * @param condition the condition to verify.
   * @throws org.fest.swing.exception.WaitTimedOutError if the wait times out (more 30 seconds).
   */
  public static void pause(Condition condition) {
    pause(condition, DEFAULT_DELAY);
  }

  /**
   * Waits until the given condition is <code>true</code>.
   * @param condition the condition to verify.
   * @param timeout the timeout.
   * @throws org.fest.swing.exception.WaitTimedOutError if the wait times out.
   */
  public static void pause(Condition condition, Timeout timeout) {
    pause(condition, timeout.duration());
  }
  
  /**
   * Waits until the given condition is <code>true</code>.
   * @param condition the condition to verify.
   * @param timeout the timeout (in milliseconds.)
   * @throws org.fest.swing.exception.WaitTimedOutError if the wait times out.
   */
  public static void pause(Condition condition, long timeout) {
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!condition.test()) {
      if (watch.isTimeOut()) throw new WaitTimedOutError((concat("Timed out waiting for ", condition)));
      pause(SLEEP_INTERVAL);
    }
  }

  /**
   * Sleeps for the specified time. 
   * @param timeout the quantity of time units to sleep.
   * @param unit the time units.
   * @see #pause(long)
   */
  public static void pause(long timeout, TimeUnit unit) {
    if (unit == null) throw new IllegalArgumentException("Time unit cannot be null");
    pause(unit.toMillis(timeout));
  }
  
  /**
   * Sleeps for the specified time. 
   * <p>
   * To catch any <code>InterruptedException</code>s that occur,
   * <code>{@link Thread#sleep(long)}()</code> may be used instead.
   * </p>
   * @param ms the time to sleep in milliseconds.
   */
  public static void pause(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
  
  /**
   * Sleeps for 10 milliseconds.
   */
  public static void pause() { pause(SLEEP_INTERVAL); }
  
  private Pause() {}
}
