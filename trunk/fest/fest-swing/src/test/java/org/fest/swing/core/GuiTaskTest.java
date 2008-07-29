/*
 * Created on Jul 29, 2008
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

import javax.swing.SwingUtilities;

import org.testng.annotations.Test;

import org.fest.swing.core.GuiTask.Reference;

import static javax.swing.SwingUtilities.isEventDispatchThread;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link GuiTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test public class GuiTaskTest {

  public void shouldExecuteInEDTWhenNotCalledInEDT() {
    GuiTaskInEDT task = new GuiTaskInEDT();
    assertThat(isEventDispatchThread()).isFalse();
    boolean ranFromEDT = task.run();
    assertThat(task.calledFromEDT()).isFalse();
    assertThat(ranFromEDT).isTrue();
  }

  public void shouldExecuteInEDTWhenCalledInEDT() throws Exception {
    final GuiTaskInEDT task = new GuiTaskInEDT();
    final Reference<Boolean> ranFromEDTReference = new Reference<Boolean>();
    SwingUtilities.invokeAndWait(new Runnable() {
      public void run() {
        ranFromEDTReference.target = task.run();
      }
    });
    assertThat(task.calledFromEDT()).isTrue();
    assertThat(ranFromEDTReference.target).isEqualTo(true);
  }

  private static class GuiTaskInEDT extends GuiTask<Boolean> {
    protected Boolean executeInEDT() {
      return isEventDispatchThread();
    }
  }
}
