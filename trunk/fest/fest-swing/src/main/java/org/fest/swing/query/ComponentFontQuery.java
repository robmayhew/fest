package org.fest.swing.query;

import java.awt.Component;
import java.awt.Font;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the font of a 
 * <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 */
public class ComponentFontQuery extends GuiQuery<Font> {
  
  private final Component component;

  /**
   * Returns the font of the given <code>{@link Component}</code>. This action is executed in the event dispatch
   * thread.
   * @param component the given <code>Component</code>.
   * @return the font of the given <code>Component</code>.
   */
  public static Font fontOf(Component component) {
    return execute(new ComponentFontQuery(component));
  }
  
  ComponentFontQuery(Component component) {
    this.component = component;
  }

  /**
   * Returns the font of this query's <code>{@link Component}</code>. This action is executed in the event dispatch
   * thread.
   * @return the font of this query's <code>Component</code>.
   */
  protected Font executeInEDT() {
    return component.getFont();
  }
}