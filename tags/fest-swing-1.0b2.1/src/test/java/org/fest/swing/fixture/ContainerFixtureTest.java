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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.query.AbstractButtonTextQuery;
import org.fest.swing.query.JLabelTextQuery;
import org.fest.swing.testing.TestWindow;

import static java.awt.Color.RED;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.factory.JButtons.button;
import static org.fest.swing.factory.JCheckBoxes.checkBox;
import static org.fest.swing.factory.JComboBoxes.comboBox;
import static org.fest.swing.factory.JDialogs.dialog;
import static org.fest.swing.factory.JFileChoosers.fileChooser;
import static org.fest.swing.factory.JLabels.label;
import static org.fest.swing.factory.JLists.list;
import static org.fest.swing.factory.JMenuBars.menuBar;
import static org.fest.swing.factory.JMenuItems.menuItem;
import static org.fest.swing.factory.JMenus.menu;
import static org.fest.swing.factory.JPanels.panel;
import static org.fest.swing.factory.JRadioButtons.radioButton;
import static org.fest.swing.factory.JScrollBars.scrollBar;
import static org.fest.swing.factory.JScrollPanes.scrollPane;
import static org.fest.swing.factory.JSliders.slider;
import static org.fest.swing.factory.JSpinners.spinner;
import static org.fest.swing.factory.JSplitPanes.splitPane;
import static org.fest.swing.factory.JTabbedPanes.tabbedPane;
import static org.fest.swing.factory.JTables.table;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.factory.JToggleButtons.toggleButton;
import static org.fest.swing.factory.JToolBars.toolBar;
import static org.fest.swing.factory.JTrees.tree;
import static org.fest.swing.query.AbstractButtonTextQuery.textOf;
import static org.fest.swing.query.ComponentBackgroundQuery.backgroundOf;
import static org.fest.swing.query.ComponentNameQuery.nameOf;
import static org.fest.swing.query.DialogTitleQuery.titleOf;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI , enabled = false)
public class ContainerFixtureTest {

  // TODO This class needs some serious refactoring

  private static final Dimension PREFERRED_DIMENSION = new Dimension(100, 100);

  private ContainerFixture<TestWindow> fixture;
  private Robot robot;
  private TestWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TestWindow.createNew(getClass());
    fixture = new ContainerFixture<TestWindow>(robot, window) {};
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindButtonByType() {
    JButton expectedButton = addJButton();
    JButtonFixture buttonFixture = fixture.button();
    assertThat(buttonFixture.target).isSameAs(expectedButton);
  }

