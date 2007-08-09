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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import static javax.swing.BoxLayout.Y_AXIS;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.GUITest;
import org.fest.swing.GenericTypeMatcher;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@GUITest public class ContainerFixtureTest {

  private static class CustomWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    
    final JButton button = new JButton("A Button");
    final JCheckBox checkBox = new JCheckBox("A CheckBox");
    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
    final JDialog dialog = new JDialog(this, "A Dialog");
    final JFileChooser fileChooser = new JFileChooser();
    final JLabel label = new JLabel("A Label");
    final JList list = new JList();
    final JMenu menu = new JMenu("A Menu");
    final JMenuItem subMenu = new JMenu("A Submenu");
    final JTabbedPane tabbedPane = new JTabbedPane();
    final JTextField textField = new JTextField(10);
    
    CustomWindow() {
      setLayout(new BoxLayout(getContentPane(), Y_AXIS));
      setUpComponents();
      addComponents();
      lookNative();
    }
    
    private void addComponents() {
      setJMenuBar(new JMenuBar());
      getJMenuBar().add(menu);
      addComponents(label, comboBox, textField, button, tabbedPane, checkBox, list, fileChooser);
    }
    
    private void addComponents(Component... components) {
      for (Component c : components) add(c);
    }
    
    private void lookNative() {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ignored) {}
    }
    
    private void setUpComponents() {
      button.setName("button");
      button.addMouseListener(new MouseAdapter() {
        @Override public void mousePressed(MouseEvent e) {
          JOptionPane.showMessageDialog(CustomWindow.this, "A Message");
        }
      });
      checkBox.setName("checkBox");
      comboBox.setName("comboBox");
      dialog.setName("dialog");
      fileChooser.setName("fileChooser");
      label.setName("label");
      list.setName("list");
      menu.setName("menu");
      menu.add(subMenu);
      textField.setName("textField");
      tabbedPane.setName("tabbedPane");
      tabbedPane.addTab("A Tab", new JPanel());
    }
  }
  
  private ContainerFixture<CustomWindow> container;
  private RobotFixture robot;
  private CustomWindow window;
  
  private JButtonFixture findButton() {
    return container.button("button");
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    container = new ContainerFixture<CustomWindow>(robot, new CustomWindow()) {};
    window = container.target;
    robot.showWindow(window);
  }
  
  @Test public void shouldFindButtonWithGivenMatcher() {
    GenericTypeMatcher<JButton> textMatcher = new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    };
    JButtonFixture button = container.button(textMatcher);
    assertThat(button.target).isSameAs(window.button);
  }
  
  @Test public void shouldFindButtonWithGivenName() {
    JButtonFixture button = findButton();
    assertThat(button.target).isSameAs(window.button);
  }

  @Test public void shouldFindCheckBoxWithGivenName() {
    JCheckBoxFixture checkBox = container.checkBox("checkBox");
    assertThat(checkBox.target).isSameAs(window.checkBox);
  }
  
  @Test public void shouldFindCheckBoxWithGivenMatcher() {
    GenericTypeMatcher<JCheckBox> textMatcher = new GenericTypeMatcher<JCheckBox>() {
      @Override protected boolean isMatching(JCheckBox checkBox) {
        return "A CheckBox".equals(checkBox.getText());
      }
    };
    JCheckBoxFixture checkbox = container.checkbox(textMatcher);
    assertThat(checkbox.target).isSameAs(window.checkBox);
  }

  @Test public void shouldFindComboBoxWithGivenName() {
    JComboBoxFixture comboBox = container.comboBox("comboBox");
    assertThat(comboBox.target).isSameAs(window.comboBox);
  }
  
  @Test public void shouldFindDialogWithGivenName() {
    DialogFixture dialog = container.dialog("dialog");
    assertThat(dialog.target).isSameAs(window.dialog);
  }
  
  @Test public void shouldFindFileChooserWithGivenName() {
    JFileChooserFixture fixture = container.fileChooser("fileChooser");
    assertThat(fixture.target).isSameAs(window.fileChooser);
  }
  
  @Test public void shouldFindLabelWithGivenName() {
    JLabelFixture label = container.label("label");
    assertThat(label.target).isSameAs(window.label);
  }
  
  @Test public void shouldFindListWithGivenName() {
    JListFixture fixture = container.list("list");
    assertThat(fixture.target).isSameAs(window.list);
  }

  @Test public void shouldFindMenuWithGivenName() {
    JMenuItemFixture fixture = container.menuItem("menu");
    assertThat(fixture.target).isSameAs(window.menu);
  }

  @Test public void shouldFindMenuWithGivenPath() {
    JMenuItemFixture fixture = container.menuItem("A Menu", "A Submenu");
    assertThat(fixture.target).isSameAs(window.subMenu);
  }
  
  @Test(dependsOnMethods = "shouldFindButtonWithGivenName") 
  public void shouldFindOptionPane() {
    findButton().click();
    JOptionPaneFixture fixture = container.optionPane();
    assertThat(fixture.target.getMessage()).isEqualTo("A Message");
  }
  
  @Test public void shouldFindTabbedPaneWithGivenName() {
    JTabbedPaneFixture tabbedPane = container.tabbedPane("tabbedPane");
    assertThat(tabbedPane.target).isSameAs(window.tabbedPane);
  }
  
  @Test public void shouldFindTextComponentWithGivenName() {
    JTextComponentFixture fixture = container.textBox("textField");
    assertThat(fixture.target).isSameAs(window.textField);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
