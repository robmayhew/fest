/*
 * Created on Dec 2, 2007
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
package org.fest.swing.remote.core;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.TimeoutWatch;

import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;

/**
 * Understands utility methods related to <code>{@link TestServer}</code>.
 *
 * @author Alex Ruiz
 */
public final class TestServerUtils {

  public static void waitUntilStarts(TestServer server) {
    TimeoutWatch watch = startWatchWithTimeoutOf(2000);
    while (!server.isRunning()) {
      if (watch.isTimeout()) throw new WaitTimedOutError("Timed out waiting for server to start");
      delay(200);
    }
  }

  private static void delay(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
  
  private TestServerUtils() {}
}
