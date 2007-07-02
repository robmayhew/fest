/*
 * Created on Jul 1, 2007
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

import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.fest.util.Collections;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.*;

/**
 * Tests for <code>{@link JSpinnerFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JSpinnerFixtureTest {

  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
   
    final JSpinner spinner = new JSpinner(new SpinnerListModel(Collections.list("Frodo", "Sam", "Gandalf")));
    
    public MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }

    private void setUpComponents() {
      spinner.setName("spinner");
    }
    
    private void addComponents() {
      add(spinner);
    }
  }
  
  private MainWindow window;
  private RobotFixture robot;
  private JSpinnerFixture fixture;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    robot.showWindow(window);
    fixture = new JSpinnerFixture(robot, "spinner");
  }
  
  @Test public void shouldIncrementValue() {
    assertThat(window.spinner.getValue()).isEqualTo("Frodo");
    fixture.increment();
    assertThat(window.spinner.getValue()).isEqualTo("Sam");
  }
  
  @Test(dependsOnMethods = "shouldIncrementValue")
  public void shouldDecrementValue() {
    fixture.increment();
    fixture.decrement();
    assertThat(window.spinner.getValue()).isEqualTo("Frodo");
  }
  
  @Test public void shouldEnterText() {
    fixture.enterText("Gandalf");
    assertThat(window.spinner.getValue()).isEqualTo("Gandalf");
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
