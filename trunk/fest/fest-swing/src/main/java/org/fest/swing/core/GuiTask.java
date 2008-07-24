/*
 * Created on Jul 22, 2008
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
package org.fest.swing.core;

import org.fest.swing.exception.UnexpectedException;

import static javax.swing.SwingUtilities.*;

import static org.fest.swing.exception.UnexpectedException.unexpected;

/**
 * Understands executing an action in the event dispatch thread.
 * @param <T> the return type of the action to execute.
 *  
 * @author Alex Ruiz
 */
public abstract class GuiTask<T> {

  /**
   * Executes an action in the event dispatch thread. This method waits until the action has finish its execution.
   * @return the result of the action executed in the main thread.
   * @throws UnexpectedException wrapping any exception thrown when executing an action in the event dispatch thread.
   */
  public final T run() {
    if (isEventDispatchThread()) return executeInEDT();
    final Reference<T> result = new Reference<T>();
    final Reference<Throwable> error = new Reference<Throwable>();
    try {
      invokeAndWait(new Runnable() {
        public void run() {
          try {
            result.target = executeInEDT();
          } catch (Exception e) {
            error.target = e;
          }
        }
      });
    } catch (Exception e) {
      throw unexpected(e);
    }
    if (error.target != null) throw unexpected(error.target);
    return result.target;
  }
  
  /**
   * Specifies the action to execute in the event dispatch thread.
   * @return the result of the execution of the action.
   */
  protected abstract T executeInEDT();
  
  private static class Reference<T> {
    T target;
    
    Reference() {}
  }
}
