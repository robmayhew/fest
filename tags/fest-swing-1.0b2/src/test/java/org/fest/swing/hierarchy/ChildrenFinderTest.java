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

import org.fest.swing.core.ScreenLock;
import org.fest.swing.factory.JMenus;
import org.fest.swing.testing.MDITestWindow;
import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.hierarchy.ContainerComponentsQuery.componentsOf;
import static org.fest.swing.hierarchy.JFrameContentPaneQuery.contentPaneOf;
import static org.fest.swing.hierarchy.JInternalFrameIconifyTask.iconify;
import static org.fest.swing.testing.MDITestWindow.showInTest;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link ChildrenFinder}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class ChildrenFinderTest {

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

  @Test(groups = GUI)
  public void shouldReturnIconifiedInternalFramesIfComponentIsJDesktopPane() {
    ScreenLock.instance().acquire(this);
    MDITestWindow window = showInTest(getClass());
    iconify(window.internalFrame());
    JDesktopPane desktop = window.desktop();
    try {
      assertThat(finder.childrenOf(desktop)).containsOnly(childrenOf(desktop));
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnPopupMenuIfComponentIsJMenu() {
    JMenu menu = JMenus.menu().createNew();
    assertThat(finder.childrenOf(menu)).containsOnly(childrenOf(menu));
  }

  @Test(groups = GUI)
  public void shouldReturnOwnedWindowsIfComponentIsWindow() {
    ScreenLock.instance().acquire(this);
    TestWindow window = TestWindow.showNewInTest(ChildrenFinderTest.class);
    TestDialog dialog = TestDialog.showInTest(window);
    try {
      assertThat(finder.childrenOf(window)).containsOnly(childrenOf(window));
    } finally {
      dialog.destroy();
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  @Test(groups = GUI)
  public void shouldReturnChildrenOfContainer() {
    ScreenLock.instance().acquire(this);
    MyWindow frame = MyWindow.createAndShow();
    try {
      assertThat(finder.childrenOf(contentPaneOf(frame))).containsOnly(frame.textField);
    } finally {
      frame.destroy();
      ScreenLock.instance().release(this);
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createAndShow() {
      MyWindow window = new MyWindow();
      window.display();
      return window;
    }

    final JTextField textField = new JTextField(20);

    private MyWindow() {
      super(ChildrenFinderTest.class);
      addComponents(textField);
    }
  }

  private Object[] childrenOf(Container c) {
    List<Component> children = new ArrayList<Component>();
    children.addAll(componentsOf(c));
    children.addAll(desktopPaneChildrenFinder.nonExplicitChildrenOf(c));
    children.addAll(menuChildrenFinder.nonExplicitChildrenOf(c));
    children.addAll(windowChildrenFinder.nonExplicitChildrenOf(c));
    return children.toArray();
  }
}
