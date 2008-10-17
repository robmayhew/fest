/*
 * Created on Aug 14, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.edt;

import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.timing.Condition;

import static javax.swing.SwingUtilities.*;

import static org.fest.swing.exception.UnexpectedException.unexpected;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

/**
 * Understands running instances of <code>{@link GuiQuery}</code> and <code>{@link GuiTask}</code>.
 *
 * @author Alex Ruiz
 */
public class GuiActionRunner {

  /**
   * Executes the given query in the event dispatch thread. This method waits until the query has finished its
   * execution.
   * @param <T> the generic type of the return value.
   * @param query the query to execute.
   * @return the result of the query executed in the main thread.
   * @throws UnexpectedException wrapping any exception thrown when executing the given query in the event dispatch
   * thread.
   */
  public static <T> T execute(GuiQuery<T> query) {
    run(query, untilExecuted(query));
    return resultOf(query);
  }

  /**
   * Executes the given task in the event dispatch thread. This method waits until the task has finished its execution.
   * @param task the task to execute.
   * @throws UnexpectedException wrapping any exception thrown when executing the given task in the event dispatch
   * thread.
   */
  public static void execute(GuiTask task) {
    run(task, untilExecuted(task));
    rethrowCatchedExceptionIn(task);
  }

  /**
   * Executes the given task in the event dispatch thread. This method waits until:
   * <ol>
   * <li>the task has finished its execution <strong>*and*</strong></li>
   * <li>the given condition has been satisfied</li>
   * </ol>
   * @param task the task to execute.
   * @param toWaitFor the condition to meet to finish the execution of the given task.
   * @throws UnexpectedException wrapping any exception thrown when executing the given task in the event dispatch
   * thread.
   */
  public static void execute(GuiTask task, Condition toWaitFor) {
    run(task, untilExecuted(task), toWaitFor);
    rethrowCatchedExceptionIn(task);
  }

  private static void run(GuiAction action, Condition... toWaitFor) {
    if (isEventDispatchThread()) {
      action.run();
    } else {
      invokeLater(action);
    }
    pause(toWaitFor);
  }

  private static <T> T resultOf(GuiQuery<T> query) {
    T result = query.result();
    query.clearResult();
    rethrowCatchedExceptionIn(query);
    return result;
  }

  /**
   * Wraps (with a <code>{@link UnexpectedException}</code>) and retrows any catched exception in the given action.
   * @param action the given action that may have a catched exception during its execution.
   * @throws UnexpectedException wrapping any catched exception during the execution of the given action.
   */
  private static void rethrowCatchedExceptionIn(GuiAction action) {
    Throwable catchedException = action.catchedException();
    action.clearCatchedException();
    if (catchedException != null) throw unexpected(catchedException);
  }

  private static ActionExecutedCondition untilExecuted(GuiAction action) {
    return new ActionExecutedCondition(action);
  }

  private static class ActionExecutedCondition extends Condition {
    private GuiAction action;

    ActionExecutedCondition(GuiAction action) {
      super(concat("action ", actionTypeName(action), " to be executed in Swing's event dispatch thread"));
      this.action = action;
    }

    private static String actionTypeName(GuiAction action) {
      return action.getClass().getName();
    }

    public boolean test() {
      return action.wasExecutedInEDT();
    }

    /** ${@inheritDoc} */
    @Override public void done() {
      action = null;
    }
  }
}
