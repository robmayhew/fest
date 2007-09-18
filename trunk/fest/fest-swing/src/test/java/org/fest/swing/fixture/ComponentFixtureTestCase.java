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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.UIManager;

import abbot.tester.ComponentTester;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_Z;
import static javax.swing.SwingUtilities.getWindowAncestor;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.fixture.ErrorMessages.EXPECTED_FALSE_BUT_WAS_TRUE;
import static org.fest.swing.fixture.ErrorMessages.EXPECTED_TRUE_BUT_WAS_FALSE;

import org.fest.swing.Condition;
import org.fest.swing.RobotFixture;
import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
      lookNative();
    }
    
    private void lookNative() {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ignored) {}
    }
    
    public void clickButton() {
      tester.click(button);      
    }
  }

  private static class KeyListener extends KeyAdapter {
    private final List<Integer> pressedKeys = new ArrayList<Integer>();
    
    void reset() { pressedKeys.clear(); }
    
    @Override public void keyPressed(KeyEvent e) {
      pressedKeys.add(e.getKeyCode());
    }

    int[] pressedKeys() {
      int pressedKeyCount = pressedKeys.size();
      int[] keys = new int[pressedKeyCount];
      for(int i = 0; i < pressedKeyCount; i++) keys[i] = pressedKeys.get(i);
      return keys;
    }
  }

  private RobotFixture robot;
  private MainWindow window;
  private ComponentFixture<T> fixture;
  private ErrorMessages errorMessages;
  
  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow(getClass());
    window.setSize(new Dimension(300, 200));
    T target = createTarget();
    errorMessages = new ErrorMessages(target); 
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
    ComponentEvents events = ComponentEvents.attachTo(fixture.target);
    fixture.click();
    assertThat(events.clicked()).isTrue();
  }
  
  @Test public final void shouldDoubleClickComponent() {
    ComponentEvents events = ComponentEvents.attachTo(fixture.target);
    fixture.doubleClick();
    assertThat(events.doubleClicked()).isTrue();
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
  
  @Test public final void shouldPressGivenKeys() {
    KeyListener keyListener = new KeyListener();
    fixture.target.addKeyListener(keyListener);
    int[] keys = { VK_A, VK_B, VK_Z };
    fixture.pressKeys(keys);
    assertThat(keyListener.pressedKeys()).isEqualTo(keys);
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
      errorMessages.assertIsCorrect(e, VISIBLE, EXPECTED_TRUE_BUT_WAS_FALSE);
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
      errorMessages.assertIsCorrect(e, VISIBLE, EXPECTED_FALSE_BUT_WAS_TRUE);
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
      errorMessages.assertIsCorrect(e, ENABLED, EXPECTED_TRUE_BUT_WAS_FALSE);
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
      errorMessages.assertIsCorrect(e, ENABLED, EXPECTED_FALSE_BUT_WAS_TRUE);
    }
  }

  @AfterMethod public final void tearDown() {
    beforeTearDown();
    robot.cleanUp();
  }

  protected void beforeTearDown() {}
  
  protected final RobotFixture robot() { return robot; }
  protected final MainWindow window() { return window; }
  protected final ComponentFixture<T> fixture() { return fixture; }
  protected final ErrorMessages errorMessages() { return errorMessages; }
}
