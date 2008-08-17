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
import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.query.AbstractButtonTextQuery;
import org.fest.swing.query.JLabelTextQuery;
import org.fest.swing.testing.TestWindow;

import static java.awt.Color.RED;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.query.AbstractButtonTextQuery.textOf;
import static org.fest.swing.query.ComponentBackgroundQuery.backgroundOf;
import static org.fest.swing.query.ComponentNameQuery.nameOf;
import static org.fest.swing.query.DialogTitleQuery.titleOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import static org.fest.swing.core.GuiActionRunner.*;

/**
 * Tests for <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI, enabled = false)
public class ContainerFixtureTest {

  private static final Dimension PREFERRED_DIMENSION = new Dimension(100, 100);

  private ContainerFixture<TestWindow> fixture;
  private Robot robot;
  private TestWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TestWindow.showNewInTest(getClass());
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
    return execute(new GuiQuery<JButton>() {
      protected JButton executeInEDT() {
        JButton button = new JButton("A Button");
        button.setName("button");
        addToWindowAndDisplay(button);
        return button;
      }
    });
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
    return execute(new GuiQuery<JCheckBox>() {
      protected JCheckBox executeInEDT() {
        JCheckBox checkBox = new JCheckBox("A CheckBox");
        checkBox.setName("checkBox");
        addToWindowAndDisplay(checkBox);
        return checkBox;
      }
    });
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
    return execute(new GuiQuery<JComboBox>() {
      protected JComboBox executeInEDT() {
        JComboBox comboBox = new JComboBox(array("first", "second", "third"));
        comboBox.setName("comboBox");
        addToWindowAndDisplay(comboBox);
        return comboBox;
      }
    });
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
    return execute(new GuiQuery<JDialog>() {
      protected JDialog executeInEDT() {
        JDialog dialog = new JDialog(window, "A Dialog");
        dialog.setName("dialog");
        window.display();
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
      }
    });
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
    return execute(new GuiQuery<JFileChooser>() {
      protected JFileChooser executeInEDT() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("fileChooser");
        addToWindowAndDisplay(fileChooser);
        return fileChooser;
      }
    });
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
    return execute(new GuiQuery<JLabel>() {
      protected JLabel executeInEDT() {
        JLabel label = new JLabel("A Label");
        label.setName("label");
        addToWindowAndDisplay(label);
        return label;
      }
    });
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
    return execute(new GuiQuery<JList>() {
      protected JList executeInEDT() {
        JList list = new JList();
        list.setName("list");
        addToWindowAndDisplay(list);
        return list;
      }
    });
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
    return execute(new GuiQuery<JMenuItem>() {
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
    });
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
    return execute(new GuiQuery<JPanel>() {
      protected JPanel executeInEDT() {
        JPanel panel = new JPanel();
        window.getRootPane().setContentPane(panel);
        panel.setName("panel");
        panel.setBackground(RED);
        window.display();
        return panel;
      }
    });
  }

  public void shouldFindOptionPane() {
    final JButton button = addJButton();
    // show option pane when clicking button
    robot.invokeAndWait(new Runnable() {
      public void run() {
        button.setName("button");
        button.addMouseListener(new MouseAdapter() {
          @Override public void mousePressed(MouseEvent e) {
            JOptionPane.showMessageDialog(window, "A Message");
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
      protected Object executeInEDT() throws Throwable {
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
    return execute(new GuiQuery<JRadioButton>() {
      protected JRadioButton executeInEDT() {
        JRadioButton radioButton = new JRadioButton("A Radio Button");
        radioButton.setName("radioButton");
        addToWindowAndDisplay(radioButton);
        return radioButton;
      }
    });
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
    return execute(new GuiQuery<JScrollBar>() {
      protected JScrollBar executeInEDT() {
        JScrollBar scrollBar = new JScrollBar(Adjustable.HORIZONTAL);
        scrollBar.setName("scrollBar");
        scrollBar.setValue(10);
        addToWindowAndDisplay(scrollBar);
        return scrollBar;
      }
    });
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
    return execute(new GuiQuery<JScrollPane>() {
      protected JScrollPane executeInEDT() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setName("scrollPane");
        scrollPane.setPreferredSize(PREFERRED_DIMENSION);
        addToWindowAndDisplay(scrollPane);
        return scrollPane;
      }
    });
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
    return execute(new GuiQuery<JSlider>() {
      protected JSlider executeInEDT() {
        JSlider slider = new JSlider(10, 20, 15);
        slider.setName("slider");
        addToWindowAndDisplay(slider);
        return slider;
      }
    });
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
    return execute(new GuiQuery<JSpinner>() {
      protected JSpinner executeInEDT() {
        JSpinner spinner = new JSpinner(new SpinnerListModel(array("One", "Two")));
        spinner.setName("spinner");
        addToWindowAndDisplay(spinner);
        return spinner;
      }
    });
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
        Component rightComponent = execute(new GuiQuery<Component>() {
          protected Component executeInEDT() {
            return splitPane.getRightComponent();
          }
        });
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
    return execute(new GuiQuery<JSplitPane>() {
      protected JSplitPane executeInEDT() {
        JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT, new JList(), new JList());
        splitPane.setName("splitPane");
        splitPane.setPreferredSize(PREFERRED_DIMENSION);
        addToWindowAndDisplay(splitPane);
        return splitPane;
      }
    });
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
    return execute(new GuiQuery<JTabbedPane>() {
      protected JTabbedPane executeInEDT() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setName("tabbedPane");
        tabbedPane.addTab("A Tab", new JPanel());
        addToWindowAndDisplay(tabbedPane);
        return tabbedPane;
      }
    });
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
    return execute(new GuiQuery<JTable>() {
      protected JTable executeInEDT() {
        JTable table = new JTable(6, 8);
        table.setName("table");
        addToWindowAndDisplay(table);
        return table;
      }
    });
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
        int columns = execute(new GuiQuery<Integer>() {
          protected Integer executeInEDT() {
            return textField.getColumns();
          }
        });
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
    return execute(new GuiQuery<JTextComponent>() {
      protected JTextComponent executeInEDT() {
        JTextField textField = new JTextField(10);
        textField.setName("textField");
        addToWindowAndDisplay(textField);
        return textField;
      }
    });
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
    return execute(new GuiQuery<JToggleButton>() {
      protected JToggleButton executeInEDT() {
        JToggleButton toggleButton = new JToggleButton("A ToggleButton");
        toggleButton.setName("toggleButton");
        addToWindowAndDisplay(toggleButton);
        return toggleButton;
      }
    });
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
    return execute(new GuiQuery<JToolBar>() {
      protected JToolBar executeInEDT() {
        JToolBar toolBar = new JToolBar(HORIZONTAL);
        toolBar.setName("toolBar");
        addToWindowAndDisplay(toolBar);
        return toolBar;
      }
    });
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
    return execute(new GuiQuery<JTree>() {
      protected JTree executeInEDT() {
        JTree tree = new JTree(array("One", "Two"));
        tree.setName("tree");
        addToWindowAndDisplay(tree);
        return tree;
      }
    });
  }

  private void addToWindowAndDisplay(Component c) {
    window.add(c);
    window.display();
  }
}
