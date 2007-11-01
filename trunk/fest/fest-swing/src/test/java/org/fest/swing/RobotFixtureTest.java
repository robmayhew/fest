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
import static org.fest.swing.Timeout.timeout;
import static org.fest.swing.util.StopWatch.startNewStopWatch;

import org.fest.swing.util.StopWatch;

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
    StopWatch watch = startNewStopWatch();
    long delay = 2000;
    robot.delay(delay);
    watch.stop();
    assertThat(watch.ellapsedTime() >= delay).isTrue();
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
  
  @Test public void shouldWaitTillConditionIsTrue() {
    class CustomCondition extends Condition {
      boolean satisfied;
      
      public CustomCondition(String description) {
        super(description);
      }

      @Override public boolean test() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        satisfied = true;
        return satisfied;
      }
    };
    CustomCondition condition = new CustomCondition("Some condition");
    StopWatch watch = startNewStopWatch();
    robot.wait(condition);
    watch.stop();
    assertThat(watch.ellapsedTime() >= 1000).isTrue();
    assertThat(condition.satisfied).isTrue();
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeoutIfConditionIsNeverTrue() {
    robot.wait(neverSatisfied());
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeoutWithGivenTimeIfConditionIsNeverTrue() {
    robot.wait(neverSatisfied(), timeout(100));
  }
  
  private NeverSatisfiedCondition neverSatisfied() {
    return new NeverSatisfiedCondition("Never satisfied");
  }
  
  private static class NeverSatisfiedCondition extends Condition {
    public NeverSatisfiedCondition(String description) {
      super(description);
    }

    @Override public boolean test() {
      return false;
    }
  };    
  
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
