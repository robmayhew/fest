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
import java.awt.Dimension;
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
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerListModel;
import javax.swing.UIManager;

import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.SwingConstants.HORIZONTAL;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.annotation.GUITest;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@GUITest
public class ContainerFixtureTest {

  private static class CustomWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private static final Dimension PREFERRED_DIMENSION = new Dimension(100, 100);
    
    final JButton button = new JButton("A Button");
    final JCheckBox checkBox = new JCheckBox("A CheckBox");
    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
    final JDialog dialog = new JDialog(this, "A Dialog");
    final JFileChooser fileChooser = new JFileChooser();
    final JLabel label = new JLabel("A Label");
    final JList list = new JList();
    final JMenu menu = new JMenu("A Menu");
    final JRadioButton radioButton = new JRadioButton("A Radio Button");
    final JSlider slider = new JSlider(10, 20, 15);
    final JSpinner spinner = new JSpinner(new SpinnerListModel(array("One", "Two")));
    final JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT, new JList(), new JList());
    final JMenuItem subMenu = new JMenu("A Submenu");
    final JTabbedPane tabbedPane = new JTabbedPane();
    final JTable table = new JTable(6, 8);
    final JTextField textField = new JTextField(10);
    final JToolBar toolBar = new JToolBar(HORIZONTAL);
    
    CustomWindow(Class testClass) {
      setTitle(testClass.getName());
      setLayout(new BoxLayout(getContentPane(), Y_AXIS));
      lookNative();
      setUpComponents();
      addComponents();
    }
    
    private void addComponents() {
      setJMenuBar(new JMenuBar());
      getJMenuBar().add(menu);
      add(button, checkBox, comboBox, fileChooser, label, list, radioButton, slider, spinner, splitPane, tabbedPane, table, textField, toolBar);
    }
    
    private void add(Component... components) {
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
      radioButton.setName("radioButton");
      slider.setName("slider");
      spinner.setName("spinner");
      splitPane.setName("splitPane");
      splitPane.setPreferredSize(PREFERRED_DIMENSION);
      tabbedPane.setName("tabbedPane");
      tabbedPane.addTab("A Tab", new JPanel());
      table.setName("table");
      table.setPreferredSize(PREFERRED_DIMENSION);
      textField.setName("textField");
      toolBar.setName("toolBar");
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
    container = new ConcreteContainerFixture<CustomWindow>(robot, new CustomWindow(getClass()));
    window = container.target;
    robot.showWindow(window);
  }

  @Test public void shouldFindButtonByType() {
    JButtonFixture button = container.button();
    assertThat(button.target).isNotNull();
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

  @Test public void shouldFindCheckBoxByType() {
    JCheckBoxFixture checkBox = container.checkBox();
    assertThat(checkBox.target).isNotNull();
  }

  @Test public void shouldFindCheckBoxWithGivenMatcher() {
    GenericTypeMatcher<JCheckBox> textMatcher = new GenericTypeMatcher<JCheckBox>() {
      protected boolean isMatching(JCheckBox checkBox) {
        return "A CheckBox".equals(checkBox.getText());
      }
    };
    JCheckBoxFixture checkbox = container.checkBox(textMatcher);
    assertThat(checkbox.target).isSameAs(window.checkBox);
  }
  
  @Test public void shouldFindCheckBoxWithGivenName() {
    JCheckBoxFixture checkBox = container.checkBox("checkBox");
    assertThat(checkBox.target).isSameAs(window.checkBox);
  }

  @Test public void shouldFindComboBoxByType() {
    JComboBoxFixture comboBox = container.comboBox();
    assertThat(comboBox.target).isNotNull();
  }
  
  @Test public void shouldFindComboBoxWithGivenMatcher() {
    GenericTypeMatcher<JComboBox> itemCountMatcher = new GenericTypeMatcher<JComboBox>() {
      protected boolean isMatching(JComboBox comboBox) {
        return comboBox.getItemCount() == 3;
      }
    };
    JComboBoxFixture comboBox = container.comboBox(itemCountMatcher);
    assertThat(comboBox.target).isSameAs(window.comboBox);
  }
  
  @Test public void shouldFindComboBoxWithGivenName() {
    JComboBoxFixture comboBox = container.comboBox("comboBox");
    assertThat(comboBox.target).isSameAs(window.comboBox);
  }
  
  @Test public void shouldFindDialogByType() {
    DialogFixture dialog = container.dialog();
    assertThat(dialog.target).isNotNull();
  }
  
  @Test public void shouldFindDialogWithGivenMatcher() {
    GenericTypeMatcher<JDialog> titleMatcher = new GenericTypeMatcher<JDialog>() {
      protected boolean isMatching(JDialog dialog) {
        return "A Dialog".equals(dialog.getTitle());
      }
    };
    DialogFixture dialog = container.dialog(titleMatcher);
    assertThat(dialog.target).isSameAs(window.dialog);
  }
  
  @Test public void shouldFindDialogWithGivenName() {
    DialogFixture dialog = container.dialog("dialog");
    assertThat(dialog.target).isSameAs(window.dialog);
  }
  
  @Test public void shouldFindFileChooserByType() {
    JFileChooserFixture fileChooser = container.fileChooser();
    assertThat(fileChooser.target).isNotNull();
  }
  
  @Test public void shouldFindFileChooserWithGivenMatcher() {
    GenericTypeMatcher<JFileChooser> nameMatcher = new GenericTypeMatcher<JFileChooser>() {
      protected boolean isMatching(JFileChooser fileChooser) {
        return "fileChooser".equals(fileChooser.getName());
      }
    };
    JFileChooserFixture fileChooser = container.fileChooser(nameMatcher);
    assertThat(fileChooser.target).isEqualTo(window.fileChooser);
  }
  
  @Test public void shouldFindFileChooserWithGivenName() {
    JFileChooserFixture fileChooser = container.fileChooser("fileChooser");
    assertThat(fileChooser.target).isSameAs(window.fileChooser);
  }
  
  @Test public void shouldFindLabelByType() {
    JLabelFixture label = container.label();
    assertThat(label.target).isNotNull();
  }
  
  @Test public void shouldFindLabelWithGivenMatcher() {
    GenericTypeMatcher<JLabel> textMatcher = new GenericTypeMatcher<JLabel>() {
      protected boolean isMatching(JLabel label) {
        return "A Label".equals(label.getText());
      }
    };
    JLabelFixture label = container.label(textMatcher);
    assertThat(label.target).isSameAs(window.label);
  }
  
  @Test public void shouldFindLabelWithGivenName() {
    JLabelFixture label = container.label("label");
    assertThat(label.target).isSameAs(window.label);
  }
  
  @Test public void shouldFindListByType() {
    JListFixture list = container.list();
    assertThat(list.target).isNotNull();
  }
  
  @Test public void shouldFindListWithGivenMatcher() {
    GenericTypeMatcher<JList> nameMatcher = new GenericTypeMatcher<JList>() {
      protected boolean isMatching(JList list) {
        return "list".equals(list.getName());
      }
    };
    JListFixture list = container.list(nameMatcher);
    assertThat(list.target).isSameAs(window.list);
  }
  
  @Test public void shouldFindListWithGivenName() {
    JListFixture list = container.list("list");
    assertThat(list.target).isSameAs(window.list);
  }

  @Test public void shouldFindMenuWithGivenMatcher() {
    GenericTypeMatcher<JMenuItem> textMatcher = new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "A Submenu".equals(menuItem.getText());
      }
    };
    JMenuItemFixture menuItem = container.menuItem(textMatcher);
    assertThat(menuItem.target).isSameAs(window.subMenu);
  }

  @Test public void shouldFindMenuWithGivenName() {
    JMenuItemFixture menuItem = container.menuItem("menu");
    assertThat(menuItem.target).isSameAs(window.menu);
  }
  
  @Test public void shouldFindMenuWithGivenPath() {
    JMenuItemFixture menuItem = container.menuItem("A Menu", "A Submenu");
    assertThat(menuItem.target).isSameAs(window.subMenu);
  }
  
  @Test(dependsOnMethods = "shouldFindButtonWithGivenName") 
  public void shouldFindOptionPane() {
    findButton().click();
    JOptionPaneFixture fixture = container.optionPane();
    assertThat(fixture.target.getMessage()).isEqualTo("A Message");
  }
  
  @Test public void shouldFindRadioButtonByType() {
    JRadioButtonFixture radioButton = container.radioButton();
    assertThat(radioButton.target).isNotNull();
  }

  @Test public void shouldFindRadioButtonWithGivenMatcher() {
    GenericTypeMatcher<JRadioButton> textMatcher = new GenericTypeMatcher<JRadioButton>() {
      protected boolean isMatching(JRadioButton radioButton) {
        return "A Radio Button".equals(radioButton.getText());
      }
    };
    JRadioButtonFixture radioButton = container.radioButton(textMatcher);
    assertThat(radioButton.target).isSameAs(window.radioButton);
  }
  
  @Test public void shouldFindRadioButtonWithGivenName() {
    JRadioButtonFixture radioButton = container.radioButton("radioButton");
    assertThat(radioButton.target).isSameAs(window.radioButton);
  }
  
  @Test public void shouldFindSliderByType() {
    JSliderFixture slider = container.slider();
    assertThat(slider.target).isNotNull();
  }

  @Test public void shouldFindSliderWithGivenMatcher() {
    GenericTypeMatcher<JSlider> valueMatcher = new GenericTypeMatcher<JSlider>() {
      protected boolean isMatching(JSlider slider) {
        return slider.getValue() == 15;
      }
    };
    JSliderFixture slider = container.slider(valueMatcher);
    assertThat(slider.target).isSameAs(window.slider);
  }
  
  @Test public void shouldFindSliderWithGivenName() {
    JSliderFixture slider = container.slider("slider");
    assertThat(slider.target).isSameAs(window.slider);
  }
  
  @Test public void shouldFindSpinnerByType() {
    JSpinnerFixture spinner = container.spinner();
    assertThat(spinner.target).isNotNull();
  }

  @Test public void shouldFindSpinnerWithGivenMatcher() {
    GenericTypeMatcher<JSpinner> valueMatcher = new GenericTypeMatcher<JSpinner>() {
      protected boolean isMatching(JSpinner spinner) {
        return spinner.getModel().getValue().equals("One");
      }
    };
    JSpinnerFixture spinner = container.spinner(valueMatcher);
    assertThat(spinner.target).isSameAs(window.spinner);
  }
  
  @Test public void shouldFindSpinnerWithGivenName() {
    JSpinnerFixture spinner = container.spinner("spinner");
    assertThat(spinner.target).isSameAs(window.spinner);
  }
  
  @Test public void shouldFindSplitPaneByType() {
    JSplitPaneFixture splitPane = container.splitPane();
    assertThat(splitPane.target).isNotNull();
  }
  
  @Test public void shouldFindSplitPaneWithGivenMatcher() {
    GenericTypeMatcher<JSplitPane> matcher = new GenericTypeMatcher<JSplitPane>() {
      protected boolean isMatching(JSplitPane splitPane) {
        return splitPane.getRightComponent() instanceof JList;
      }
    };
    JSplitPaneFixture splitPane = container.splitPane(matcher);
    assertThat(splitPane.target).isSameAs(window.splitPane);
  }
  
  @Test public void shouldFindSplitPaneWithGivenName() {
    JSplitPaneFixture splitPane = container.splitPane("splitPane");
    assertThat(splitPane.target).isSameAs(window.splitPane);
  }
  
  @Test public void shouldFindTabbedPaneByType() {
    JTabbedPaneFixture tabbedPane = container.tabbedPane();
    assertThat(tabbedPane.target).isNotNull();
  }

  @Test public void shouldFindTabbedPaneWithGivenMatcher() {
    GenericTypeMatcher<JTabbedPane> tabCountMatcher = new GenericTypeMatcher<JTabbedPane>() {
      protected boolean isMatching(JTabbedPane tabbedPane) {
        return tabbedPane.getTabCount() == 1;
      }
    };
    JTabbedPaneFixture tabbedPane = container.tabbedPane(tabCountMatcher);
    assertThat(tabbedPane.target).isSameAs(window.tabbedPane);
  }
  
  @Test public void shouldFindTabbedPaneWithGivenName() {
    JTabbedPaneFixture tabbedPane = container.tabbedPane("tabbedPane");
    assertThat(tabbedPane.target).isSameAs(window.tabbedPane);
  }
  
  @Test public void shouldFindTableByType() {
    JTableFixture table = container.table();
    assertThat(table.target).isNotNull();
  }
  
  @Test public void shouldFindTableWithGivenMatcher() {
    GenericTypeMatcher<JTable> rowCountMatcher = new GenericTypeMatcher<JTable>() {
      protected boolean isMatching(JTable table) {
        return table.getRowCount() == 6;
      }
    };
    JTableFixture table = container.table(rowCountMatcher);
    assertThat(table.target).isSameAs(window.table);
  }
  
  @Test public void shouldFindTableWithGivenName() {
    JTableFixture table = container.table("table");
    assertThat(table.target).isSameAs(window.table);
  }
  
  @Test public void shouldFindTextComponentByType() {
    JTextComponentFixture textField = container.textBox();
    assertThat(textField.target).isNotNull();
  }
  
  @Test public void shouldFindTextComponentWithGivenMatcher() {
    GenericTypeMatcher<JTextField> columnMatcher = new GenericTypeMatcher<JTextField>() {
      protected boolean isMatching(JTextField textField) {
        return textField.getColumns() == 10;
      }
    };
    JTextComponentFixture textField = container.textBox(columnMatcher);
    assertThat(textField.target).isSameAs(window.textField);
  }
  
  @Test public void shouldFindTextComponentWithGivenName() {
    JTextComponentFixture textField = container.textBox("textField");
    assertThat(textField.target).isSameAs(window.textField);
  }
  
  @Test public void shouldFindToolBarByType() {
    JToolBarFixture toolBar = container.toolBar();
    assertThat(toolBar.target).isNotNull();
  }
  
  @Test public void shouldFindToolBarWithGivenMatcher() {
    GenericTypeMatcher<JToolBar> columnMatcher = new GenericTypeMatcher<JToolBar>() {
      protected boolean isMatching(JToolBar toolBar) {
        return toolBar.getOrientation() == HORIZONTAL && "toolBar".equals(toolBar.getName());
      }
    };
    JToolBarFixture toolBar = container.toolBar(columnMatcher);
    assertThat(toolBar.target).isSameAs(window.toolBar);
  }
  
  @Test public void shouldFindToolBarWithGivenName() {
    JToolBarFixture toolBar = container.toolBar("toolBar");
    assertThat(toolBar.target).isSameAs(window.toolBar);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
