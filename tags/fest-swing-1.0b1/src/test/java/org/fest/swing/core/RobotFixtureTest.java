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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.KeyRecorder;
import org.fest.swing.testing.TestWindow;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.RobotFixtureTest.KeyAction.action;
import static org.fest.swing.testing.ClickRecorder.attachTo;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.util.AWT.centerOf;

/**
 * Tests for <code>{@link org.fest.swing.core.RobotFixture}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class RobotFixtureTest {

  private Robot robot;
  private MyFrame frame;
  private JTextField textFieldWithPopup;
  private JTextField textFieldWithoutPopup;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    frame = new MyFrame();
    textFieldWithPopup = frame.textFieldWithPopup;
    textFieldWithoutPopup = frame.textFieldWithoutPopup;
    robot.showWindow(frame); // implicitly test 'showWindow(Window)'
    assertThat(frame.isVisible()).isTrue();
    assertThat(frame.getLocationOnScreen()).isEqualTo(new Point(100, 100));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorIfWindowNeverShown() {
    try {
      robot.showWindow(new JFrame() {
        private static final long serialVersionUID = 1L;

        @Override public void setVisible(boolean b) {
          super.setVisible(false);
        }
      });
      fail();
    } catch (WaitTimedOutError e) {
      assertThat(e).message().contains("Timed out waiting for Window to open");
    }
  }

  public void shouldNotPackWindowAsSpecified() {
    class MyWindow extends JWindow {
      private static final long serialVersionUID = 1L;
      private boolean packed;

      @Override public void pack() {
        packed = true;
        super.pack();
      }

      boolean packed() { return packed; }
    }
    Dimension size = new Dimension(100, 100);
    MyWindow window = new MyWindow();
    robot.showWindow(window, size, false);
    assertThat(window.getSize()).isEqualTo(size);
    assertThat(window.packed()).isFalse();
    assertThat(window.getLocationOnScreen()).isEqualTo(new Point(0, 0));
  }

  public void shouldClickComponent() {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1);
  }

  public void shouldRightClickComponent() {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.rightClick(textFieldWithoutPopup);
    assertThat(recorder).clicked(RIGHT_BUTTON).timesClicked(1);
  }

  public void shouldDoubleClickComponent() {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.doubleClick(textFieldWithoutPopup);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(2);
  }

  public void shouldClickComponentOnceWithLeftButtonAtGivenPoint() {
    Point p = new Point(10, 10);
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup, p);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1).clickedAt(p);
  }

  @Test(groups = GUI, dataProvider = "mouseButtons")
  public void shouldClickComponentOnceWithGivenButton(MouseButton button) {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup, button);
    assertThat(recorder).clicked(button).timesClicked(1);
  }

  @DataProvider(name = "mouseButtons")
  public Object[][] mouseButtons() {
    return new Object[][] {
        { LEFT_BUTTON },
        { MIDDLE_BUTTON },
        { RIGHT_BUTTON }
    };
  }

  @Test(groups = GUI, dataProvider = "clickingData")
  public void shouldClickComponentWithGivenMouseButtonAndGivenNumberOfTimes(MouseButton button, int times) {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup, button, times);
    assertThat(recorder).clicked(button).timesClicked(times).clickedAt(centerOf(textFieldWithoutPopup));
  }

  @Test(groups = GUI, dataProvider = "clickingData")
  public void shouldClickComponentWithGivenMouseButtonAndGivenNumberOfTimesAtGivenPoint(MouseButton button, int times) {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    Point point = new Point(10, 10);
    robot.click(textFieldWithoutPopup, point, button, times);
    assertThat(recorder).clicked(button).timesClicked(times).clickedAt(point);
  }

  @DataProvider(name = "clickingData")
  public Object[][] clickingData() {
    return new Object[][] {
        { LEFT_BUTTON, 1 },
        { LEFT_BUTTON, 2 },
        { MIDDLE_BUTTON, 1 },
        { MIDDLE_BUTTON, 2 },
        { RIGHT_BUTTON, 1 },
        { RIGHT_BUTTON, 2 },
    };
  }

  public void shouldPressAndReleaseGivenKeys() {
    textFieldWithPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(textFieldWithPopup);
    int[] keys = { VK_A, VK_B, VK_Z };
    robot.pressAndReleaseKeys(keys);
    assertThat(recorder).keysPressed(keys).keysReleased(keys);
  }

  public void shouldPressKeyAndModifiers() {
    textFieldWithPopup.requestFocusInWindow();
    KeyPressRecorder recorder = KeyPressRecorder.attachTo(textFieldWithPopup);
    robot.pressAndReleaseKey(VK_C, new int[] { CTRL_MASK, SHIFT_MASK });
    List<KeyAction> actions = recorder.actions;
    assertThat(actions).containsOnly(
        action(KEY_PRESSED,  VK_SHIFT),
        action(KEY_PRESSED,  VK_CONTROL),
        action(KEY_PRESSED,  VK_C),
        action(KEY_RELEASED, VK_C),
        action(KEY_RELEASED, VK_CONTROL),
        action(KEY_RELEASED, VK_SHIFT)
    );
  }

  public void shouldPressGivenKeyWithoutReleasingIt() {
    textFieldWithPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(textFieldWithPopup);
    robot.pressKey(VK_A);
    assertThat(recorder).keysPressed(VK_A).noKeysReleased();
  }

  public void shouldReleaseGivenKey() {
    textFieldWithPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(textFieldWithPopup);
    robot.pressKey(VK_A);
    robot.releaseKey(VK_A);
    assertThat(recorder).keysReleased(VK_A);
  }

  public void shouldShowPopupMenu() {
    JPopupMenu menu = robot.showPopupMenu(textFieldWithPopup);
    assertThat(menu).isSameAs(popupMenu());
    assertThat(menu.isVisible()).isTrue();
  }

  public void shouldThrowErrorIfPopupNotFound() {
    try {
      robot.showPopupMenu(textFieldWithoutPopup);
      fail();
    } catch (ComponentLookupException expected) {
      assertThat(expected).message().contains("Unable to show popup")
                                    .contains("on javax.swing.JTextField")
                                    .contains("name='withoutPopup'");
    }
  }

  public void shouldReturnActivePopupMenu() {
    robot.showPopupMenu(textFieldWithPopup);
    JPopupMenu found = robot.findActivePopupMenu();
    assertThat(found).isSameAs(frame.popupMenu);
  }

  public void shouldReturnNullIfActivePopupMenuNotFound() {
    JPopupMenu found = robot.findActivePopupMenu();
    assertThat(found).isNull();
  }

  public void shouldCloseWindow() {
    final TestWindow w = new TestWindow(getClass());
    w.display();
    robot.close(w);
    pause(new Condition("Window closed") {
      @Override public boolean test() {
        return !w.isVisible();
      }
    });
    assertThat(w.isVisible()).isFalse();
  }

  public void shouldNotCloseWindowIfWindowNotShowing() {
    TestWindow w = new TestWindow(getClass());
    w.display();
    w.setVisible(false);
    assertThat(w.isShowing()).isFalse();
    robot.close(w);
    assertThat(w.isShowing()).isFalse();
  }

  public void shouldGiveFocus() {
    giveFocusAndVerifyThatHasFocus(textFieldWithPopup);
    giveFocusAndVerifyThatHasFocus(textFieldWithoutPopup);
  }

  private void giveFocusAndVerifyThatHasFocus(Component c) {
    robot.focus(c);
    pause(500);
    assertThat(c.isFocusOwner()).isTrue();
  }

  public void shouldGiveFocusAndWaitUntilComponentHasFocus() {
    robot.focusAndWaitForFocusGain(textFieldWithPopup);
    assertThat(textFieldWithPopup.isFocusOwner()).isTrue();
    robot.focusAndWaitForFocusGain(textFieldWithoutPopup);
    assertThat(textFieldWithoutPopup.isFocusOwner()).isTrue();
  }

  private JPopupMenu popupMenu() {
    return frame.popupMenu;
  }

  public void shouldPassIfNoJOptionPaneIsShowing() {
    robot.requireNoJOptionPaneIsShowing();
  }

  public void shouldFailIfJOptionPaneIsShowingAndExpectingNotShowing() throws Exception {
    robot.click(frame.button);
    pause(500);
    try {
      robot.requireNoJOptionPaneIsShowing();
      fail("Expecting AssertionError");
    } catch (AssertionError e) {
      assertThat(e).message().contains("Expecting no JOptionPane to be showing");
    }
  }

  static class KeyPressRecorder extends KeyAdapter {
    final List<KeyAction> actions = new ArrayList<KeyAction>();

    static KeyPressRecorder attachTo(Component c) {
      KeyPressRecorder recorder = new KeyPressRecorder();
      c.addKeyListener(recorder);
      return recorder;
    }
    
    @Override public void keyPressed(KeyEvent e) {
      actions.add(action(KEY_PRESSED, e.getKeyCode()));
    }

    @Override public void keyReleased(KeyEvent e) {
      actions.add(action(KEY_RELEASED, e.getKeyCode()));
    }    
  }
  
  static class KeyAction {
    final int type;
    final int keyCode;

    static KeyAction action(int type, int keyCode) {
      return new KeyAction(type, keyCode);
    }
    
    private KeyAction(int type, int keyCode) {
      this.type = type;
      this.keyCode = keyCode;
    }
    
    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      final KeyAction other = (KeyAction) obj;
      if (type != other.type) return false;
      return keyCode == other.keyCode;
    }

    @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + type;
      result = prime * result + keyCode;
      return result;
    }
    
    @Override public String toString() {
      StringBuilder b = new StringBuilder();
      b.append("type=").append(type).append(", ");
      b.append("keyCode=").append(keyCode).append("]");
      return b.toString();
    }
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textFieldWithPopup = new JTextField("With Pop-up Menu");
    final JTextField textFieldWithoutPopup = new JTextField("Without Pop-up Menu");
    final JButton button = new JButton("Click Me");
    final JPopupMenu popupMenu = new JPopupMenu("Pop-up Menu");

    MyFrame() {
      super(RobotFixtureTest.class);
      add(textFieldWithPopup);
      add(textFieldWithoutPopup);
      add(button);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(MyFrame.this, "A Message");
        }
      });
      textFieldWithPopup.setComponentPopupMenu(popupMenu);
      textFieldWithoutPopup.setName("withoutPopup");
      popupMenu.add(new JMenuItem("Luke"));
      popupMenu.add(new JMenuItem("Leia"));
    }
  }
}
