/*
 * Created on Oct 23, 2008
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
package org.fest.swing.driver;

import java.awt.Component;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands validation methods related to the state of a <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 */
public final class ComponentStateValidator {

  /**
   * Asserts that the <code>{@link Component}</code> is enabled. Unlike
   * <code>{@link ComponentDriver#requireEnabled(Component)}</code>, this method is for internal use only, to be used to
   * verify that a component is enabled before performing any action on it. <b>Note:</b> this method is <b>not</b>
   * executed in the event dispatch thread. Callers are responsible for calling this method in the event dispatch
   * thread.
   * @param c the target component.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public static void inEdtValidateIsEnabled(final Component c) {
    try {
      execute(new GuiTask() {
        protected void executeInEDT() {
          validateIsEnabled(c);
        }
      });
    } catch (UnexpectedException unexpected) {
      throw unexpected.bomb();
    }
  }

  /**
   * Asserts that the <code>{@link Component}</code> is enabled. Unlike
   * <code>{@link ComponentDriver#requireEnabled(Component)}</code>, this method is for internal use only, to be used to
   * verify that a component is enabled before performing any action on it. <b>Note:</b> this method is <b>not</b>
   * executed in the event dispatch thread. Callers are responsible for calling this method in the event dispatch
   * thread.
   * @param c the target component.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInCurrentThread
  public static void validateIsEnabled(Component c) {
    if (!c.isEnabled()) throw actionFailure(concat("Expecting component ", format(c), " to be enabled"));
  }

  private ComponentStateValidator() {}
}
