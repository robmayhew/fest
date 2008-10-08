/*
 * Created on Aug 21, 2008
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

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.MenuElement;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JPopupMenuElementsQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JPopupMenuElementsQueryTest {

  private Robot robot;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnMenuElementsFromJPopupMenu() {
    robot.showPopupMenu(window.textField);
    MenuElement[] elements = JPopupMenuElementsQuery.elementsOf(window.popupMenu);
    assertThat(elements).hasSize(2);
    assertThatMenuElementHasText(elements[0], "One");
    assertThatMenuElementHasText(elements[1], "Two");
    assertThat(window.popupMenu.methodGetSubElementsWasInvoked()).isTrue();
  }

  private void assertThatMenuElementHasText(MenuElement element, String text) {
    JMenuItem menuItem = (JMenuItem) element;
    assertThat(menuItem.getText()).isEqualTo(text);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField(20);
    final MyPopupMenu popupMenu;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JPopupMenuElementsQueryTest.class);
      popupMenu = new MyPopupMenu();
      populate(popupMenu);
      textField.setComponentPopupMenu(popupMenu);
      addComponents(textField);
    }

    private static void populate(JPopupMenu popupMenu) {
      popupMenu.add(new JMenuItem("One"));
      popupMenu.add(new JMenuItem("Two"));
    }
  }

  private static class MyPopupMenu extends JPopupMenu {
    private static final long serialVersionUID = 1L;

    private boolean methodGetSubElementsInvoked;

    @Override public MenuElement[] getSubElements() {
      methodGetSubElementsInvoked = true;
      return super.getSubElements();
    }

    boolean methodGetSubElementsWasInvoked() { return methodGetSubElementsInvoked; }
  }
}
