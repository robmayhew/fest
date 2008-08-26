/*
 * Created on Sep 16, 2007
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
package org.fest.swing.format;

import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.*;

import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;

import static java.awt.Adjustable.VERTICAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link Formatting}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class FormattingTest {

  private static Logger logger = Logger.getAnonymousLogger();

  public void shouldReplaceExistingFormatter() {
    final Class<JComboBox> type = JComboBox.class;
    ComponentFormatter oldFormatter = Formatting.formatter(type);
    ComponentFormatter newFormatter = new ComponentFormatterTemplate() {
      protected String doFormat(Component c) { return null; }

      public Class<? extends Component> targetType() { 
        return type;
      }
    };
    try {
      Formatting.register(newFormatter);
      assertThat(Formatting.formatter(type)).isSameAs(newFormatter);
    } finally {
      Formatting.register(oldFormatter);
    }
  }
  
  public void shouldFormatDialog() {
    JDialog dialog = newJDialog();
    assertThat(formatted(dialog)).contains(classNameOf(dialog))
                                 .contains("name='dialog'")
                                 .contains("title='A dialog'")
                                 .contains("enabled=true")
                                 .contains("modal=false")
                                 .contains("visible=false")
                                 .contains("showing=false");
  }

  private static JDialog newJDialog() {
    return execute(new GuiQuery<JDialog>() {
      protected JDialog executeInEDT() {
        JDialog dialog = new JDialog();
        dialog.setName("dialog");
        dialog.setTitle("A dialog");
        return dialog;
      }
    });
  }

  public void shouldFormatFrame() {
    JFrame frame = newJFrame();
    assertThat(formatted(frame)).contains(classNameOf(frame))
                                .contains("name='frame'")
                                .contains("title='A frame'")
                                .contains("enabled=true")
                                .contains("visible=false")
                                .contains("showing=false");
  }
  
  private static JFrame newJFrame() {
    return execute(new GuiQuery<JFrame>() {
      protected JFrame executeInEDT() {
        JFrame frame = new JFrame("A frame");
        frame.setName("frame");
        return frame;
      }
    });
  }

  public void shouldFormatJComboBox() {
    assertThat(Formatting.formatter(JComboBox.class)).isInstanceOf(JComboBoxFormatter.class);
  }

  public void shouldFormatJButton() {
    JButton button = newJButton();
    assertThat(formatted(button)).contains(classNameOf(button))
                                 .contains("name='button'")
                                 .contains("text='A button'")
                                 .contains("enabled=false")
                                 .contains("visible=true")
                                 .contains("showing=false");
  }

  private static JButton newJButton() {
    return execute(new GuiQuery<JButton>() {
      protected JButton executeInEDT() {
        JButton button = new JButton("A button");
        button.setEnabled(false);
        button.setName("button");
        return button;
      }
    });
  }

  public void shouldFormatJFileChooser() {
    assertThat(Formatting.formatter(JFileChooser.class)).isInstanceOf(JFileChooserFormatter.class);
  }

  public void shouldFormatJLabel() {
    JLabel label = newJLabel();
    assertThat(formatted(label)).contains(classNameOf(label))
                                .contains("name='label'")
                                .contains("text='A label'")
                                .contains("enabled=true")
                                .contains("visible=true")
                                .contains("showing=false");
  }

  private static JLabel newJLabel() {
    return execute(new GuiQuery<JLabel>() {
      protected JLabel executeInEDT() {
        JLabel label = new JLabel("A label");
        label.setName("label");
        return label;
      }
    });
  }

  public void shouldFormatJLayeredPane() {
    JLayeredPane pane = newJLayeredPane();
    assertThat(formatted(pane)).isEqualTo(concat(classNameOf(pane), "[]"));
  }

  private static JLayeredPane newJLayeredPane() {
    return execute(new GuiQuery<JLayeredPane>() {
      protected JLayeredPane executeInEDT() {
        return new JLayeredPane();
      }
    });
  }

  public void shouldFormatJList() {
    assertThat(Formatting.formatter(JList.class)).isInstanceOf(JListFormatter.class);
  }

  public void shouldFormatJMenuBar() {
    JMenuBar menuBar = newJMenuBar();
    assertThat(formatted(menuBar)).isEqualTo(concat(classNameOf(menuBar), "[]"));
  }

  private static JMenuBar newJMenuBar() {
    return execute(new GuiQuery<JMenuBar>() {
      protected JMenuBar executeInEDT() {
        return new JMenuBar();
      }
    });
  }

  public void shouldFormatJMenuItem() {
    JMenuItem menuItem = newJMenuItem();
    assertThat(formatted(menuItem)).contains(classNameOf(menuItem))
                                   .contains("name='menuItem'")
                                   .contains("text='A menu item'")
                                   .contains("selected=true")
                                   .contains("enabled=true")
                                   .contains("visible=true")
                                   .contains("showing=false");
  }

  private static JMenuItem newJMenuItem() {
    return execute(new GuiQuery<JMenuItem>() {
      protected JMenuItem executeInEDT() {
        JMenuItem menuItem = new JMenuItem();
        menuItem.setName("menuItem");
        menuItem.setSelected(true);
        menuItem.setText("A menu item");
        return menuItem;
      }
    });
  }

  public void shouldFormatJOptionPane() {
    assertThat(Formatting.formatter(JOptionPane.class)).isInstanceOf(JOptionPaneFormatter.class);
  }

  public void shouldFormatJPanel() {
    JPanel panel = newJPanel();
    assertThat(formatted(panel)).contains(classNameOf(panel))
                                .contains("name='panel'");
  }

  private static JPanel newJPanel() {
    return execute(new GuiQuery<JPanel>() {
      protected JPanel executeInEDT()  {
        JPanel panel = new JPanel();
        panel.setName("panel");
        return panel;
      }
    });
  }

  public void shouldFormatJPopupMenu() {
    JPopupMenu popupMenu = newJPopupMenu();
    assertThat(formatted(popupMenu)).contains(classNameOf(popupMenu))
                                    .contains("name='popupMenu'")
                                    .contains("label='Menu'")
                                    .contains("enabled=true")
                                    .contains("visible=false")
                                    .contains("showing=false");
  }

  private static JPopupMenu newJPopupMenu() {
    return execute(new GuiQuery<JPopupMenu>() {
      protected JPopupMenu executeInEDT()  {
        JPopupMenu popupMenu = new JPopupMenu("Menu");
        popupMenu.setName("popupMenu");
        return popupMenu;
      }
    });
  }

  public void shouldFormatJRootPane() {
    JRootPane pane = newJRootPane();
    assertThat(formatted(pane)).isEqualTo(concat(classNameOf(pane), "[]"));
  }

  private static JRootPane newJRootPane() {
    return execute(new GuiQuery<JRootPane>() {
      protected JRootPane executeInEDT()  {
        return new JRootPane();
      }
    });
  }

  public void shouldFormatJScrollBar() {
    JScrollBar scrollBar = newJScrollBar();
    assertThat(formatted(scrollBar)).contains(classNameOf(scrollBar))
                                    .contains("name='scrollBar'")
                                    .contains("value=20")
                                    .contains("blockIncrement=10")
                                    .contains("minimum=0")
                                    .contains("maximum=60")
                                    .contains("enabled=true")
                                    .contains("visible=true")
                                    .contains("showing=false");
  }

  private static JScrollBar newJScrollBar() {
    return execute(new GuiQuery<JScrollBar>() {
      protected JScrollBar executeInEDT()  {
        JScrollBar scrollBar = new JScrollBar(VERTICAL, 20, 10, 0, 60);
        scrollBar.setName("scrollBar");
        return scrollBar;
      }
    });
  }

  public void shouldFormatJScrollPane() {
    JScrollPane scrollPane = newJScrollPane();
    assertThat(formatted(scrollPane)).contains(classNameOf(scrollPane))
                                     .contains("name='scrollPane'")
                                     .contains("enabled=true")
                                     .contains("visible=true")
                                     .contains("showing=false");
  }

  private static JScrollPane newJScrollPane() {
    return execute(new GuiQuery<JScrollPane>() {
      protected JScrollPane executeInEDT()  {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setName("scrollPane");
        return scrollPane;
      }
    });
  }

  public void shouldFormatJSlider() {
    JSlider slider = newJSlider();
    assertThat(formatted(slider)).contains(classNameOf(slider))
                                 .contains("name='slider'")   
                                 .contains("value=6")   
                                 .contains("minimum=2")   
                                 .contains("maximum=8")   
                                 .contains("enabled=true")   
                                 .contains("visible=true")   
                                 .contains("showing=false"); 
  }

  private static JSlider newJSlider() {
    return execute(new GuiQuery<JSlider>() {
      protected JSlider executeInEDT()  {
        JSlider slider = new JSlider(2, 8, 6);
        slider.setName("slider");
        return slider;
      }
    });
  }

  public void shouldFormatJSpinner() {
    JSpinner spinner = newJSpinner();
    assertThat(formatted(spinner)).contains(classNameOf(spinner))
                                  .contains("name='spinner'")
                                  .contains("value=6")
                                  .contains("enabled=true")
                                  .contains("visible=true")
                                  .contains("showing=false");
  }

  private static JSpinner newJSpinner() {
    return execute(new GuiQuery<JSpinner>() {
      protected JSpinner executeInEDT()  {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(6, 2, 8, 1));
        spinner.setName("spinner");
        return spinner;
      }
    });
  }

  public void shouldFormatJTabbedPane() {
    assertThat(Formatting.formatter(JTabbedPane.class)).isInstanceOf(JTabbedPaneFormatter.class);
  }

  public void shouldFormatJTable() {
    assertThat(Formatting.formatter(JTable.class)).isInstanceOf(JTableFormatter.class);
  }

  public void shouldFormatJPasswordField() {
    JPasswordField passwordField = newJPasswordField();
    assertThat(formatted(passwordField)).contains(classNameOf(passwordField))
                                        .contains("name='passwordField'")
                                        .contains("enabled=true")
                                        .contains("visible=true")
                                        .contains("showing=false");
  }

  private static JPasswordField newJPasswordField() {
    return execute(new GuiQuery<JPasswordField>() {
      protected JPasswordField executeInEDT()  {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setName("passwordField");
        return passwordField;
      }
    });
  }

  public void shouldFormatJTextComponent() {
    JTextField textField = newJTextField();
    assertThat(formatted(textField)).contains(classNameOf(textField))
                                    .contains("name='textField'")
                                    .contains("text='Hello'")
                                    .contains("enabled=true")
                                    .contains("visible=true")
                                    .contains("showing=false");
  }

  private static JTextField newJTextField() {
    return execute(new GuiQuery<JTextField>() {
      protected JTextField executeInEDT()  {
        JTextField textField = new JTextField("Hello");
        textField.setName("textField");
        return textField;
      }
    });
  }

  public void shouldFormatJToggleButton() {
    JToggleButton toggleButton = newJToggleButton();
    assertThat(formatted(toggleButton)).contains(classNameOf(toggleButton))
                                       .contains("name='toggleButton'")
                                       .contains("text='A toggle button'")
                                       .contains("selected=true")
                                       .contains("enabled=true")
                                       .contains("visible=true")
                                       .contains("showing=false");
  }

  private static JToggleButton newJToggleButton() {
    return execute(new GuiQuery<JToggleButton>() {
      protected JToggleButton executeInEDT()  {
        JToggleButton toggleButton = new JToggleButton();
        toggleButton.setName("toggleButton");
        toggleButton.setSelected(true);
        toggleButton.setText("A toggle button");
        return toggleButton;
      }
    });
  }

  public void shouldFormatJTree() {
    assertThat(Formatting.formatter(JTree.class)).isInstanceOf(JTreeFormatter.class);
  }

  private String formatted(Component c) {
    String formatted = Formatting.format(c);
    logger.info(concat("formatted: ", formatted));
    return formatted;
  }

  private static String classNameOf(Object o) {
    return o.getClass().getName();
  }
  
  public void shouldReturnComponentIsNullIfComponentIsNull() {
    assertThat(Formatting.format(null)).isEqualTo("Null Component");
  }
}
