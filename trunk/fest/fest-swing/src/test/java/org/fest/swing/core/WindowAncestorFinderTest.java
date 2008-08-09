/*
 * Created on Apr 1, 2008
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

import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentParentTaskQuery.parentOf;

/**
 * Tests for <code>{@link WindowAncestorFinder}</code>.
 *
 * @author Yvonne Wang
 */
public class WindowAncestorFinderTest {

  private MyFrame frame;

  @BeforeMethod public void setUp() {
    frame = new MyFrame();
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test public void shouldFindWindowAncestor() {
    Window ancestor = WindowAncestorFinder.ancestorOf(frame.button);
    assertThat(ancestor).isSameAs(frame);
  }

  @Test public void shouldReturnNullIfComponentIsNull() {
    assertThat(WindowAncestorFinder.ancestorOf(null)).isSameAs(null);
  }

  @Test public void shouldReturnWindowAsItsOwnAncestor() {
    Window ancestor = WindowAncestorFinder.ancestorOf(frame);
    assertThat(ancestor).isSameAs(frame);
  }

  @Test public void shouldReturnInvokerAncestorAsAncestorIfComponentIsMenuElement() {
    Robot robot = RobotFixture.robotWithCurrentAwtHierarchy();
    robot.showWindow(frame);
    robot.showPopupMenu(frame.textField);
    Window ancestor = WindowAncestorFinder.ancestorOf(frame.popupMenu);
    assertThat(ancestor).isSameAs(frame);
    robot.cleanUp();
  }

  @Test public void shouldReturnParentAsAncestorIfComponentIsMenuElementAndInvokerIsNull() {
    Window ancestor = WindowAncestorFinder.ancestorOf(frame.popupMenu);
    assertThat(ancestor).isSameAs(parentOf(frame.popupMenu));
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click Me");
    final JTextField textField = new JTextField(20);
    final JPopupMenu popupMenu = new JPopupMenu();

    MyFrame() {
      super(WindowAncestorFinderTest.class);
      add(button);
      add(textField);
      textField.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("Frodo"));
    }
  }

}
