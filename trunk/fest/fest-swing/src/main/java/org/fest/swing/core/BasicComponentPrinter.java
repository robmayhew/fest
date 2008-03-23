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

import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;

import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.hierarchy.NewHierarchy.ignoreExistingComponents;

/**
 * Understands printing the <code>String</code> representation of <code>{@link java.awt.Component}</code>s to
 * facilitate debugging.
 * 
 * @author Alex Ruiz
 */
public final class BasicComponentPrinter implements ComponentPrinter {

  private static final String INDENTATION = "  ";
  
  private final ComponentHierarchy hierarchy;

  /**
   * Creates a new <code>{@link BasicComponentPrinter}</code> with a new AWT hierarchy. <code>{@link Component}</code>s
   * created before the created <code>{@link BasicComponentPrinter}</code> cannot be accessed by the created
   * <code>{@link BasicComponentPrinter}</code>.
   * @return the created finder.
   */
  public static ComponentPrinter printerWithNewAwtHierarchy() {
    return new BasicComponentPrinter(ignoreExistingComponents());
  }

  /**
   * Creates a new <code>{@link BasicComponentPrinter}</code> that has access to all the GUI components in the AWT
   * hierarchy.
   * @return the created printer.
   */
  public static ComponentPrinter printerWithCurrentAwtHierarchy() {
    return new BasicComponentPrinter(new ExistingHierarchy());
  }

  /**
   * Creates a new <code>{@link BasicComponentPrinter}</code>.
   * @param hierarchy provides access to the components in the AWT hierarchy.
   */
  BasicComponentPrinter(ComponentHierarchy hierarchy) {
    this.hierarchy = hierarchy;
  }
  
  ComponentHierarchy hierarchy() { return hierarchy; }
  
  /** ${@inheritDoc} */
  public void printComponents(PrintStream out) {
    printComponents(out, (Container)null);
  }

  /** ${@inheritDoc} */
  public void printComponents(PrintStream out, Container root) {
    printComponents(out, null, root);
  }

  /** ${@inheritDoc} */
  public void printComponents(PrintStream out, Class<? extends Component> type) {
    printComponents(out, type, null);
  }
  
  /** ${@inheritDoc} */
  public void printComponents(PrintStream out, Class<? extends Component> type, Container root) {
    print(hierarchy(root), type, out);
  }

  private ComponentHierarchy hierarchy(Container root) {
    return root != null ? new SingleComponentHierarchy(root, hierarchy) : hierarchy;
  }
  
  private static void print(ComponentHierarchy hierarchy, Class<? extends Component> type, PrintStream out) {
    for (Component c : hierarchy.roots()) print(c, hierarchy, type, 0, out);
  }
  
  private static void print(Component c, ComponentHierarchy h, Class<? extends Component> type, int level,
      PrintStream out) {
    if (type == null || type.isAssignableFrom(c.getClass())) print(c, level, out);
    for (Component child : h.childrenOf(c))
      print(child, h, type, level + 1, out);
  }

  private static void print(Component c, int level, PrintStream out) {
    for (int i = 0; i < level; i++) out.print(INDENTATION);
    out.println(format(c));
  }
}
