/*
 * Created on Jun 10, 2007
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

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JCheckBoxFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JCheckboxFixtureTest {

  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
   
    final JCheckBox checkBox = new JCheckBox("A checkbox");
    
    public MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }

    private void setUpComponents() {
      checkBox.setName("checkBox");
    }
    
    private void addComponents() {
      add(checkBox);
    }
  }

  private MainWindow window;
  private RobotFixture robot;
  private JCheckBoxFixture fixture;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    robot.showWindow(window);
    fixture = new JCheckBoxFixture(robot, "checkBox");
  }
  
  @Test public void shouldSelectCheckBoxIfNotSelected() {
    window.checkBox.setSelected(false);
    fixture.check();
    assertThat(window.checkBox.isSelected()).isTrue();
  }
  
  @Test public void shouldNotSelectCheckboxIfAlreadySelected() {
    window.checkBox.setSelected(true);
    fixture.check();
    assertThat(window.checkBox.isSelected()).isTrue();
  }
  
  @Test public void shouldUnselectCheckBoxIfSelected() {
    window.checkBox.setSelected(true);
    fixture.uncheck();
    assertThat(window.checkBox.isSelected()).isFalse();
  }
  
  @Test public void shouldNotUnselectCheckboxIfAlreadyUnselected() {
    window.checkBox.setSelected(false);
    fixture.uncheck();
    assertThat(window.checkBox.isSelected()).isFalse();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
