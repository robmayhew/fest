/*
 * Created on Oct 26, 2007
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.hierarchy.MDIFrame.showInTest;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Collections.list;

/**
 * Tests for <code>{@link ChildrenFinder}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 *
 * TODO access components in EDT
 */
public class ChildrenFinderTest {

  private ChildrenFinder finder;

  private JDesktopPaneChildrenFinder desktopPaneChildrenFinder;
  private JMenuChildrenFinder menuChildrenFinder;
  private WindowChildrenFinder windowChildrenFinder;

  @BeforeMethod public void setUp() {
    finder = new ChildrenFinder();
    desktopPaneChildrenFinder = new JDesktopPaneChildrenFinder();
    menuChildrenFinder = new JMenuChildrenFinder();
    windowChildrenFinder = new WindowChildrenFinder();
  }

  @Test public void shouldReturnIconifiedInternalFramesIfComponentIsJDesktopPane() throws Exception {
    MDIFrame frame = showInTest(getClass());
    frame.internalFrame().setIcon(true);
    JDesktopPane desktop = frame.desktop();
    assertThat(finder.childrenOf(desktop)).containsOnly(childrenOf(desktop));
    frame.destroy();
  }

  @Test public void shouldReturnPopupMenuIfComponentIsJMenu() {
    JMenu menu = new JMenu();
    assertThat(finder.childrenOf(menu)).containsOnly(childrenOf(menu));
  }

  @Test(groups = GUI) public void shouldReturnOwnedWindowsIfComponentIsWindow() {
    TestWindow frame = TestWindow.showNewInTest(getClass());
    TestDialog dialog = TestDialog.showInTest(frame);
    dialog.display();
    assertThat(finder.childrenOf(frame)).containsOnly(childrenOf(frame));
    dialog.destroy();
    frame.destroy();
  }

  @Test(groups = GUI) public void shouldReturnChildrenOfContainer() {
    TestWindow frame = new TestWindow(getClass());
    JTextField textField = new JTextField();
    frame.add(textField);
    frame.display();
    assertThat(finder.childrenOf(frame.getContentPane())).containsOnly(textField);
    frame.destroy();
  }

  private Object[] childrenOf(Container c) {
    List<Component> children = new ArrayList<Component>();
    children.addAll(list(c.getComponents()));
    children.addAll(desktopPaneChildrenFinder.nonExplicitChildrenOf(c));
    children.addAll(menuChildrenFinder.nonExplicitChildrenOf(c));
    children.addAll(windowChildrenFinder.nonExplicitChildrenOf(c));
    return children.toArray();
  }
}
