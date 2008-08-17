package org.fest.swing.driver;

import javax.swing.text.JTextComponent;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the selected text of a
 * <code>{@link JTextComponent}</code>.
 * 
 * @author Alex Ruiz
 */
class JTextComponentSelectedTextQuery extends GuiQuery<String> {
  
  private final JTextComponent textBox;

  static String selectedTextOf(JTextComponent textBox) {
    return execute(new JTextComponentSelectedTextQuery(textBox));
  }
  
  private JTextComponentSelectedTextQuery(JTextComponent textBox) {
    this.textBox = textBox;
  }
  
  protected String executeInEDT() throws Throwable {
    return textBox.getSelectedText();
  }
}