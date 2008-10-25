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
import java.awt.Container;
import java.util.Collection;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.MDITestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.hierarchy.JInternalFrameIconifyTask.iconify;
import static org.fest.swing.testing.MDITestWindow.createAndDisplayInEDT;
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
    Container container = textField().createNew();
    assertThat(finder.nonExplicitChildrenOf(container)).isEmpty();
  }

  public void shouldReturnEmptyCollectionIfComponentIsNull() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }

  @Test(groups = GUI)
  public void shouldReturnIconifiedInternalFramesIfComponentIsJDesktopPane() {
    ScreenLock.instance().acquire(this);
    MDITestWindow window = createAndDisplayInEDT(getClass());
    iconify(window.internalFrame());
    Collection<Component> children = finder.nonExplicitChildrenOf(window.desktop());
    try {
      assertThat(children).containsOnly(window.internalFrame());
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }
}
