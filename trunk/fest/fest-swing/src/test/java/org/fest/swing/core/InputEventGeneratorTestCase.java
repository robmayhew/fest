/*
 * Created on Apr 3, 2008
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

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.util.AWT;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.*;

/**
 * Test case for implementations of <code>{@link InputEventGenerator}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class InputEventGeneratorTestCase {

  private MyFrame frame;
  private InputEventGenerator generator;

  @BeforeMethod public void setUp() throws Exception {
    frame = new MyFrame();
    onSetUp();
    generator = generator();
    frame.display();
  }
  
  void onSetUp() throws Exception {}

  abstract InputEventGenerator generator();

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test public void shouldMoveMouse() {
    MouseMotionRecorder recorder = MouseMotionRecorder.attachTo(frame);
    Point center = AWT.centerOf(frame);
    generator.moveMouse(frame, center.x, center.y);
    assertThat(recorder.point()).isEqualTo(center);
  }
  
  @Test(dataProvider = "mouseButtons",  dependsOnMethods = "shouldMoveMouse")
  public void shouldClickMouseButton(MouseButton button) {
    Point center = AWT.centerOf(frame);
    generator.moveMouse(frame, center.x, center.y);
    ClickRecorder recorder = ClickRecorder.attachTo(frame);
    generator.pressMouse(button.mask);
    assertThat(recorder.clicked(button));
  }
  
 
  @DataProvider(name = "mouseButtons") public Object[][] mouseButtons() {
    return new Object[][] { { LEFT_BUTTON }, { MIDDLE_BUTTON }, { RIGHT_BUTTON } };
  }
  
  private static class MouseMotionRecorder extends MouseMotionAdapter {
    private Point point;

    static MouseMotionRecorder attachTo(Component c) {
      MouseMotionRecorder recorder = new MouseMotionRecorder();
      c.addMouseMotionListener(recorder);
      return recorder;
    }
    
    @Override public void mouseMoved(MouseEvent e) {
      point = e.getPoint();
    }
    
    Point point() { return point; }
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextField textBox = new JTextField(20);
    
    public MyFrame() {
      super(InputEventGeneratorTestCase.class);
      add(textBox);
    }
  }
}
