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

import javax.swing.JButton;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.FocusSetter.setFocusOn;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link FocusMonitor}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class FocusMonitorTest {

  private FocusMonitor monitor;
  private MyFrame frame;
  
  @BeforeMethod public void setUp() {
    frame = new MyFrame();
    frame.display();
    setFocusOn(frame.button);
    monitor = FocusMonitor.addFocusMonitorTo(frame.button);
    assertThat(monitor.hasFocus()).isTrue();
  }
  
  @AfterMethod public void tearDown() {
    frame.destroy();
  }
  
  public void shouldReturnFalseIfLosesFocus() {
    setFocusOn(frame.textBox);
    assertThat(monitor.hasFocus()).isFalse();
  }

  public void shouldNotHaveFocusIsComponentIsNotFocusOwner() {
    setFocusOn(frame.textBox);
    monitor = FocusMonitor.addFocusMonitorTo(frame.button);
    assertThat(monitor.hasFocus()).isFalse();
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;
    
    final JButton button = new JButton("Click Me");
    final JTextField textBox = new JTextField(20); 
    
    MyFrame() {
      super(FocusMonitorTest.class);
      add(button);
      add(textBox);
    }
  }
}
