/*
 * Created on Jun 19, 2008
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

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.ComponentDriverGuiTest.KeyAction.action;
import static org.fest.swing.driver.ComponentDriverGuiTest.KeyActionType.*;
import static org.fest.swing.driver.ComponentDriverGuiTest.KeyPressRecorder.attachTo;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverGuiTest {

  private Robot robot;
  private ComponentDriver driver;
  private JTextField textField;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new ComponentDriver(robot);
    MyFrame frame = new MyFrame();
    textField = frame.textField;
    robot.showWindow(frame);
  }
  
  public void shouldPressKeyAndModifiersUsingKeyPressInfo() {
    KeyPressRecorder recorder = attachTo(textField);
    driver.pressAndReleaseKey(textField, KeyPressInfo.keyCode(VK_R).modifiers(SHIFT_MASK));
    List<KeyAction> actions = recorder.actions;
    assertThat(actions).containsOnly(
        action(PRESSED, VK_SHIFT),
        action(PRESSED, VK_R),
        action(RELEASED, VK_R),
        action(RELEASED, VK_SHIFT)
    );
  }

  public void shouldPressKeyAndModifiers() {
    KeyPressRecorder recorder = attachTo(textField);
    driver.pressAndReleaseKey(textField, VK_C, new int[] { CTRL_MASK, SHIFT_MASK });
    List<KeyAction> actions = recorder.actions;
    assertThat(actions).containsOnly(
        action(PRESSED, VK_SHIFT),
        action(PRESSED, VK_CONTROL),
        action(PRESSED, VK_C),
        action(RELEASED, VK_C),
        action(RELEASED, VK_CONTROL),
        action(RELEASED, VK_SHIFT)
    );
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  static class KeyPressRecorder extends KeyAdapter {
    final List<KeyAction> actions = new ArrayList<KeyAction>();

    static KeyPressRecorder attachTo(Component c) {
      KeyPressRecorder recorder = new KeyPressRecorder();
      c.addKeyListener(recorder);
      return recorder;
    }
    
    @Override public void keyPressed(KeyEvent e) {
      actions.add(action(KeyActionType.PRESSED, e.getKeyCode()));
    }

    @Override public void keyReleased(KeyEvent e) {
      actions.add(action(KeyActionType.RELEASED, e.getKeyCode()));
    }    
  }
  
  static class KeyAction {
    final KeyActionType type;
    final int keyCode;

    static KeyAction action(KeyActionType type, int keyCode) {
      return new KeyAction(type, keyCode);
    }
    
    private KeyAction(KeyActionType type, int keyCode) {
      this.type = type;
      this.keyCode = keyCode;
    }
    
    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      final KeyAction other = (KeyAction) obj;
      if (keyCode != other.keyCode) return false;
      if (type == null && other.type != null) return false;
      if (!type.equals(other.type)) return false;
      return true;
    }

    @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + keyCode;
      result = prime * result + ((type == null) ? 0 : type.hashCode());
      return result;
    }
    
    @Override public String toString() {
      StringBuilder b = new StringBuilder();
      b.append("type=").append(type).append(", ");
      b.append("keyCode=").append(keyCode).append("]");
      return b.toString();
    }
  }
  
  static enum KeyActionType {
    PRESSED, RELEASED
  }
  
  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField(20);
    
    MyFrame() {
      super(ComponentDriverGuiTest.class);
      add(textField);
    }
  }
}
