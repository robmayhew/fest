/*
 * Created on Mar 23, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.monitor;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

import static org.fest.reflect.core.Reflection.field;

/**
 * Prevents misbehaving TimerTasks from canceling the timer thread by throwing exceptions and/or errors. Naming the
 * timer thread facilitates discerning different threads in a full stack dump.
 * 
 * @author Alex Ruiz
 */
public class NamedTimer extends Timer {

  private static Logger logger = Logger.getLogger(NamedTimer.class.getName());
  
  /**
   * Creates a new timer whose associated thread has the specified name.
   * The associated thread does <i>not</i> run as a daemon.
   * @param name the name of the associated thread.
   * @throws NullPointerException if name is <code>null</code>.
   */
  public NamedTimer(final String name) {
    this(name, false);
  }

  /**
   * Creates a new timer whose associated thread has the specified name, and may be specified to run as a daemon.
   * @param name the name of the associated thread.
   * @param isDaemon true if the associated thread should run as a daemon.
   * @throws NullPointerException if name is <code>null</code>.
   */
  public NamedTimer(final String name, boolean isDaemon) {
    super(name, isDaemon);
  }

  /**
   * Same as <code>{@link Timer#schedule(TimerTask, Date)}</code>, but wrapping the given task with a
   * <code>ProtectingTimerTask</code>.
   * @param task task to be scheduled.
   * @param time time at which task is to be executed.
   * @throws IllegalArgumentException if <tt>time.getTime()</tt> is negative.
   * @throws IllegalStateException if task was already scheduled or canceled, timer was canceled, or timer thread
   *         terminated.
   */
  @Override public void schedule(TimerTask task, Date time) {
    super.schedule(new ProtectingTimerTask(task), time);
  }

  /**
   * Same as <code>{@link Timer#schedule(TimerTask, Date, long)}</code>, but wrapping the given task with a
   * <code>ProtectingTimerTask</code>.
   * @param task task to be scheduled.
   * @param firstTime First time at which task is to be executed.
   * @param period time in milliseconds between successive task executions.
   * @throws IllegalArgumentException if <tt>time.getTime()</tt> is negative.
   * @throws IllegalStateException if task was already scheduled or canceled, timer was canceled, or timer thread 
   *         terminated.
   */
  @Override public void schedule(TimerTask task, Date firstTime, long period) {
    super.schedule(new ProtectingTimerTask(task), firstTime, period);
  }

  /**
   * Same as <code>{@link Timer#schedule(TimerTask, long)}</code>, but wrapping the given task with a
   * <code>ProtectingTimerTask</code>.
   * @param task task to be scheduled.
   * @param delay delay in milliseconds before task is to be executed.
   * @throws IllegalArgumentException if <tt>delay</tt> is negative, or <tt>delay + System.currentTimeMillis()</tt>
   *         is negative.
   * @throws IllegalStateException if task was already scheduled or canceled, or timer was canceled.
   */
  @Override public void schedule(TimerTask task, long delay) {
    super.schedule(new ProtectingTimerTask(task), delay);
  }

  /**
   * Same as <code>{@link Timer#schedule(TimerTask, long, long)}</code>, but wrapping the given task with a
   * <code>ProtectingTimerTask</code>.
   * @param task task to be scheduled.
   * @param delay delay in milliseconds before task is to be executed.
   * @param period time in milliseconds between successive task executions.
   * @throws IllegalArgumentException if <tt>delay</tt> is negative, or <tt>delay + System.currentTimeMillis()</tt>
   *         is negative.
   * @throws IllegalStateException if task was already scheduled or canceled, timer was canceled, or timer thread
   *         terminated.
   */
  @Override public void schedule(TimerTask task, long delay, long period) {
    super.schedule(new ProtectingTimerTask(task), delay, period);
  }

  /**
   * Same as <code>{@link Timer#scheduleAtFixedRate(TimerTask, Date, long)}</code>, but wrapping the given task with
   * a <code>ProtectingTimerTask</code>.
   * @param task task to be scheduled.
   * @param firstTime First time at which task is to be executed.
   * @param period time in milliseconds between successive task executions.
   * @throws IllegalArgumentException if <tt>time.getTime()</tt> is negative.
   * @throws IllegalStateException if task was already scheduled or canceled, timer was canceled, or timer thread
   *         terminated.
   */
  @Override public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
    super.scheduleAtFixedRate(new ProtectingTimerTask(task), firstTime, period);
  }

  /**
   * Same as <code>{@link Timer#schedule(TimerTask, Date, long)}</code>, but wrapping the given task with a
   * <code>ProtectingTimerTask</code>.
   * @param task task to be scheduled.
   * @param delay delay in milliseconds before task is to be executed.
   * @param period time in milliseconds between successive task executions.
   * @throws IllegalArgumentException if <tt>delay</tt> is negative, or <tt>delay + System.currentTimeMillis()</tt>
   *         is negative.
   * @throws IllegalStateException if task was already scheduled or canceled, timer was canceled, or timer thread
   *         terminated.
   */
  @Override public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
    super.scheduleAtFixedRate(new ProtectingTimerTask(task), delay, period);
  }

  // TODO: prevent scheduled tasks from throwing uncaught exceptions and thus canceling the Timer.
  // We can easily wrap scheduled tasks with a catcher, but we can't readily cancel the wrapper when
  private class ProtectingTimerTask extends TimerTask {
    private static final int CANCELED = 3;

    private final TimerTask task;

    public ProtectingTimerTask(TimerTask task) {
      this.task = task;
    }

    public void run() {
      if (isCanceled()) {
        cancel();
        return;
      } 
      try {
        task.run();
      } catch (Throwable thrown) {
        handleException(thrown);
      }
    }

    private boolean isCanceled() {
      boolean isCancelled = false;
      try {
        int state = field("state").ofType(int.class).in(task).get();
        isCancelled = state == CANCELED;
      } catch (RuntimeException e) {
        handleException(e);
      }
      return isCancelled;
    }
  }

  /**
   * Handle an exception thrown by a <code>TimerTask</code>. The default does nothing.
   * @param thrown the thrown exception.
   */
  protected void handleException(Throwable thrown) {
    logger.log(WARNING, "Exception thrown by a TimerTask", thrown);
  }
}