/*
 * Created on Jul 3, 2007
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
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JButton;

import abbot.tester.ComponentTester;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_Z;
import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.swing.SwingUtilities.getWindowAncestor;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.core.Timeout.timeout;
import static org.fest.swing.fixture.ErrorMessageAssert.actual;
import static org.fest.swing.fixture.ErrorMessageAssert.expected;
import static org.fest.swing.fixture.ErrorMessageAssert.property;
import static org.fest.swing.fixture.MouseClickInfo.leftButton;
import static org.fest.swing.fixture.MouseClickInfo.middleButton;
import static org.fest.swing.fixture.MouseClickInfo.rightButton;

import org.fest.swing.core.Condition;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.KeyRecorder;
import org.fest.swing.testing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Understands test methods for subclasses of <code>{@link ComponentFixture}</code>.
 * @param <T> the type of component tested by this test class. 
 *
 * @author Alex Ruiz
 */
public abstract class ComponentFixtureTestCase<T extends Component> {

  private static final String ENABLED = "enabled";
  private static final String VISIBLE = "visible";
  
  protected static class MainWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    public final JButton button = new JButton("Some Button");

    private final ComponentTester tester = new ComponentTester();
    
    MainWindow(Class testClass) {
      super(testClass);
      button.setName("button");
      add(button);
    }
    
