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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.driver.JPopupMenuDriverTest;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.KeyRecorder;
import org.fest.swing.testing.TestFrame;

import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.Pause.pause;
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
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    frame = new MyFrame();
    robot.showWindow(frame);
    assertThat(frame.isVisible()).isTrue();
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test(dataProvider = "clickingData") 
  public void shouldClickComponentWithGivenMouseButtonAndGivenNumberOfTimes(MouseButton button, int times) {
    ClickRecorder recorder = ClickRecorder.attachTo(frame.withoutPopup);
    robot.click(frame.withoutPopup, centerOf(frame.withoutPopup), button, times);
    assertThat(recorder).clicked(button).timesClicked(times);
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
  
  @Test public void shouldPressAndReleaseGivenKeys() {
    frame.withPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(frame.withPopup);
    int[] keys = { VK_A, VK_B, VK_Z };
    robot.pressAndReleaseKeys(keys);
    assertThat(recorder).keysPressed(keys).keysReleased(keys);
  }

  @Test public void shouldPressGivenKeyWithoutReleasingIt() {
    frame.withPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(frame.withPopup);
    robot.pressKey(VK_A);
    assertThat(recorder).keysPressed(VK_A).noKeysReleased();
  }

  @Test(dependsOnMethods = "shouldPressGivenKeyWithoutReleasingIt") 
  public void shouldReleaseGivenKey() {
    frame.withPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(frame.withPopup);
    robot.pressKey(VK_A);
    robot.releaseKey(VK_A);
    assertThat(recorder).keysReleased(VK_A);
  }

  @Test public void shouldShowPopupMenu() {
    JPopupMenu menu = robot.showPopupMenu(frame.withPopup);
    assertThat(menu).isSameAs(popupMenu());
    assertThat(menu.isVisible()).isTrue();
  }

  @Test public void shouldThrowErrorIfPopupNotFound() {
    try {
      robot.showPopupMenu(frame.withoutPopup);
      fail();
    } catch (ComponentLookupException expected) {
      assertThat(expected).message().contains("Unable to show popup")
                                    .contains("on javax.swing.JTextField")
                                    .contains("name='withoutPopup'");
    }
  }

  @Test(dependsOnMethods = "shouldShowPopupMenu")
  public void shouldReturnActivePopupMenu() {
    robot.showPopupMenu(frame.withPopup);
    JPopupMenu found = robot.findActivePopupMenu();
    assertThat(found).isSameAs(frame.popupMenu);
  }

  @Test public void shouldReturnNullIfActivePopupMenuNotFound() {
    JPopupMenu found = robot.findActivePopupMenu();
    assertThat(found).isNull();
  }

  @Test public void shouldCloseWindow() {
    final TestFrame w = new TestFrame(getClass());
    w.display();
    robot.close(w);
    pause(new Condition("Window closed") {
      @Override public boolean test() {
        return !w.isVisible();
      }
    });
    assertThat(w.isVisible()).isFalse();
  }
    
  private JPopupMenu popupMenu() {
    return frame.popupMenu;
  }
  
  @Test public void shouldPassIfNoJOptionPaneIsShowing() {
    robot.requireNoJOptionPaneIsShowing();
  }

  @Test public void shouldFailIfJOptionPaneIsShowingAndExpectingNotShowing() throws Exception {
    robot.click(frame.button);
    pause(500);
    try {
      robot.requireNoJOptionPaneIsShowing();
      fail("Expecting AssertionError");
    } catch (AssertionError e) {
      assertThat(e).message().contains("Expecting no JOptionPane to be showing");
    }
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextField withPopup = new JTextField("With Pop-up Menu");
    final JTextField withoutPopup = new JTextField("Without Pop-up Menu");
    final JButton button = new JButton("Click Me");
    final JPopupMenu popupMenu = new JPopupMenu("Pop-up Menu");

    MyFrame() {
      super(JPopupMenuDriverTest.class);
      add(withPopup);
      add(withoutPopup);
      add(button);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(MyFrame.this, "A Message");
        }
      });
      withPopup.setComponentPopupMenu(popupMenu);
      withoutPopup.setName("withoutPopup");
      popupMenu.add(new JMenuItem("Luke"));
      popupMenu.add(new JMenuItem("Leia"));
    }
  }
}
