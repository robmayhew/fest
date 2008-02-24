/*
 * Created on Feb 23, 2008
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
package org.fest.swing.task;

import java.awt.Component;

import javax.accessibility.AccessibleAction;
import javax.swing.Action;

import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands execution of the first <code>{@link Action}</code> in a <code>{@link Component}</code>'s
 * <code>{@link AccessibleAction}</code>.
 * 
 * @author Alex Ruiz
 */
public class PerformAccessibleActionTask implements Runnable {

  private final AccessibleAction action;

  /**
   * Creates a new </code>{@link PerformAccessibleActionTask}</code>.
   * @param c the <code>Component</code> containing the <code>AccessibleAction</code> to execute.
   * @throws ActionFailedException if the <code>Component</code> does not contain an <code>AccessibleAction</code>
   *         or if the <code>AccessibleAction</code> is empty.
   */
  public PerformAccessibleActionTask(Component c) {
    action = c.getAccessibleContext().getAccessibleAction();
    if (action == null || action.getAccessibleActionCount() == 0)
      throw actionFailure(concat("Unable to perform accessible action for ", format(c)));
  }

  /**
   * Executes the first <code>{@link Action}</code> in this task's <code>{@link AccessibleAction}</code>.
   */
  public void run() {
    action.doAccessibleAction(0);
  }
}
