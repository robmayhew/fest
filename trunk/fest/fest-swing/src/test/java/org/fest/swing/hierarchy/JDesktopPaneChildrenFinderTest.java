/*
 * Created on Oct 25, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.util.Collection;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.hierarchy.JInternalFrameIconifyTask.iconifyTask;
import static org.fest.swing.hierarchy.MDIFrame.showInTest;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JDesktopPaneChildrenFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JDesktopPaneChildrenFinderTest {

  private JDesktopPaneChildrenFinder finder;
  
  @BeforeMethod public void setUp() {
    finder = new JDesktopPaneChildrenFinder();
  }
  
  public void shouldReturnEmptyCollectionIfComponentIsNotJDesktopPane() {
    assertThat(finder.nonExplicitChildrenOf(newJTextField())).isEmpty();
  }

  private static JTextField newJTextField() {
    return execute(new GuiQuery<JTextField>() {
      protected JTextField executeInEDT() {
        return new JTextField();
      }
    });
  }
  
  public void shouldReturnEmptyCollectionIfComponentIsNull() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }
  
  @Test(groups = GUI) 
  public void shouldReturnIconifiedInternalFramesIfComponentIsJDesktopPane() {
    MDIFrame frame = showInTest(getClass());
    iconify(frame.internalFrame());
    Collection<Component> children = finder.nonExplicitChildrenOf(frame.desktop());
    try {
      assertThat(children).containsOnly(frame.internalFrame());
    } finally {
      frame.destroy();
    }
  }

  private static void iconify(JInternalFrame internalFrame) {
    execute(iconifyTask(internalFrame));
  }
}
