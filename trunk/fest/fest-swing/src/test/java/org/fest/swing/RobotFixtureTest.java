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
package org.fest.swing;


import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_Z;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.MouseButton.LEFT_BUTTON;
import static org.fest.swing.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.MouseButton.RIGHT_BUTTON;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link RobotFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class RobotFixtureTest {

  private RobotFixture robot;
  private MyFrame frame;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    frame = new MyFrame(getClass());
    robot.showWindow(frame);
    assertThat(frame.isVisible()).isTrue();
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldShowPopupMenuInTextFieldContainingPopupMenu() {
    JPopupMenu menu = robot.showPopupMenu(frame.textBoxWithPopupMenu);
    assertThat(menu).isSameAs(frame.popupMenu);
  }

  @Test(dataProvider = "clickingData") 
  public void shouldClickComponentWithGivenMouseButtonAndGivenNumberOfTimes(MouseButton button, int times) {
    ClickRecorder clickRecord = ClickRecorder.attachTo(frame.textBox);
    robot.click(frame.textBox, button, times);
    assertThat(clickRecord.clickedButton()).isEqualTo(button);
    assertThat(clickRecord.clickCount()).isEqualTo(times);
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
    frame.textBox.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(frame.textBox);
    int[] keys = { VK_A, VK_B, VK_Z };
    robot.pressAndReleaseKeys(keys);
    recorder.assertKeysPressed(keys);
    recorder.assertKeysReleased(keys);
  }

  @Test public void shouldPressGivenKeyWithoutReleasingIt() {
    frame.textBox.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(frame.textBox);
    robot.pressKey(VK_A);
    recorder.assertKeysPressed(VK_A);
    recorder.assertNoKeysReleased();
  }

  @Test(dependsOnMethods = "shouldPressGivenKeyWithoutReleasingIt") 
  public void shouldReleaseGivenKey() {
    frame.textBox.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(frame.textBox);
    robot.pressKey(VK_A);
    robot.releaseKey(VK_A);
    recorder.assertKeysReleased(VK_A);
  }

  @Test(expectedExceptions = ComponentLookupException.class)
  public void shouldNotShowPopupMenuInTextFieldNotContainingPopupMenu() {
    robot.showPopupMenu(frame.textBox);
  }

  @Test public void shouldSleepForTheGivenTime() {
    long start = System.currentTimeMillis();
    long delay = 2000;
    robot.delay(delay);
    long delta = System.currentTimeMillis() - start;
    assertThat(delta >= delay).isTrue();
  }
  
  @Test public void shouldCloseWindow() {
    final TestFrame w = new TestFrame(getClass());
    w.display();
    robot.close(w);
    robot.wait(new Condition("Window closed") {
      @Override public boolean test() {
        return !w.isVisible();
      }
    });
    assertThat(w.isVisible()).isFalse();
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private final JPopupMenu popupMenu = new JPopupMenu("Popup Menu");
    private final JTextField textBoxWithPopupMenu = new JTextField(20);
    private final JTextField textBox = new JTextField(20);
    
    MyFrame(Class testClass) {
      super(testClass);
      add(textBoxWithPopupMenu);
      add(textBox);
      textBoxWithPopupMenu.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("First"));
      popupMenu.add(new JMenuItem("Second"));
    }
  }
}
