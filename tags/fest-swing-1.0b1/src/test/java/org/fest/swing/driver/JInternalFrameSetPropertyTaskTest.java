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
package org.fest.swing.driver;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import org.testng.annotations.Test;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JInternalFrameAction.MAXIMIZE;

/**
 * Tests for <code>{@link JInternalFrameSetPropertyTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JInternalFrameSetPropertyTaskTest {

  public void shouldSavePropertyVetoExceptionThrown() {
    final PropertyVetoException toThrow = new PropertyVetoException("Testing", createMock(PropertyChangeEvent.class));
    JInternalFrameSetPropertyTask task = new JInternalFrameSetPropertyTask(new JInternalFrame(), MAXIMIZE) {
      void execute() throws PropertyVetoException {
        throw toThrow;
      }
    };
    task.run();
    assertThat(task.veto().cause()).isSameAs(toThrow);
  }
  
  public void shouldReturnNullVetoIfPropertyVetoExceptionNeverThrown() {
    JInternalFrameSetPropertyTask task = new JInternalFrameSetPropertyTask(new JInternalFrame(), MAXIMIZE) {
      void execute() {}
    };
    task.run();
    assertThat(task.veto()).isNull();
  }
}
