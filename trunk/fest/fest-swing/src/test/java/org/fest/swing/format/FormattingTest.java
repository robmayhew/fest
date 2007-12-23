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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.format;

import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;

import org.fest.swing.testing.TestFrame;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Formatting}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FormattingTest {

  private static Logger logger = Logger.getAnonymousLogger();

  @Test public void shouldFormatDialog() {
    JDialog dialog = new JDialog();
    dialog.setTitle("A dialog");
    dialog.setName("dialog");
    String formatted = formatted(dialog);
    assertThat(formatted).isEqualTo(expected(dialog, "[name='dialog', title='A dialog', enabled=true, showing=false]"));
  }

  @Test public void shouldFormatFrame() {
    TestFrame frame = TestFrame.showInTest(getClass());
    frame.setName("frame");
    String formatted = formatted(frame);
    assertThat(formatted).isEqualTo(
        expected(frame, "[name='frame', title='FormattingTest', enabled=true, showing=true]"));
    frame.destroy();
  }
  
  @Test public void shouldFormatJButton() {
    JButton button = new JButton("A button");
    button.setName("button");
    button.setEnabled(false);
    String formatted = formatted(button);
    assertThat(formatted).isEqualTo(expected(button, "[name='button', text='A button', enabled=false]"));
  }

  @Test public void shouldFormatJLabel() {
    JLabel label = new JLabel("A label");
    label.setName("label");
    String formatted = formatted(label);
    assertThat(formatted).isEqualTo(expected(label, "[name='label', text='A label', enabled=true]"));
  }

  @Test public void shouldFormatJLayeredPane() {
    JLayeredPane pane = new JLayeredPane();
    String formatted = formatted(pane);
    assertThat(formatted).isEqualTo(expected(pane, "[]"));
  }
  
  @Test public void shouldFormatJPanel() {
    JPanel panel = new JPanel();
    panel.setName("panel");
    String formatted = formatted(panel);
    assertThat(formatted).isEqualTo(expected(panel, "[name='panel']"));    
  }
  
  @Test public void shouldFormatJRootPane() {
    JRootPane pane = new JRootPane();
    String formatted = formatted(pane);
    assertThat(formatted).isEqualTo(expected(pane, "[]"));
  }

  private String formatted(Component c) {
    String formatted = Formatting.format(c);
    logger.info(concat("formatted: ", formatted));
    return formatted;
  }
  
  private String expected(Component c, String properties) {
    return concat(c.getClass().getName(), properties);
  }
  
  @Test public void shouldReturnComponentIsNullIfComponentIsNull() {
    assertThat(Formatting.format(null)).isEqualTo("Null Component");
  }
}
