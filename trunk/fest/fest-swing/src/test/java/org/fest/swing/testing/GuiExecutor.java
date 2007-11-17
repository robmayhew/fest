/*
 * Created on Aug 7, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.testing;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

/**
 * Understands an <code>{@link java.util.concurrent.Executor}</code> built atop <code>{@link SwingUtilities}</code>.
 * Borrowed from the book "Java Concurrency In Practice" by Brian Goetz.
 * 
 * @author Yvonne Wang
 */
public class GuiExecutor extends AbstractExecutorService {
  
  private static final GuiExecutor INSTANCE = new GuiExecutor();

  /**
   * Executes the given command at some time in the future.
   * @param command the runnable task.
   */
  public void execute(Runnable command) {
    if (SwingUtilities.isEventDispatchThread()) command.run();
    else SwingUtilities.invokeLater(command);
  }
  
  public static GuiExecutor instance() { return INSTANCE; }
  private GuiExecutor() {}

  public void shutdown() { throw new UnsupportedOperationException(); }
  public List<Runnable> shutdownNow() { throw new UnsupportedOperationException(); }
  public boolean awaitTermination(long timeout, TimeUnit unit) { throw new UnsupportedOperationException(); }
  public boolean isShutdown() { return false; }
  public boolean isTerminated() { return false; }
}
