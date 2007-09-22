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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.MouseButtons.BUTTON1;
import static org.fest.swing.MouseButtons.BUTTON2;
import static org.fest.swing.MouseButtons.BUTTON3;

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
  public void shouldClickComponentWithGivenMouseButtonAndGivenNumberOfTimes(MouseButtons button, int times) {
    ClickRecordMouseListener clickRecord = new ClickRecordMouseListener();
    frame.textBox.addMouseListener(clickRecord);
    robot.click(frame.textBox, button, times);
  }
  
  @DataProvider(name = "clickingData") 
  public Object[][] clickingData() {
    return new Object[][] {
        { BUTTON1, 1 },
        { BUTTON1, 2 },
        { BUTTON2, 1 },
        { BUTTON2, 2 },
        { BUTTON3, 1 },
        { BUTTON3, 2 },
    };
  }
  
  @Test(expectedExceptions = ComponentLookupException.class)
  public void shouldNotShowPopupMenuInTextFieldNotContainingPopupMenu() {
    robot.showPopupMenu(frame.textBox);
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
  
  private static class ClickRecordMouseListener extends MouseAdapter {
    private static final Map<Integer, MouseButtons> MOUSE_BUTTON_MAP = new HashMap<Integer, MouseButtons>(); 
    
    static {
        MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON1, BUTTON1);
        MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON2, BUTTON2);
        MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON3, BUTTON3);
    }
    
    MouseButtons clickedButton;
    int clickCount;

    @Override public void mousePressed(MouseEvent e) {
      clickedButton = MOUSE_BUTTON_MAP.get(e.getButton());
      clickCount = e.getClickCount();
    }
  }
}
