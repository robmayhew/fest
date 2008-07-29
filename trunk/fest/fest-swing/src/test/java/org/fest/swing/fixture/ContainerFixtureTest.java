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

import java.awt.Adjustable;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.task.*;
import org.fest.swing.testing.TestWindow;

import static java.awt.Color.RED;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.task.GetAbstractButtonTextTask.textOf;
import static org.fest.swing.task.GetComponentBackgroundTask.backgroundOf;
import static org.fest.swing.task.GetComponentNameTask.nameOf;
import static org.fest.swing.task.GetDialogTitleTask.titleOf;
import static org.fest.swing.task.GetJComboBoxItemCountTask.itemCountOf;
import static org.fest.swing.task.GetJOptionPaneMessageTask.messageOf;
import static org.fest.swing.task.GetJTabbedPaneTabCountTask.tabCountOf;
import static org.fest.swing.task.GetJTableRowCountTask.rowCountOf;
import static org.fest.swing.task.GetJToolBarOrientationTask.isHorizontal;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureTest {

  private static final Dimension PREFERRED_DIMENSION = new Dimension(100, 100);
  
  private ContainerFixture<TestWindow> fixture;
  private Robot robot;
  private TestWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new GuiTask<TestWindow>() {
      protected TestWindow executeInEDT() {
        return new TestWindow(ContainerFixtureTest.class);
      }
    }.run();
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
    return new GuiTask<JButton>() {
      protected JButton executeInEDT() {
        JButton button = new JButton("A Button");
        button.setName("button");
        addToWindowAndDisplay(button);
        return button;
      }
    }.run();
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
    return new GuiTask<JCheckBox>() {
      protected JCheckBox executeInEDT() {
        JCheckBox checkBox = new JCheckBox("A CheckBox");
        checkBox.setName("checkBox");
        addToWindowAndDisplay(checkBox);
        return checkBox;
      }
    }.run();
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
        return itemCountOf(comboBox) == 3;
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
    return new GuiTask<JComboBox>() {
      protected JComboBox executeInEDT() {
        JComboBox comboBox = new JComboBox(array("first", "second", "third"));
        comboBox.setName("comboBox");
        addToWindowAndDisplay(comboBox);
        return comboBox;
      }
    }.run();
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
    return new GuiTask<JDialog>() {
      protected JDialog executeInEDT() {
        JDialog dialog = new JDialog(window, "A Dialog");
        dialog.setName("dialog");
        window.display();
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
      }
    }.run();
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
    return new GuiTask<JFileChooser>() {
      protected JFileChooser executeInEDT() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("fileChooser");
        addToWindowAndDisplay(fileChooser);
        return fileChooser;
      }
    }.run();
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
        return "A Label".equals(GetJLabelTextTask.textOf(label));
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
    return new GuiTask<JLabel>() {
      protected JLabel executeInEDT() {
        JLabel label = new JLabel("A Label");
        label.setName("label");
        addToWindowAndDisplay(label);
        return label;
      }
    }.run();
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
    return new GuiTask<JList>() {
      protected JList executeInEDT() {
        JList list = new JList();
        list.setName("list");
        addToWindowAndDisplay(list);
        return list;
      }
    }.run();
  }

  public void shouldFindMenuWithGivenMatcher() {
    JMenuItem expectedMenuItem = addJMenuItem();
    GenericTypeMatcher<JMenuItem> textMatcher = new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "A Submenu".equals(GetAbstractButtonTextTask.textOf(menuItem));
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
    return new GuiTask<JMenuItem>() {
      protected JMenuItem executeInEDT() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("A Menu");
        JMenuItem subMenu = new JMenu("A Submenu");
        subMenu.setName("subMenu");
        menu.add(subMenu);
        menuBar.add(menu);
        window.setJMenuBar(menuBar);
        window.display();
        return subMenu;
      }
    }.run();
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
    return new GuiTask<JPanel>() {
      protected JPanel executeInEDT() {
        JPanel panel = new JPanel();
        window.getRootPane().setContentPane(panel);
        panel.setName("panel");
        panel.setBackground(RED);
        window.display();
        return panel;
      }
    }.run();
  }

  public void shouldFindOptionPane() {
    final JButton button = addJButton();
    // show option pane when clicking button
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        button.setName("button");
        button.addMouseListener(new MouseAdapter() {
          @Override public void mousePressed(MouseEvent e) {
            JOptionPane.showMessageDialog(window, "A Message");
          }
        });
        return null;
      }
    }.run();
    robot.click(button);
    JOptionPaneFixture optionPaneFixture = fixture.optionPane();
    assertThat(messageOf(optionPaneFixture.target)).isEqualTo("A Message");
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
        return "A Radio Button".equals(GetAbstractButtonTextTask.textOf(radioButton));
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
    return new GuiTask<JRadioButton>() {
      protected JRadioButton executeInEDT() {
        JRadioButton radioButton = new JRadioButton("A Radio Button");
        radioButton.setName("radioButton");
        addToWindowAndDisplay(radioButton);
        return radioButton;
      }
    }.run();
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
        return GetJScrollBarValueTask.valueOf(scrollBar) == 10;
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
    return new GuiTask<JScrollBar>() {
      protected JScrollBar executeInEDT() {
        JScrollBar scrollBar = new JScrollBar(Adjustable.HORIZONTAL);
        scrollBar.setName("scrollBar");
        scrollBar.setValue(10);
        addToWindowAndDisplay(scrollBar);
        return scrollBar;
      }
    }.run();
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
    return new GuiTask<JScrollPane>() {
      protected JScrollPane executeInEDT() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setName("scrollPane");
        scrollPane.setPreferredSize(PREFERRED_DIMENSION);
        addToWindowAndDisplay(scrollPane);
        return scrollPane;
      }
    }.run();
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
        return GetJSliderValueTask.valueOf(slider) == 15;
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
    return new GuiTask<JSlider>() {
      protected JSlider executeInEDT() {
        JSlider slider = new JSlider(10, 20, 15);
        slider.setName("slider");
        addToWindowAndDisplay(slider);
        return slider;
      }
    }.run();
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
        return GetJSpinnerValueTask.valueOf(spinner).equals("One");
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
    return new GuiTask<JSpinner>() {
      protected JSpinner executeInEDT() {
        JSpinner spinner = new JSpinner(new SpinnerListModel(array("One", "Two")));
        spinner.setName("spinner");
        addToWindowAndDisplay(spinner);
        return spinner;
      }
    }.run();
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
        Component rightComponent = new GuiTask<Component>() {
          protected Component executeInEDT() {
            return splitPane.getRightComponent();
          }
        }.run();
        return rightComponent instanceof JList;
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
    return new GuiTask<JSplitPane>() {
      protected JSplitPane executeInEDT() {
        JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT, new JList(), new JList());
        splitPane.setName("splitPane");
        splitPane.setPreferredSize(PREFERRED_DIMENSION);
        addToWindowAndDisplay(splitPane);
        return splitPane;
      }
    }.run();
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
        return tabCountOf(tabbedPane) == 1;
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
    return new GuiTask<JTabbedPane>() {
      protected JTabbedPane executeInEDT() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setName("tabbedPane");
        tabbedPane.addTab("A Tab", new JPanel());
        addToWindowAndDisplay(tabbedPane);
        return tabbedPane;
      }
    }.run();
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
        return rowCountOf(table) == 6;
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
    return new GuiTask<JTable>() {
      protected JTable executeInEDT() {
        JTable table = new JTable(6, 8);
        table.setName("table");
        addToWindowAndDisplay(table);
        return table;
      }
    }.run();
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
        int columns = new GuiTask<Integer>() {
          protected Integer executeInEDT() {
            return textField.getColumns();
          }
        }.run();
        return columns == 10;
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
    return new GuiTask<JTextComponent>() {
      protected JTextComponent executeInEDT() {
        JTextField textField = new JTextField(10);
        textField.setName("textField");
        addToWindowAndDisplay(textField);
        return textField;
      }
    }.run();
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
        return "A ToggleButton".equals(GetAbstractButtonTextTask.textOf(toggleButton));
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
    return new GuiTask<JToggleButton>() {
      protected JToggleButton executeInEDT() {
        JToggleButton toggleButton = new JToggleButton("A ToggleButton");
        toggleButton.setName("toggleButton");
        addToWindowAndDisplay(toggleButton);
        return toggleButton;
      }
    }.run();
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
        return isHorizontal(toolBar) && "toolBar".equals(nameOf(toolBar));
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
    return new GuiTask<JToolBar>() {
      protected JToolBar executeInEDT() {
        JToolBar toolBar = new JToolBar(HORIZONTAL);
        toolBar.setName("toolBar");
        addToWindowAndDisplay(toolBar);
        return toolBar;
      }
    }.run();
  }

  private void addToWindowAndDisplay(Component c) {
    window.add(c);
    window.display();
  }
}
