/*
 * Created on Feb 10, 2007
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.AbstractContainerFixture;
import org.fest.swing.fixture.ComponentFixture;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JLabelFixture;
import org.fest.swing.fixture.JMenuItemFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.fest.swing.fixture.JTextComponentFixture;



import static java.awt.GridBagConstraints.HORIZONTAL;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static org.fest.assertions.Assertions.assertThat;

import static org.fest.util.Objects.*;

/**
 * Unit tests for <code>{@link AbstractContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixtureTest {

  private static class CustomWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    
    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
    final JMenu menu = new JMenu("A Menu");
    final JMenuItem subMenu = new JMenu("A Submenu");
    final JLabel label = new JLabel("A Label");
    final JButton button = new JButton("A Button");
    final JDialog dialog = new JDialog(this, "A Dialog");
    final JTextField textField = new JTextField(10);
    final JTabbedPane tabbedPane = new JTabbedPane() ;
    
    CustomWindow() {
      setLayout(new GridBagLayout());
      setUpComponents();
      addComponents();
    }
    
    private void setUpComponents() {
      comboBox.setName("comboBox");
      menu.setName("menu");
      menu.add(subMenu);
      label.setName("label");
      button.setName("button");
      button.addMouseListener(new MouseAdapter() {
        @Override public void mousePressed(MouseEvent e) {
          JOptionPane.showMessageDialog(CustomWindow.this, "A Message");
        }
      });
      dialog.setName("dialog");
      textField.setName("textField");
      tabbedPane.setName("tabbedPane");
    }
    
    private void addComponents() {
      setJMenuBar(new JMenuBar());
      getJMenuBar().add(menu);
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = c.gridy = 0;
      c.fill = HORIZONTAL;
      add(label, c);
      c.gridy++;
      add(comboBox, c);
      c.gridy++;
      add(textField, c);
      c.gridy++;
      add(button, c);
      c.gridy++;
      add(tabbedPane, c);
    }
  }
  
  private RobotFixture robot;
  private AbstractContainerFixture<CustomWindow> containerFixture;
  private CustomWindow window;
  
  @BeforeClass public void setUp() {
    robot = new RobotFixture();
    containerFixture = new AbstractContainerFixture<CustomWindow>(robot, new CustomWindow()) {

      public ComponentFixture<CustomWindow> click() {
        return null;
      }

      public ComponentFixture<CustomWindow> focus() {
        return null;
      }

      public ComponentFixture<CustomWindow> shouldBeVisible() {
        return null;
      }

      public ComponentFixture<CustomWindow> shouldNotBeVisible() {
        return null;
      }};
    window = containerFixture.target;
    robot.showWindow(window);
  }

  @Test public void shouldFindComboBoxWithGivenName() {
    JComboBoxFixture fixture = containerFixture.findComboBox("comboBox");
    assertThat(fixture.target).isSameAs(window.comboBox);
  }
  
  @Test public void shouldFindTabbedPaneWithGivenName() {
    JTabbedPaneFixture fixture = containerFixture.findTabbedPane("tabbedPane");
    assertThat(fixture.target).isSameAs(window.tabbedPane);
  }
  
  @Test public void shouldFindLabelWithGivenName() {
    JLabelFixture fixture = containerFixture.findLabel("label");
    assertThat(fixture.target).isSameAs(window.label);
  }

  @Test public void shouldFindButtonWithGivenName() {
    JButtonFixture fixture = findButton();
    assertThat(fixture.target).isSameAs(window.button);
  }

  @Test public void shouldFindDialogWithGivenName() {
    DialogFixture fixture = containerFixture.findDialog("dialog");
    assertThat(fixture.target).isSameAs(window.dialog);
  }
  
  @Test public void shouldFindMenuWithGivenName() {
    JMenuItemFixture fixture = containerFixture.findMenuItem("menu");
    assertThat(fixture.target).isSameAs(window.menu);
  }
  
  @Test public void shouldFindMenuWithGivenPath() {
    JMenuItemFixture fixture = containerFixture.findMenuItem("A Menu", "A Submenu");
    assertThat(fixture.target).isSameAs(window.subMenu);
  }
  
  @Test(dependsOnMethods = "shouldFindButtonWithGivenName") 
  public void shouldFindOptionPane() {
    findButton().click();
    JOptionPaneFixture fixture = containerFixture.findOptionPane();
    assertThat(fixture.target.getMessage()).isEqualTo("A Message");
  }

  private JButtonFixture findButton() {
    return containerFixture.findButton("button");
  }

  @Test public void shouldFindTextComponentWithGivenName() {
    JTextComponentFixture fixture = containerFixture.findTextComponent("textField");
    assertThat(fixture.target).isSameAs(window.textField);
  }
  
  @AfterClass public void tearDown() {
    robot.cleanUp();
  }
}