  public void shouldFindButtonWithGivenMatcher() {
    JButton expectedButton = addJButton();
    GenericTypeMatcher<JButton> textMatcher = new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(textOf(button));
      }
    };
    JButtonFixture buttonFixture = fixture.button(textMatcher);
    assertThat(buttonFixture.target).isSameAs(expectedButton);
  }

  public void shouldFindButtonWithGivenName() {
    JButton expectedButton = addJButton();
    JButtonFixture buttonFixture = fixture.button("button");
    assertThat(buttonFixture.target).isSameAs(expectedButton);
  }

  private JButton addJButton() {
    JButton button = button().withName("button")
                             .withText("A Button")
                             .createNew();
    showWindowWith(button);
    return button;
  }

  public void shouldFindCheckBoxByType() {
    JCheckBox expectedCheckBox = addJCheckBox();
    JCheckBoxFixture checkBoxFixture = fixture.checkBox();
    assertThat(checkBoxFixture.target).isSameAs(expectedCheckBox);
  }

  public void shouldFindCheckBoxWithGivenMatcher() {
    JCheckBox expectedCheckBox = addJCheckBox();
    GenericTypeMatcher<JCheckBox> textMatcher = new GenericTypeMatcher<JCheckBox>() {
      protected boolean isMatching(JCheckBox checkBox) {
        return "A CheckBox".equals(textOf(checkBox));
      }
    };
    JCheckBoxFixture checkboxFixture = fixture.checkBox(textMatcher);
    assertThat(checkboxFixture.target).isSameAs(expectedCheckBox);
  }

  public void shouldFindCheckBoxWithGivenName() {
    JCheckBox expectedCheckBox = addJCheckBox();
    JCheckBoxFixture checkBoxFixture = fixture.checkBox("checkBox");
    assertThat(checkBoxFixture.target).isSameAs(expectedCheckBox);
  }

  private JCheckBox addJCheckBox() {
    JCheckBox checkBox = checkBox().withName("checkBox")
                                   .withText("A CheckBox")
                                   .createNew();
    showWindowWith(checkBox);
    return checkBox;
  }

  public void shouldFindComboBoxByType() {
    JComboBox expectedComboBox = addJComboBox();
    JComboBoxFixture comboBoxFixture = fixture.comboBox();
    assertThat(comboBoxFixture.target).isSameAs(expectedComboBox);
  }

  public void shouldFindComboBoxWithGivenMatcher() {
    JComboBox expectedComboBox = addJComboBox();
    GenericTypeMatcher<JComboBox> itemCountMatcher = new GenericTypeMatcher<JComboBox>() {
      protected boolean isMatching(JComboBox comboBox) {
        return nameOf(comboBox).equals("comboBox");
      }
    };
    JComboBoxFixture comboBoxFixture = fixture.comboBox(itemCountMatcher);
    assertThat(comboBoxFixture.target).isSameAs(expectedComboBox);
  }

  public void shouldFindComboBoxWithGivenName() {
    JComboBox expectedComboBox = addJComboBox();
    JComboBoxFixture comboBoxFixture = fixture.comboBox("comboBox");
    assertThat(comboBoxFixture.target).isSameAs(expectedComboBox);
  }

  private JComboBox addJComboBox() {
    JComboBox comboBox = comboBox().withItems("first", "second", "third")
                                   .withName("comboBox")
                                   .createNew();
    showWindowWith(comboBox);
    return comboBox;
  }

  public void shouldFindDialogByType() {
    JDialog expectedDialog = addJDialog();
    DialogFixture dialogFixture = fixture.dialog();
    assertThat(dialogFixture.target).isSameAs(expectedDialog);
  }

  public void shouldFindDialogWithGivenMatcher() {
    JDialog expectedDialog = addJDialog();
    GenericTypeMatcher<JDialog> titleMatcher = new GenericTypeMatcher<JDialog>() {
      protected boolean isMatching(JDialog dialog) {
        return "A Dialog".equals(titleOf(dialog));
      }
    };
    DialogFixture dialogFixture = fixture.dialog(titleMatcher);
    assertThat(dialogFixture.target).isSameAs(expectedDialog);
  }

  public void shouldFindDialogWithGivenName() {
    JDialog expectedDialog = addJDialog();
    DialogFixture dialogFixture = fixture.dialog("dialog");
    assertThat(dialogFixture.target).isSameAs(expectedDialog);
  }

  private JDialog addJDialog() {
    window.display();
    JDialog dialog = dialog().withName("dialog")
                             .withOwner(window)
                             .withTitle("A Dialog")
                             .createAndShow();
    return dialog;
  }

  public void shouldFindFileChooserByType() {
    JFileChooser expectedFileChooser = addJFileChooser();
    JFileChooserFixture fileChooserFixture = fixture.fileChooser();
    assertThat(fileChooserFixture.target).isSameAs(expectedFileChooser);
  }

  public void shouldFindFileChooserWithGivenMatcher() {
    JFileChooser expectedFileChooser = addJFileChooser();
    GenericTypeMatcher<JFileChooser> nameMatcher = new GenericTypeMatcher<JFileChooser>() {
      protected boolean isMatching(JFileChooser fileChooser) {
        return "fileChooser".equals(nameOf(fileChooser));
      }
    };
    JFileChooserFixture fileChooserFixture = fixture.fileChooser(nameMatcher);
    assertThat(fileChooserFixture.target).isEqualTo(expectedFileChooser);
  }

  public void shouldFindFileChooserWithGivenName() {
    JFileChooser expectedFileChooser = addJFileChooser();
    JFileChooserFixture fileChooserFixture = fixture.fileChooser("fileChooser");
    assertThat(fileChooserFixture.target).isSameAs(expectedFileChooser);
  }

  private JFileChooser addJFileChooser() {
    JFileChooser fileChooser = fileChooser().withName("fileChooser")
                                            .createNew();
    showWindowWith(fileChooser);
    return fileChooser;
  }

  public void shouldFindLabelByType() {
    JLabel expectedLabel = addJLabel();
    JLabelFixture labelFixture = fixture.label();
    assertThat(labelFixture.target).isSameAs(expectedLabel);
  }

  public void shouldFindLabelWithGivenMatcher() {
    JLabel expectedLabel = addJLabel();
    GenericTypeMatcher<JLabel> textMatcher = new GenericTypeMatcher<JLabel>() {
      protected boolean isMatching(JLabel label) {
        return "A Label".equals(JLabelTextQuery.textOf(label));
      }
    };
    JLabelFixture labelFixture = fixture.label(textMatcher);
    assertThat(labelFixture.target).isSameAs(expectedLabel);
  }

  public void shouldFindLabelWithGivenName() {
    JLabel expectedLabel = addJLabel();
    JLabelFixture labelFixture = fixture.label("label");
    assertThat(labelFixture.target).isSameAs(expectedLabel);
  }

  private JLabel addJLabel() {
    JLabel label = label().withName("label")
                          .withText("A Label")
                          .createNew();
    showWindowWith(label);
    return label;
  }

  public void shouldFindListByType() {
    JList expectedList = addJList();
    JListFixture listFixture = fixture.list();
    assertThat(listFixture.target).isSameAs(expectedList);
  }

  public void shouldFindListWithGivenMatcher() {
    JList expectedList = addJList();
    GenericTypeMatcher<JList> nameMatcher = new GenericTypeMatcher<JList>() {
      protected boolean isMatching(JList list) {
        return "list".equals(nameOf(list));
      }
    };
    JListFixture listFixture = fixture.list(nameMatcher);
    assertThat(listFixture.target).isSameAs(expectedList);
  }

  public void shouldFindListWithGivenName() {
    JList expectedList = addJList();
    JListFixture listFixture = fixture.list("list");
    assertThat(listFixture.target).isSameAs(expectedList);
  }

  private JList addJList() {
    JList list = list().withName("list").createNew();
    showWindowWith(list);
    return list;
  }

  public void shouldFindMenuWithGivenMatcher() {
    JMenuItem expectedMenuItem = addJMenuItem();
    GenericTypeMatcher<JMenuItem> textMatcher = new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "A Submenu".equals(AbstractButtonTextQuery.textOf(menuItem));
      }
    };
    JMenuItemFixture menuItemFixture = fixture.menuItem(textMatcher);
    assertThat(menuItemFixture.target).isSameAs(expectedMenuItem);
  }

  public void shouldFindMenuWithGivenName() {
    JMenuItem expectedMenuItem = addJMenuItem();
    JMenuItemFixture menuItem = fixture.menuItem("subMenu");
    assertThat(menuItem.target).isSameAs(expectedMenuItem);
  }

  public void shouldFindMenuWithGivenPath() {
    JMenuItem expectedMenuItem = addJMenuItem();
    JMenuItemFixture menuItemFixture = fixture.menuItemWithPath("A Menu", "A Submenu");
    assertThat(menuItemFixture.target).isSameAs(expectedMenuItem);
  }

  private JMenuItem addJMenuItem() {
    JMenuItem menuItem = menuItem().withName("subMenu")
                                   .withText("A Submenu")
                                   .createNew();
    JMenu menu = menu().withText("A Menu")
                       .withMenuItems(menuItem)
                       .createNew();
    JMenuBar menuBar = menuBar().withMenus(menu)
                                .createNew();
    setJMenuBar(window, menuBar);
    window.display();
    return menuItem;
  }

  private static void setJMenuBar(JFrame frame, JMenuBar menuBar) {
    frame.setJMenuBar(menuBar);
  }

  public void shouldFindPanelByType() {
    JPanel expectedPanel = addJPanel();
    JPanelFixture panelFixture = fixture.panel();
    assertThat(panelFixture.target).isSameAs(expectedPanel);
  }

  public void shouldFindPanelWithGivenMatcher() {
    JPanel expectedPanel = addJPanel();
    GenericTypeMatcher<JPanel> colorMatcher = new GenericTypeMatcher<JPanel>() {
      protected boolean isMatching(JPanel panel) {
        return RED.equals(backgroundOf(panel));
      }
    };
    JPanelFixture panelFixture = fixture.panel(colorMatcher);
    assertThat(panelFixture.target).isSameAs(expectedPanel);
  }

  public void shouldFindPanelWithGivenName() {
    JPanel expectedPanel = addJPanel();
    JPanelFixture panelFixture = fixture.panel("panel");
    assertThat(panelFixture.target).isSameAs(expectedPanel);
  }

  private JPanel addJPanel() {
    JPanel panel = panel().withBackground(RED)
                          .withName("panel")
                          .createNew();
    setContentPane(window, panel);
    window.display();
    return panel;
  }

  private void setContentPane(JFrame frame, JPanel panel) {
    frame.getRootPane().setContentPane(panel);
  }

  public void shouldFindOptionPane() {
    final JButton button = addJButton();
    // show option pane when clicking button
    robot.invokeAndWait(new Runnable() {
      public void run() {
        button.setName("button");
        button.addMouseListener(new MouseAdapter() {
          @Override public void mousePressed(MouseEvent e) {
            showMessageDialog(window, "A Message");
          }
        });
      }
    });
    robot.click(button);
    JOptionPaneFixture optionPaneFixture = fixture.optionPane();
    assertThat(messageOf(optionPaneFixture.target)).isEqualTo("A Message");
  }

  private Object messageOf(final JOptionPane optionPane) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() {
        return optionPane.getMessage();
      }
    });
  }

  public void shouldFindRadioButtonByType() {
    JRadioButton expectedRadioButton = addJRadioButton();
    JRadioButtonFixture radioButtonFixture = fixture.radioButton();
    assertThat(radioButtonFixture.target).isSameAs(expectedRadioButton);
  }

  public void shouldFindRadioButtonWithGivenMatcher() {
    JRadioButton expectedRadioButton = addJRadioButton();
    GenericTypeMatcher<JRadioButton> textMatcher = new GenericTypeMatcher<JRadioButton>() {
      protected boolean isMatching(JRadioButton radioButton) {
        return "A Radio Button".equals(AbstractButtonTextQuery.textOf(radioButton));
      }
    };
    JRadioButtonFixture radioButtonFixture = fixture.radioButton(textMatcher);
    assertThat(radioButtonFixture.target).isSameAs(expectedRadioButton);
  }

  public void shouldFindRadioButtonWithGivenName() {
    JRadioButton expectedRadioButton = addJRadioButton();
    JRadioButtonFixture radioButtonFixture = fixture.radioButton("radioButton");
    assertThat(radioButtonFixture.target).isSameAs(expectedRadioButton);
  }

  private JRadioButton addJRadioButton() {
    JRadioButton radioButton = radioButton().withName("radioButton")
                                            .withText("A Radio Button")
                                            .createNew();
    showWindowWith(radioButton);
    return radioButton;
  }

  public void shouldFindScrollBarByType() {
    JScrollBar expectedScrollBar = addJScrollBar();
    JScrollBarFixture scrollBarFixture = fixture.scrollBar();
    assertThat(scrollBarFixture.target).isSameAs(expectedScrollBar);
  }

  public void shouldFindScrollBarWithGivenMatcher() {
    JScrollBar expectedScrollBar = addJScrollBar();
    GenericTypeMatcher<JScrollBar> valueMatcher = new GenericTypeMatcher<JScrollBar>() {
      protected boolean isMatching(JScrollBar scrollBar) {
        return scrollBar.getName().equals("scrollBar");
      }
    };
    JScrollBarFixture scrollBarFixture = fixture.scrollBar(valueMatcher);
    assertThat(scrollBarFixture.target).isSameAs(expectedScrollBar);
  }

  public void shouldFindScrollBarWithGivenName() {
    JScrollBar expectedScrollBar = addJScrollBar();
    JScrollBarFixture scrollBarFixture = fixture.scrollBar("scrollBar");
    assertThat(scrollBarFixture.target).isSameAs(expectedScrollBar);
  }

  private JScrollBar addJScrollBar() {
    JScrollBar scrollBar = scrollBar().withName("scrollBar")
                                      .withValue(10)
                                      .createNew();
    showWindowWith(scrollBar);
    return scrollBar;
  }

  public void shouldFindScrollPane() {
    JScrollPane expectedScrollPane = addJScrollPane();
    JScrollPaneFixture scrollPaneFixture = fixture.scrollPane();
    assertThat(scrollPaneFixture.target).isSameAs(expectedScrollPane);
  }

  public void shouldFindScrollPaneWithGivenMatcher() {
    JScrollPane expectedScrollPane = addJScrollPane();
    GenericTypeMatcher<JScrollPane> nameMatcher = new GenericTypeMatcher<JScrollPane>() {
      protected boolean isMatching(JScrollPane scrollPane) {
        return "scrollPane".equals(nameOf(scrollPane));
      }
    };
    JScrollPaneFixture scrollPaneFixture = fixture.scrollPane(nameMatcher);
    assertThat(scrollPaneFixture.target).isSameAs(expectedScrollPane);
  }

  public void shouldFindScrollPaneWithGivenName() {
    JScrollPane expectedScrollPane = addJScrollPane();
    JScrollPaneFixture scrollPaneFixture = fixture.scrollPane("scrollPane");
    assertThat(scrollPaneFixture.target).isSameAs(expectedScrollPane);
  }

  private JScrollPane addJScrollPane() {
    JScrollPane scrollPane = scrollPane().withName("scrollPane")
                                         .withPreferredSize(PREFERRED_DIMENSION)
                                         .createNew();
    showWindowWith(scrollPane);
    return scrollPane;
  }

  public void shouldFindSliderByType() {
    JSlider expectedSlider = addJSlider();
    JSliderFixture sliderFixture = fixture.slider();
    assertThat(sliderFixture.target).isSameAs(expectedSlider);
  }

  public void shouldFindSliderWithGivenMatcher() {
    JSlider expectedSlider = addJSlider();
    GenericTypeMatcher<JSlider> valueMatcher = new GenericTypeMatcher<JSlider>() {
      protected boolean isMatching(JSlider slider) {
        return "slider".equals(slider.getName());
      }
    };
    JSliderFixture sliderFixture = fixture.slider(valueMatcher);
    assertThat(sliderFixture.target).isSameAs(expectedSlider);
  }

  public void shouldFindSliderWithGivenName() {
    JSlider expectedSlider = addJSlider();
    JSliderFixture sliderFixture = fixture.slider("slider");
    assertThat(sliderFixture.target).isSameAs(expectedSlider);
  }

  private JSlider addJSlider() {
    JSlider slider = slider().withMinimum(10)
                             .withMaximum(20)
                             .withValue(15)
                             .withName("slider")
                             .createNew();
    showWindowWith(slider);
    return slider;
  }

  public void shouldFindSpinnerByType() {
    JSpinner expectedSpinner = addJSpinner();
    JSpinnerFixture spinnerFixture = fixture.spinner();
    assertThat(spinnerFixture.target).isSameAs(expectedSpinner);
  }

  public void shouldFindSpinnerWithGivenMatcher() {
    JSpinner expectedSpinner = addJSpinner();
    GenericTypeMatcher<JSpinner> valueMatcher = new GenericTypeMatcher<JSpinner>() {
      protected boolean isMatching(JSpinner spinner) {
        return "spinner".equals(spinner.getName());
      }
    };
    JSpinnerFixture spinnerFixture = fixture.spinner(valueMatcher);
    assertThat(spinnerFixture.target).isSameAs(expectedSpinner);
  }

  public void shouldFindSpinnerWithGivenName() {
    JSpinner expectedSpinner = addJSpinner();
    JSpinnerFixture spinnerFixture = fixture.spinner("spinner");
    assertThat(spinnerFixture.target).isSameAs(expectedSpinner);
  }

  private JSpinner addJSpinner() {
    JSpinner spinner = spinner().withName("spinner")
                                .withValues("One", "Two")
                                .createNew();
    showWindowWith(spinner);
    return spinner;
  }

  public void shouldFindSplitPaneByType() {
    JSplitPane expectedSplitPane = addJSplitPane();
    JSplitPaneFixture splitPaneFixture = fixture.splitPane();
    assertThat(splitPaneFixture.target).isSameAs(expectedSplitPane);
  }

  public void shouldFindSplitPaneWithGivenMatcher() {
    JSplitPane expectedSplitPane = addJSplitPane();
    GenericTypeMatcher<JSplitPane> matcher = new GenericTypeMatcher<JSplitPane>() {
      protected boolean isMatching(final JSplitPane splitPane) {
        return splitPane.getRightComponent() instanceof JList;
      }
    };
    JSplitPaneFixture splitPaneFixture = fixture.splitPane(matcher);
    assertThat(splitPaneFixture.target).isSameAs(expectedSplitPane);
  }

  public void shouldFindSplitPaneWithGivenName() {
    JSplitPane expectedSplitPane = addJSplitPane();
    JSplitPaneFixture splitPaneFixture = fixture.splitPane("splitPane");
    assertThat(splitPaneFixture.target).isSameAs(expectedSplitPane);
  }

  private JSplitPane addJSplitPane() {
    JSplitPane splitPane = splitPane().withName("splitPane")
                                      .withRightComponent(newList())
                                      .createNew();
    showWindowWith(splitPane);
    return splitPane;
  }

  private JList newList() {
    return list().createNew();
  }

  public void shouldFindTabbedPaneByType() {
    JTabbedPane expectedTabbedPane = addJTabbedPane();
    JTabbedPaneFixture tabbedPaneFixture = fixture.tabbedPane();
    assertThat(tabbedPaneFixture.target).isSameAs(expectedTabbedPane);
  }

  public void shouldFindTabbedPaneWithGivenMatcher() {
    JTabbedPane expectedTabbedPane = addJTabbedPane();
    GenericTypeMatcher<JTabbedPane> tabCountMatcher = new GenericTypeMatcher<JTabbedPane>() {
      protected boolean isMatching(JTabbedPane tabbedPane) {
        return "tabbedPane".equals(tabbedPane.getName());
      }
    };
    JTabbedPaneFixture tabbedPaneFixture = fixture.tabbedPane(tabCountMatcher);
    assertThat(tabbedPaneFixture.target).isSameAs(expectedTabbedPane);
  }

  public void shouldFindTabbedPaneWithGivenName() {
    JTabbedPane expectedTabbedPane = addJTabbedPane();
    JTabbedPaneFixture tabbedPaneFixture = fixture.tabbedPane("tabbedPane");
    assertThat(tabbedPaneFixture.target).isSameAs(expectedTabbedPane);
  }

  private JTabbedPane addJTabbedPane() {
    JTabbedPane tabbedPane = tabbedPane().withName("tabbedPane")
                                         .withTabs("A Tab")
                                         .createNew();
    showWindowWith(tabbedPane);
    return tabbedPane;
  }

  public void shouldFindTableByType() {
    JTable expectedTable = addJTable();
    JTableFixture tableFixture = fixture.table();
    assertThat(tableFixture.target).isSameAs(expectedTable);
  }

  public void shouldFindTableWithGivenMatcher() {
    JTable expectedTable = addJTable();
    GenericTypeMatcher<JTable> rowCountMatcher = new GenericTypeMatcher<JTable>() {
      protected boolean isMatching(JTable table) {
        return "table".equals(table.getName());
      }
    };
    JTableFixture tableFixture = fixture.table(rowCountMatcher);
    assertThat(tableFixture.target).isSameAs(expectedTable);
  }

  public void shouldFindTableWithGivenName() {
    JTable expectedTable = addJTable();
    JTableFixture tableFixture = fixture.table("table");
    assertThat(tableFixture.target).isSameAs(expectedTable);
  }

  private JTable addJTable() {
    JTable table = table().withRowCount(6)
                          .withColumnCount(8)
                          .withName("table")
                          .createNew();
    showWindowWith(table);
    return table;
  }

  public void shouldFindTextComponentByType() {
    JTextComponent expectedTextComponent = addJTextComponent();
    JTextComponentFixture textComponentFixture = fixture.textBox();
    assertThat(textComponentFixture.target).isSameAs(expectedTextComponent);
  }

  public void shouldFindTextComponentWithGivenMatcher() {
    JTextComponent expectedTextComponent = addJTextComponent();
    GenericTypeMatcher<JTextField> columnMatcher = new GenericTypeMatcher<JTextField>() {
      protected boolean isMatching(final JTextField textField) {
        return textField.getColumns() == 10;
      }
    };
    JTextComponentFixture textComponentFixture = fixture.textBox(columnMatcher);
    assertThat(textComponentFixture.target).isSameAs(expectedTextComponent);
  }

  public void shouldFindTextComponentWithGivenName() {
    JTextComponent expectedTextComponent = addJTextComponent();
    JTextComponentFixture textComponentFixture = fixture.textBox("textField");
    assertThat(textComponentFixture.target).isSameAs(expectedTextComponent);
  }

  private JTextComponent addJTextComponent() {
    JTextField textField = textField().withColumns(10)
                                      .withName("textField")
                                      .createNew();
    showWindowWith(textField);
    return textField;
  }

  public void shouldFindToggleButtonByType() {
    JToggleButton expectedToggleButton = addJToggleButton();
    JToggleButtonFixture toggleButtonFixture = fixture.toggleButton();
    assertThat(toggleButtonFixture.target).isSameAs(expectedToggleButton);
  }

  public void shouldFindToggleButtonWithGivenMatcher() {
    JToggleButton expectedToggleButton = addJToggleButton();
    GenericTypeMatcher<JToggleButton> textMatcher = new GenericTypeMatcher<JToggleButton>() {
      protected boolean isMatching(JToggleButton toggleButton) {
        return "A ToggleButton".equals(AbstractButtonTextQuery.textOf(toggleButton));
      }
    };
    JToggleButtonFixture toggleButtonFixture = fixture.toggleButton(textMatcher);
    assertThat(toggleButtonFixture.target).isSameAs(expectedToggleButton);
  }

  public void shouldFindToggleButtonWithGivenName() {
    JToggleButton expectedToggleButton = addJToggleButton();
    JToggleButtonFixture toggleButtonFixture = fixture.toggleButton("toggleButton");
    assertThat(toggleButtonFixture.target).isSameAs(expectedToggleButton);
  }

  private JToggleButton addJToggleButton() {
    JToggleButton toggleButton = toggleButton().withName("toggleButton")
                                               .withText("A ToggleButton")
                                               .createNew();
    showWindowWith(toggleButton);
    return toggleButton;
  }

  public void shouldFindToolBarByType() {
    JToolBar expectedToolBar = addJToolBar();
    JToolBarFixture toolBarFixture = fixture.toolBar();
    assertThat(toolBarFixture.target).isSameAs(expectedToolBar);
  }

  public void shouldFindToolBarWithGivenMatcher() {
    JToolBar expectedToolBar = addJToolBar();
    GenericTypeMatcher<JToolBar> columnMatcher = new GenericTypeMatcher<JToolBar>() {
      protected boolean isMatching(JToolBar toolBar) {
        return "toolBar".equals(nameOf(toolBar));
      }
    };
    JToolBarFixture toolBarFixture = fixture.toolBar(columnMatcher);
    assertThat(toolBarFixture.target).isSameAs(expectedToolBar);
  }

  public void shouldFindToolBarWithGivenName() {
    JToolBar expectedToolBar = addJToolBar();
    JToolBarFixture toolBarFixture = fixture.toolBar("toolBar");
    assertThat(toolBarFixture.target).isSameAs(expectedToolBar);
  }

  private JToolBar addJToolBar() {
    JToolBar toolBar = toolBar().withName("toolBar")
                                .withOrientation(HORIZONTAL)
                                .createNew();
    showWindowWith(toolBar);
    return toolBar;
  }

  public void shouldFindTreeByType() {
    JTree expectedTree = addJTree();
    JTreeFixture treeFixture = fixture.tree();
    assertThat(treeFixture.target).isSameAs(expectedTree);
  }

  public void shouldFindTreeWithGivenMatcher() {
    JTree expectedTree = addJTree();
    GenericTypeMatcher<JTree> columnMatcher = new GenericTypeMatcher<JTree>() {
      protected boolean isMatching(JTree tree) {
        return "tree".equals(nameOf(tree));
      }
    };
    JTreeFixture treeFixture = fixture.tree(columnMatcher);
    assertThat(treeFixture.target).isSameAs(expectedTree);
  }

  public void shouldFindTreeWithGivenName() {
    JTree expectedTree = addJTree();
    JTreeFixture treeFixture = fixture.tree("tree");
    assertThat(treeFixture.target).isSameAs(expectedTree);
  }

  private JTree addJTree() {
    JTree tree = tree().withName("tree")
                       .withValues("One", "Two")
                       .createNew();
    showWindowWith(tree);
    return tree;
  }

  private void showWindowWith(Component c) {
    add(window, c);
    window.display();
  }

  private static void add(final Container container, final Component component) {
    container.add(component);
  }
}
