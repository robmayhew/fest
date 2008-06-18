/*
 * Created on Apr 5, 2008
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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link AbstractButtonDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class AbstractButtonDriverTest {

  private Robot robot;
  private JCheckBox checkBox;
  private AbstractButtonDriver driver;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    driver = new AbstractButtonDriver(robot);
    MyFrame frame = new MyFrame();
    checkBox = frame.checkBox;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldClickButton() {
    final boolean[] clicked = new boolean[1];
    checkBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clicked[0] = true;
      }
    });
    driver.click(checkBox);
    assertThat(clicked[0]).isTrue();
  }
  
  @Test public void shouldNotClickButtonIfButtonDisabled() {
    final boolean[] clicked = new boolean[1];
    checkBox.setEnabled(false);
    checkBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clicked[0] = true;
      }
    });
    driver.click(checkBox);
    assertThat(clicked[0]).isFalse();
  }

  @Test public void shouldPassIfTextIsEqualToExpectedOne() {
    driver.requireText(checkBox, "Hello");
  }
  
  @Test public void shouldFailIfTextIsNotEqualToExpectedOne() {
    try {
      driver.requireText(checkBox, "Bye");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'")
                             .contains("expected:<'Bye'> but was:<'Hello'>");
    }
  }

  @Test public void shouldNotSelectIfButtonAlreadySelected() {
    checkBox.setSelected(true);
    driver.select(checkBox);
    assertThat(checkBox.isSelected()).isTrue();
  }
  
  @Test public void shouldSelectButton() {
    checkBox.setSelected(false);
    driver.select(checkBox);
    assertThat(checkBox.isSelected()).isTrue();
  }

  @Test public void shouldNotUnselectIfButtonAlreadySelected() {
    checkBox.setSelected(false);
    driver.unselect(checkBox);
    assertThat(checkBox.isSelected()).isFalse();
  }
  
  @Test public void shouldUnselectButton() {
    checkBox.setSelected(true);
    driver.unselect(checkBox);
    assertThat(checkBox.isSelected()).isFalse();
  }

  @Test public void shouldPassIfButtonIsSelectedAsAnticipated() {
    checkBox.setSelected(true);
    driver.requireSelected(checkBox);
  }
  
  @Test public void shouldFailIfButtonIsNotSelectedAndExpectingSelected() {
    checkBox.setSelected(false);
    try {
      driver.requireSelected(checkBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selected'")
                             .contains("expected:<true> but was:<false>");
    }
  }
  
  @Test public void shouldPassIfButtonIsUnselectedAsAnticipated() {
    checkBox.setSelected(false);
    driver.requireNotSelected(checkBox);
  }

  @Test public void shouldFailIfButtonIsSelectedAndExpectingNotSelected() {
    checkBox.setSelected(true);
    try {
      driver.requireNotSelected(checkBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selected'")
                             .contains("expected:<false> but was:<true>");
    }
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JCheckBox checkBox = new JCheckBox("Hello", true);

    public MyFrame() {
      super(AbstractButtonDriverTest.class);
      add(checkBox);
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
