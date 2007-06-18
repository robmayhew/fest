/*
 * Created on Apr 9, 2007
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

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JComboBoxFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@GUITest public class JComboBoxFixtureTest {

  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
   
    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
    
    public MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }

    private void setUpComponents() {
      comboBox.setName("comboBox");
    }
    
    private void addComponents() {
      add(comboBox);
    }
  }
  
  private MainWindow window;
  private RobotFixture robot;
  private JComboBoxFixture fixture;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    robot.showWindow(window);
    fixture = new JComboBoxFixture(robot, "comboBox");
  }

  @Test public void shouldHaveFoundComboBox() {
    assertThat(fixture.target).isSameAs(window.comboBox);
  }
 
  @Test(dependsOnMethods = "shouldHaveFoundComboBox")
  public void shouldReturnComboBoxContents() {
    assertThat(fixture.contents()).isEqualTo(array("first", "second", "third"));
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundComboBox")
  public void shouldSelectItemAtGivenIndex() {
    fixture.selectItemAt(2);
    assertThat(window.comboBox.getSelectedItem()).equals("third");
  }

  @Test(dependsOnMethods = "shouldHaveFoundComboBox")
  public void shouldSelectItemWithGivenText() {
    fixture.selectItemWithText("second");
    assertThat(window.comboBox.getSelectedItem()).equals("second");
  }

  @Test(dependsOnMethods = "shouldHaveFoundComboBox")
  public void shouldReturnValueAtGivenIndex() {
    assertThat(fixture.valueAt(2)).isEqualTo("third");
  }

  @Test(dependsOnMethods = "shouldHaveFoundComboBox")
  public void shouldEnterTextInEditableComboBox() {
    window.comboBox.setEditable(true);
    fixture.enter("Text entered by FEST");
    JTextField editorComponent = (JTextField)window.comboBox.getEditor().getEditorComponent();
    assertThat(editorComponent.getText()).contains("Text entered by FEST");
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundComboBox")
  public void shouldNotEnterTextInNonEditableComboBox() {
    window.comboBox.setEditable(false);
    fixture.enter("Text entered by FEST");
    JTextField editorComponent = (JTextField)window.comboBox.getEditor().getEditorComponent();
    assertThat(editorComponent.getText()).isEmpty();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
