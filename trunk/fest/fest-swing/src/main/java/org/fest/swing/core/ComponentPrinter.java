/*
 * Created on Dec 22, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Container;
import java.io.PrintStream;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import abbot.finder.TestHierarchy;

import static org.fest.swing.format.Formatting.format;

import org.fest.swing.format.Formatting;

/**
 * Understands printing the <code>String</code> representation of <code>{@link java.awt.Component}</code>s to
 * facilitate debugging.
 * 
 * @author Alex Ruiz
 */
public final class ComponentPrinter {

  private final Hierarchy hierarchy;

  /**
   * Creates a new <code>{@link ComponentPrinter}</code> with a new AWT hierarchy. <code>{@link Component}</code>s
   * created before the created <code>{@link ComponentPrinter}</code> cannot be accessed by the created
   * <code>{@link ComponentPrinter}</code>.
   * @return the created finder.
   */
  public static ComponentPrinter printerWithNewAwtHierarchy() {
    return new ComponentPrinter(new TestHierarchy());
  }

  /**
   * Creates a new <code>{@link ComponentPrinter}</code> that has access to all the GUI components in the AWT
   * hierarchy.
   * @return the created printer.
   */
  public static ComponentPrinter printerWithCurrentAwtHierarchy() {
    return new ComponentPrinter(new AWTHierarchy());
  }

  /**
   * Creates a new <code>{@link ComponentPrinter}</code>.
   * @param hierarchy provides access to the components in the AWT hierarchy.
   */
  ComponentPrinter(Hierarchy hierarchy) {
    this.hierarchy = hierarchy;
  }
  
  /**
   * Prints all the components (as <code>String</code>s) in the hierarchy.
   * @param out the output stream where to print the components to.
   * @see Formatting#format(Component)
   */
  public void printComponents(PrintStream out) {
    printComponents(out, (Container)null);
  }

  /**
   * Prints all the components (as <code>String</code>s) in the hierarchy under the given root.
   * @param out the output stream where to print the components to.
   * @param root the root used as the starting point of the search.
   * @see Formatting#format(Component)
   */
  public void printComponents(PrintStream out, Container root) {
    printComponents(out, null, root);
  }

  /**
   * Prints only the components of the given type (as <code>String</code>s) in the hierarchy.
   * @param out the output stream where to print the components to.
   * @param type the type of components to print.
   * @see Formatting#format(Component)
   */
  public void printComponents(PrintStream out, Class<? extends Component> type) {
    printComponents(out, type, null);
  }
  
  /**
   * Prints all the components of the given type (as <code>String</code>s) in the hierarchy under the given root.
   * @param out the output stream where to print the components to.
   * @param type the type of components to print.
   * @param root the root used as the starting point of the search.
   * @see Formatting#format(Component)
   */
  public void printComponents(PrintStream out, Class<? extends Component> type, Container root) {
    print(hierarchy(root), type, out);
  }

  private Hierarchy hierarchy(Container root) {
    return root != null ? new SingleComponentHierarchy(root, hierarchy) : hierarchy;
  }
  
  private static void print(Hierarchy hierarchy, Class<? extends Component> type, PrintStream out) {
    for (Object o : hierarchy.getRoots()) print((Component)o, hierarchy, type, 0, out);
  }
  
  private static void print(Component c, Hierarchy h, Class<? extends Component> type, int level, PrintStream out) {
    if (type == null || type.isAssignableFrom(c.getClass())) print(c, level, out);
    for (Object o : h.getComponents(c)) print((Component)o, h, type, level + 1, out);
  }

  private static void print(Component c, int level, PrintStream out) {
    for (int i = 0; i < level; i++) out.print("  ");
    out.println(format(c));
  }
}