    public void clickButton() {
      tester.click(button);      
    }
  }

  private RobotFixture robot;
  private MainWindow window;
  private ComponentFixture<T> fixture;
  
  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow(getClass());
    window.setSize(new Dimension(300, 200));
    T target = createTarget();
    addToWindow(target);
    robot().showWindow(window);
    createFixture(target); 
    afterSetUp();
    moveToUnblockMainWindow(target);
    giveFocusToButton();
  }
  
  protected abstract T createTarget();

  private void createFixture(T target) {
    fixture = createFixture(); 
    assertThat(fixture.target).isSameAs(target);
  }

  private void addToWindow(T target) {
    if (addTargetToWindow()) window.add(decorateBeforeAddingToWindow(target));
  }

  protected abstract ComponentFixture<T> createFixture();

  protected boolean addTargetToWindow() { return true; }
  
  protected Component decorateBeforeAddingToWindow(T target) { return target; }
  
  private void moveToUnblockMainWindow(T target) {
    if (!targetBlocksMainWindow()) return;
    Rectangle mainWindowBounds = window.getBounds();
    Window targetWindow = windowAncestorOf(target);
    Rectangle targetBounds = targetWindow.getBounds();
    targetBounds.y = mainWindowBounds.y + mainWindowBounds.height + 10;
    targetWindow.setBounds(targetBounds);
  }

  private Window windowAncestorOf(T target) {
    if (target instanceof Window) return (Window)target;
    return getWindowAncestor(target);
  }
  
  protected void afterSetUp() {}
  
  @Test public final void shouldClickComponent() {
    ClickRecorder recorder = ClickRecorder.attachTo(fixture.target);
    fixture.click();
    assertThat(recorder).wasClicked();
  }
  
  @Test public final void shouldClickComponentWithGivenButton() {
    ClickRecorder recorder = ClickRecorder.attachTo(fixture.target);
    fixture.click(MIDDLE_BUTTON);
    assertThat(recorder).clicked(MIDDLE_BUTTON).timesClicked(1);
  }

  @Test(dataProvider = "mouseClickInfos")
  public final void shouldClickComponentWithGivenInfo(MouseClickInfo info) {
    ClickRecorder recorder = ClickRecorder.attachTo(fixture.target);
    fixture.click(info);
    assertThat(recorder).clicked(info.button()).timesClicked(info.times());
  }

  @DataProvider(name = "mouseClickInfos")
  public Object[][] mouseClickInfos() {
    return new Object[][] {
        { leftButton().times(1) },
        { leftButton().times(2) },
        { middleButton().times(2) },
        { rightButton().times(1) }
    };
  }
  
  @Test public final void shouldRightClickComponent() {
    ClickRecorder recorder = ClickRecorder.attachTo(fixture.target);
    fixture.rightClick();
    assertThat(recorder).wasRightClicked();
  }

  @Test public final void shouldDoubleClickComponent() {
    ClickRecorder recorder = ClickRecorder.attachTo(fixture.target);
    fixture.doubleClick();
    assertThat(recorder).wasDoubleClicked();
  }  
  
  @Test public final void shouldGiveFocusToComponent() {
    T target = fixture.target;
    if (target.hasFocus()) giveFocusToButton();
    fixture.focus();
    assertThat(target.hasFocus()).isTrue();
  }

  private void giveFocusToButton() {
    if (targetBlocksMainWindow()) window.clickButton();
    giveFocusTo(window.button);
  }

  protected boolean targetBlocksMainWindow() { return false; }
  
  protected final void giveFocusTo(final Component c) {
    c.requestFocusInWindow();
    robot().wait(new Condition("component has focus") {
      public boolean test() {
        return c.hasFocus();
      }
    });
  }
  
  @Test public final void shouldPressAndReleaseGivenKeys() {
    KeyRecorder recorder = KeyRecorder.attachTo(fixture.target);
    int[] keys = { VK_A, VK_B, VK_Z };
    fixture.pressAndReleaseKeys(keys);
    assertThat(recorder).keysPressed(keys).keysReleased(keys);
  }

  @Test public final void shouldPressGivenKeyWithoutReleasingIt() {
    KeyRecorder recorder = KeyRecorder.attachTo(fixture.target);
    fixture.pressKey(VK_A);
    assertThat(recorder).keysPressed(VK_A).noKeysReleased();
  }

  @Test(dependsOnMethods = "shouldPressGivenKeyWithoutReleasingIt") 
  public void shouldReleaseGivenKey() {
    KeyRecorder recorder = KeyRecorder.attachTo(fixture.target);
    fixture.pressKey(VK_A);
    fixture.releaseKey(VK_A);
    assertThat(recorder).keysPressed(VK_A);
  }

  @Test public final void shouldPassIfComponentIsVisibleAndExpectingVisible() {
    fixture.target.setVisible(true);
    fixture.requireVisible();
  }
  
  @Test public final void shouldFailIfComponentIsNotVisibleAndExpectingVisible() {
    fixture.target.setVisible(false);
    try {
      fixture.requireVisible();
      fail();
    } catch(AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property(VISIBLE), expected("true"), actual("false"));
    }
  }

  @Test public final void shouldPassIfComponentIsNotVisibleAndExpectingNotVisible() {
    fixture.target.setVisible(false);
    fixture.requireNotVisible();
  }
  
  @Test public final void shouldFailIfComponentIsVisibleAndExpectingNotVisible() {
    fixture.target.setVisible(true);
    try {
      fixture.requireNotVisible();
      fail();
    } catch(AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property(VISIBLE), expected("false"), actual("true"));
    }
  }

  @Test public final void shouldPassIfComponentIsEnabledAndExpectingEnabled() {
    fixture.target.setEnabled(true);
    fixture.requireEnabled();
  }
  
  @Test public final void shouldFailIfComponentIsDisabledAndExpectingEnabled() {
    fixture.target.setEnabled(false);
    try {
      fixture.requireEnabled();
      fail();
    } catch(AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property(ENABLED), expected("true"), actual("false"));
    }
  }
  
  @Test public final void shouldPassIfComponentIsDisabledAndExpectingDisabled() {
    fixture.target.setEnabled(false);
    fixture.requireDisabled();
  }
  
  @Test public final void shouldFailIfComponentIsEnabledAndExpectingDisabled() {
    fixture.target.setEnabled(true);
    try {
      fixture.requireDisabled();
      fail();
    } catch(AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property(ENABLED), expected("false"), actual("true"));
    }
  }

  @Test public void shouldWaitTillComponentIsEnabled() {
    fixture.target.setEnabled(false);
    new Thread() {
      @Override public void run() {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {}
        fixture.target.setEnabled(true);
      }
    }.start();
    fixture.assertEnabled(timeout(3, SECONDS));
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeoutIfComponentNotEnabled() {
    fixture.target.setEnabled(false);
    fixture.assertEnabled(timeout(1, SECONDS));
  }
  
  @AfterMethod public final void tearDown() {
    beforeTearDown();
    robot.cleanUp();
  }

  protected void beforeTearDown() {}
  
  protected final RobotFixture robot() { return robot; }
  protected final MainWindow window() { return window; }
  protected final ComponentFixture<T> fixture() { return fixture; }
}
