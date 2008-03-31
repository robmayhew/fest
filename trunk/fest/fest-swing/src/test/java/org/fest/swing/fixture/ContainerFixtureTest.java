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
import java.util.Collection;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;

import static java.awt.Color.RED;
import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureTest {

  private ContainerFixture<FrameWithAllSupportedComponents> fixture;
  private Robot robot;
  private FrameWithAllSupportedComponents window;

  private JButtonFixture findButton() {
    return fixture.button("button");
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new FrameWithAllSupportedComponents(getClass());
    fixture = new ConcreteContainerFixture<FrameWithAllSupportedComponents>(robot, window);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldFindButtonByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.button();
      }
    };
    find(finder, window.button);
  }
  
  @Test public void shouldFindButtonWithGivenMatcher() {
    GenericTypeMatcher<JButton> textMatcher = new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    };
    JButtonFixture button = fixture.button(textMatcher);
    assertThat(button.target).isSameAs(window.button);
  }

  @Test public void shouldFindButtonWithGivenName() {
    JButtonFixture button = findButton();
    assertThat(button.target).isSameAs(window.button);
  }

  @Test public void shouldFindCheckBoxByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.checkBox();
      }
    };
    find(finder, window.checkBox);
  }

  @Test public void shouldFindCheckBoxWithGivenMatcher() {
    GenericTypeMatcher<JCheckBox> textMatcher = new GenericTypeMatcher<JCheckBox>() {
      protected boolean isMatching(JCheckBox checkBox) {
        return "A CheckBox".equals(checkBox.getText());
      }
    };
    JCheckBoxFixture checkbox = fixture.checkBox(textMatcher);
    assertThat(checkbox.target).isSameAs(window.checkBox);
  }

  @Test public void shouldFindCheckBoxWithGivenName() {
    JCheckBoxFixture checkBox = fixture.checkBox("checkBox");
    assertThat(checkBox.target).isSameAs(window.checkBox);
  }

  @Test public void shouldFindComboBoxByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.comboBox();
      }
    };
    find(finder, window.comboBox);
  }

  @Test public void shouldFindComboBoxWithGivenMatcher() {
    GenericTypeMatcher<JComboBox> itemCountMatcher = new GenericTypeMatcher<JComboBox>() {
      protected boolean isMatching(JComboBox comboBox) {
        return comboBox.getItemCount() == 3;
      }
    };
    JComboBoxFixture comboBox = fixture.comboBox(itemCountMatcher);
    assertThat(comboBox.target).isSameAs(window.comboBox);
  }

  @Test public void shouldFindComboBoxWithGivenName() {
    JComboBoxFixture comboBox = fixture.comboBox("comboBox");
    assertThat(comboBox.target).isSameAs(window.comboBox);
  }

  @Test public void shouldFindDialogByType() {
    window.dialog.setVisible(true);
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.dialog();
      }
    };
    find(finder, window.dialog);
  }

  @Test public void shouldFindDialogWithGivenMatcher() {
    GenericTypeMatcher<JDialog> titleMatcher = new GenericTypeMatcher<JDialog>() {
      protected boolean isMatching(JDialog dialog) {
        return "A Dialog".equals(dialog.getTitle());
      }
    };
    DialogFixture dialog = fixture.dialog(titleMatcher);
    assertThat(dialog.target).isSameAs(window.dialog);
  }

  @Test public void shouldFindDialogWithGivenName() {
    window.dialog.pack();
    window.dialog.setVisible(true);
    DialogFixture dialog = fixture.dialog("dialog");
    assertThat(dialog.target).isSameAs(window.dialog);
  }

  @Test public void shouldFindFileChooserByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.fileChooser();
      }
    };
    find(finder, window.fileChooser);
  }

  @Test public void shouldFindFileChooserWithGivenMatcher() {
    GenericTypeMatcher<JFileChooser> nameMatcher = new GenericTypeMatcher<JFileChooser>() {
      protected boolean isMatching(JFileChooser fileChooser) {
        return "fileChooser".equals(fileChooser.getName());
      }
    };
    JFileChooserFixture fileChooser = fixture.fileChooser(nameMatcher);
    assertThat(fileChooser.target).isEqualTo(window.fileChooser);
  }

  @Test public void shouldFindFileChooserWithGivenName() {
    JFileChooserFixture fileChooser = fixture.fileChooser("fileChooser");
    assertThat(fileChooser.target).isSameAs(window.fileChooser);
  }

  @Test public void shouldFindLabelByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.label();
      }
    };
    find(finder, window.label);
  }

  @Test public void shouldFindLabelWithGivenMatcher() {
    GenericTypeMatcher<JLabel> textMatcher = new GenericTypeMatcher<JLabel>() {
      protected boolean isMatching(JLabel label) {
        return "A Label".equals(label.getText());
      }
    };
    JLabelFixture label = fixture.label(textMatcher);
    assertThat(label.target).isSameAs(window.label);
  }

  @Test public void shouldFindLabelWithGivenName() {
    JLabelFixture label = fixture.label("label");
    assertThat(label.target).isSameAs(window.label);
  }

  @Test public void shouldFindListByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.list();
      }
    };
    find(finder, window.list);
  }

  @Test public void shouldFindListWithGivenMatcher() {
    GenericTypeMatcher<JList> nameMatcher = new GenericTypeMatcher<JList>() {
      protected boolean isMatching(JList list) {
        return "list".equals(list.getName());
      }
    };
    JListFixture list = fixture.list(nameMatcher);
    assertThat(list.target).isSameAs(window.list);
  }

  @Test public void shouldFindListWithGivenName() {
    JListFixture list = fixture.list("list");
    assertThat(list.target).isSameAs(window.list);
  }

  @Test public void shouldFindMenuWithGivenMatcher() {
    GenericTypeMatcher<JMenuItem> textMatcher = new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "A Submenu".equals(menuItem.getText());
      }
    };
    JMenuItemFixture menuItem = fixture.menuItem(textMatcher);
    assertThat(menuItem.target).isSameAs(window.subMenu);
  }

  @Test public void shouldFindMenuWithGivenName() {
    JMenuItemFixture menuItem = fixture.menuItem("menu");
    assertThat(menuItem.target).isSameAs(window.menu);
  }

  @Test public void shouldFindMenuWithGivenPath() {
    JMenuItemFixture menuItem = fixture.menuItemWithPath("A Menu", "A Submenu");
    assertThat(menuItem.target).isSameAs(window.subMenu);
  }

  @Test public void shouldFindPanelByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.panel();
      }
    };
    find(finder, window.panel);
  }

  @Test public void shouldFindPanelWithGivenMatcher() {
    GenericTypeMatcher<JPanel> colorMatcher = new GenericTypeMatcher<JPanel>() {
      protected boolean isMatching(JPanel panel) {
        return RED.equals(panel.getBackground());
      }
    };
    JPanelFixture panel = fixture.panel(colorMatcher);
    assertThat(panel.target).isSameAs(window.panel);
  }

  @Test public void shouldFindPanelWithGivenName() {
    JPanelFixture panel = fixture.panel("panel");
    assertThat(panel.target).isSameAs(window.panel);
  }

  @Test(dependsOnMethods = "shouldFindButtonWithGivenName")
  public void shouldFindOptionPane() {
    findButton().click();
    JOptionPaneFixture optionPane = fixture.optionPane();
    assertThat(optionPane.target.getMessage()).isEqualTo("A Message");
  }

  @Test public void shouldFindRadioButtonByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.radioButton();
      }
    };
    find(finder, window.radioButton);
  }

  @Test public void shouldFindRadioButtonWithGivenMatcher() {
    GenericTypeMatcher<JRadioButton> textMatcher = new GenericTypeMatcher<JRadioButton>() {
      protected boolean isMatching(JRadioButton radioButton) {
        return "A Radio Button".equals(radioButton.getText());
      }
    };
    JRadioButtonFixture radioButton = fixture.radioButton(textMatcher);
    assertThat(radioButton.target).isSameAs(window.radioButton);
  }

  @Test public void shouldFindRadioButtonWithGivenName() {
    JRadioButtonFixture radioButton = fixture.radioButton("radioButton");
    assertThat(radioButton.target).isSameAs(window.radioButton);
  }

  @Test public void shouldFindScrollBarByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.scrollBar();
      }
    };
    find(finder, window.scrollBar);
  }

  @Test public void shouldFindScrollBarWithGivenMatcher() {
    GenericTypeMatcher<JScrollBar> valueMatcher = new GenericTypeMatcher<JScrollBar>() {
      protected boolean isMatching(JScrollBar scrollBar) {
        return scrollBar.getValue() == 10;
      }
    };
    JScrollBarFixture scrollBar = fixture.scrollBar(valueMatcher);
    assertThat(scrollBar.target).isSameAs(window.scrollBar);
  }

  @Test public void shouldFindScrollBarWithGivenName() {
    JScrollBarFixture scrollBar = fixture.scrollBar("scrollBar");
    assertThat(scrollBar.target).isSameAs(window.scrollBar);
  }

  @Test public void shouldFindScrollPane() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.scrollPane();
      }
    };
    find(finder, window.scrollPane);
  }

  @Test public void shouldFindScrollPaneWithGivenMatcher() {
    GenericTypeMatcher<JScrollPane> nameMatcher = new GenericTypeMatcher<JScrollPane>() {
      protected boolean isMatching(JScrollPane scrollPane) {
        return "scrollPane".equals(scrollPane.getName());
      }
    };
    JScrollPaneFixture scrollPane = fixture.scrollPane(nameMatcher);
    assertThat(scrollPane.target).isSameAs(window.scrollPane);
  }

  @Test public void shouldFindScrollPaneWithGivenName() {
    JScrollPaneFixture scrollPane = fixture.scrollPane("scrollPane");
    assertThat(scrollPane.target).isSameAs(window.scrollPane);
  }

  @Test public void shouldFindSliderByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.slider();
      }
    };
    find(finder, window.slider);
  }

  @Test public void shouldFindSliderWithGivenMatcher() {
    GenericTypeMatcher<JSlider> valueMatcher = new GenericTypeMatcher<JSlider>() {
      protected boolean isMatching(JSlider slider) {
        return slider.getValue() == 15;
      }
    };
    JSliderFixture slider = fixture.slider(valueMatcher);
    assertThat(slider.target).isSameAs(window.slider);
  }

  @Test public void shouldFindSliderWithGivenName() {
    JSliderFixture slider = fixture.slider("slider");
    assertThat(slider.target).isSameAs(window.slider);
  }

  @Test public void shouldFindSpinnerByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.spinner();
      }
    };
    find(finder, window.spinner);
  }

  @Test public void shouldFindSpinnerWithGivenMatcher() {
    GenericTypeMatcher<JSpinner> valueMatcher = new GenericTypeMatcher<JSpinner>() {
      protected boolean isMatching(JSpinner spinner) {
        return spinner.getModel().getValue().equals("One");
      }
    };
    JSpinnerFixture spinner = fixture.spinner(valueMatcher);
    assertThat(spinner.target).isSameAs(window.spinner);
  }

  @Test public void shouldFindSpinnerWithGivenName() {
    JSpinnerFixture spinner = fixture.spinner("spinner");
    assertThat(spinner.target).isSameAs(window.spinner);
  }

  @Test public void shouldFindSplitPaneByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.splitPane();
      }
    };
    find(finder, window.splitPane);
  }

  @Test public void shouldFindSplitPaneWithGivenMatcher() {
    GenericTypeMatcher<JSplitPane> matcher = new GenericTypeMatcher<JSplitPane>() {
      protected boolean isMatching(JSplitPane splitPane) {
        return splitPane.getRightComponent() instanceof JList;
      }
    };
    JSplitPaneFixture splitPane = fixture.splitPane(matcher);
    assertThat(splitPane.target).isSameAs(window.splitPane);
  }

  @Test public void shouldFindSplitPaneWithGivenName() {
    JSplitPaneFixture splitPane = fixture.splitPane("splitPane");
    assertThat(splitPane.target).isSameAs(window.splitPane);
  }

  @Test public void shouldFindTabbedPaneByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.tabbedPane();
      }
    };
    find(finder, window.tabbedPane);
  }

  @Test public void shouldFindTabbedPaneWithGivenMatcher() {
    GenericTypeMatcher<JTabbedPane> tabCountMatcher = new GenericTypeMatcher<JTabbedPane>() {
      protected boolean isMatching(JTabbedPane tabbedPane) {
        return tabbedPane.getTabCount() == 1;
      }
    };
    JTabbedPaneFixture tabbedPane = fixture.tabbedPane(tabCountMatcher);
    assertThat(tabbedPane.target).isSameAs(window.tabbedPane);
  }

  @Test public void shouldFindTabbedPaneWithGivenName() {
    JTabbedPaneFixture tabbedPane = fixture.tabbedPane("tabbedPane");
    assertThat(tabbedPane.target).isSameAs(window.tabbedPane);
  }

  @Test public void shouldFindTableByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.table();
      }
    };
    find(finder, window.table);
  }

  @Test public void shouldFindTableWithGivenMatcher() {
    GenericTypeMatcher<JTable> rowCountMatcher = new GenericTypeMatcher<JTable>() {
      protected boolean isMatching(JTable table) {
        return table.getRowCount() == 6;
      }
    };
    JTableFixture table = fixture.table(rowCountMatcher);
    assertThat(table.target).isSameAs(window.table);
  }

  @Test public void shouldFindTableWithGivenName() {
    JTableFixture table = fixture.table("table");
    assertThat(table.target).isSameAs(window.table);
  }

  @Test public void shouldFindTextComponentByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.textBox();
      }
    };
    find(finder, window.textField);
  }

  @Test public void shouldFindTextComponentWithGivenMatcher() {
    GenericTypeMatcher<JTextField> columnMatcher = new GenericTypeMatcher<JTextField>() {
      protected boolean isMatching(JTextField textField) {
        return textField.getColumns() == 10;
      }
    };
    JTextComponentFixture textField = fixture.textBox(columnMatcher);
    assertThat(textField.target).isSameAs(window.textField);
  }

  @Test public void shouldFindTextComponentWithGivenName() {
    JTextComponentFixture textField = fixture.textBox("textField");
    assertThat(textField.target).isSameAs(window.textField);
  }

  @Test public void shouldFindToggleButtonByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.toggleButton();
      }
    };
    find(finder, window.toggleButton);
  }

  @Test public void shouldFindToggleButtonWithGivenMatcher() {
    GenericTypeMatcher<JToggleButton> textMatcher = new GenericTypeMatcher<JToggleButton>() {
      protected boolean isMatching(JToggleButton toggleButton) {
        return "A ToggleButton".equals(toggleButton.getText());
      }
    };
    JToggleButtonFixture checkbox = fixture.toggleButton(textMatcher);
    assertThat(checkbox.target).isSameAs(window.toggleButton);
  }

  @Test public void shouldFindToggleButtonWithGivenName() {
    JToggleButtonFixture toggleButton = fixture.toggleButton("toggleButton");
    assertThat(toggleButton.target).isSameAs(window.toggleButton);
  }

  @Test public void shouldFindToolBarByType() {
    FindFunction finder = new FindFunction() {
      @Override ComponentFixture<? extends Component> perform() {
        return fixture.toolBar();
      }
    };
    find(finder, window.toolBar);
  }

  @Test public void shouldFindToolBarWithGivenMatcher() {
    GenericTypeMatcher<JToolBar> columnMatcher = new GenericTypeMatcher<JToolBar>() {
      protected boolean isMatching(JToolBar toolBar) {
        return toolBar.getOrientation() == HORIZONTAL && "toolBar".equals(toolBar.getName());
      }
    };
    JToolBarFixture toolBar = fixture.toolBar(columnMatcher);
    assertThat(toolBar.target).isSameAs(window.toolBar);
  }

  @Test public void shouldFindToolBarWithGivenName() {
    JToolBarFixture toolBar = fixture.toolBar("toolBar");
    assertThat(toolBar.target).isSameAs(window.toolBar);
  }

  private void find(FindFunction findFunction, Component expected) {
    try {
      ComponentFixture<? extends Component> component = findFunction.perform();
      assertThat(component.target).isSameAs(expected);        
    } catch (ComponentLookupException e) {
      Collection<? extends Component> found = e.found();
      assertThat(found).isNotEmpty();
      for (Component c : found) if (c == expected) return;
      fail(concat("Unable to find ", expected));
    }
  }
  
  private static abstract class FindFunction {
    abstract ComponentFixture<? extends Component> perform();
  }
}
