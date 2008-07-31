/*
 * Created on Sep 5, 2007
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
package org.fest.swing.driver;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.task.GetAbstractButtonTextTask.textOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JPopupMenuDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Price
 */
@Test(groups = GUI)
public class JPopupMenuDriverTest {

  private Robot robot;
  private JPopupMenuDriver driver;
  private MyFrame frame;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    frame = new GuiTask<MyFrame>() {
      protected MyFrame executeInEDT() throws Throwable {
        return new MyFrame();
      }
    }.run();
    robot.showWindow(frame);
    driver = new JPopupMenuDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTextOfMenuItem() {
    String s = JPopupMenuDriver.asString(new JMenuItem("Hello"));
    assertThat(s).isEqualTo("Hello");
  }

  public void shouldReturnDashIfNotMenuItem() {
    class MyMenuElement implements MenuElement {
      private final JButton button = new JButton();

      public Component getComponent() {
        return button;
      }

      public MenuElement[] getSubElements() { return null; }
      public void menuSelectionChanged(boolean isIncluded) {}
      public void processKeyEvent(KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {}
      public void processMouseEvent(MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {}
    }
    MenuElement e = new GuiTask<MenuElement>() {
      protected MenuElement executeInEDT() throws Throwable {
        return new MyMenuElement();
      }
    }.run();
    assertThat(JPopupMenuDriver.asString(e)).isEqualTo("-");
  }

  public void shouldReturnsPopupLabels() {
    String[] labels = driver.menuLabelsOf(popupMenu());
    assertThat(labels).isEqualTo(array("First", "Second"));
  }

  public void shouldFindMenuItemByName() {
    JMenuItem found = driver.menuItem(popupMenu(), "first");
    assertThat(found).isSameAs(frame.menuItem1);
  }

  public void shouldFindMenuItemWithGivenMatcher() {
    JMenuItem found = driver.menuItem(popupMenu(), new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "Second".equals(textOf(menuItem));
      }
    });
    assertThat(found).isSameAs(frame.menuItem2);
  }

  private JPopupMenu popupMenu() {
    return frame.popupMenu;
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField withPopup = new JTextField("With Pop-up Menu");
    
    final JPopupMenu popupMenu = new JPopupMenu("Pop-up Menu");
    
    final JMenuItem menuItem1 = new JMenuItem("First");
    final JMenuItem menuItem2 = new JMenuItem("Second");

    MyFrame() {
      super(JPopupMenuDriverTest.class);
      add(withPopup);
      withPopup.setComponentPopupMenu(popupMenu);
      popupMenu.add(menuItem1);
      menuItem1.setName("first");
      popupMenu.add(menuItem2);
    }
  }
}
