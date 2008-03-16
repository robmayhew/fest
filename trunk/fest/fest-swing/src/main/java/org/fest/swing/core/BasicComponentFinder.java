/*
 * Created on May 14, 2007
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
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import abbot.finder.TestHierarchy;

import org.fest.swing.exception.ComponentLookupException;

import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.System.LINE_SEPARATOR;
import static org.fest.util.Strings.concat;

/**
 * Understands GUI <code>{@link java.awt.Component}</code> lookup.
 * 
 * @author Alex Ruiz
 */
public final class BasicComponentFinder implements ComponentFinder {

  private final Hierarchy hierarchy;
  private final ComponentPrinter printer;

  private boolean includeHierarchyInComponentLookupException;

  /**
   * Creates a new <code>{@link BasicComponentFinder}</code> with a new AWT hierarchy. <code>{@link Component}</code>s
   * created before the created <code>{@link BasicComponentFinder}</code> cannot be accessed by the created
   * <code>{@link BasicComponentFinder}</code>.
   * @return the created finder.
   */
  public static ComponentFinder finderWithNewAwtHierarchy() {
    return new BasicComponentFinder(new TestHierarchy());
  }

  /**
   * Creates a new <code>{@link BasicComponentFinder}</code> that has access to all the GUI components in the AWT
   * hierarchy.
   * @return the created finder.
   */
  public static ComponentFinder finderWithCurrentAwtHierarchy() {
    return new BasicComponentFinder(new AWTHierarchy());
  }

  /**
   * Creates a new <code>{@link BasicComponentFinder}</code>.
   * @param hierarchy provides access to the components in the AWT hierarchy.
   */
  BasicComponentFinder(Hierarchy hierarchy) {
    this.hierarchy = hierarchy;
    printer = new BasicComponentPrinter(hierarchy);
    includeHierarchyIfComponentNotFound(true);
  }

  /** ${@inheritDoc} */
  public ComponentPrinter printer() { return printer; }
  
  /** ${@inheritDoc} */
  public <T extends Component> T findByType(Class<T> type) {
    return findByType(type, false);
  }

  /** ${@inheritDoc} */
  public <T extends Component> T findByType(Class<T> type, boolean showing) {
    return type.cast(find(new TypeMatcher(type, showing)));
  }
  
  /** ${@inheritDoc} */
  public <T extends Component> T findByType(Container root, Class<T> type) {
    return findByType(root, type, false);
  }

  /** ${@inheritDoc} */
  public <T extends Component> T findByType(Container root, Class<T> type, boolean showing) {
    return type.cast(find(root, new TypeMatcher(type, showing)));
  }

  /** ${@inheritDoc} */
  public <T extends Component> T findByName(String name, Class<T> type) {
    return findByName(name, type, false);
  }

  /** ${@inheritDoc} */
  public <T extends Component> T findByName(String name, Class<T> type, boolean showing) {
    Component found = find(new NameAndTypeMatcher(name, type, showing));
    return type.cast(found);
  }

  /** ${@inheritDoc} */
  public Component findByName(String name) {
    return findByName(name, false);
  }

  /** ${@inheritDoc} */
  public Component findByName(String name, boolean showing) {
    return find(new NameMatcher(name, showing));
  }

  /** ${@inheritDoc} */
  @SuppressWarnings("unchecked") 
  public <T extends Component> T find(GenericTypeMatcher<T> m) {
    return (T)find((ComponentMatcher)m);
  }

  /** ${@inheritDoc} */
  public Component find(ComponentMatcher m) {
    return find(hierarchy, m);
  }
  
  /** ${@inheritDoc} */
  public <T extends Component> T findByName(Container root, String name, Class<T> type) {
    return findByName(root, name, type, false);
  }

  /** ${@inheritDoc} */
  public <T extends Component> T findByName(Container root, String name, Class<T> type, boolean showing) {
    Component found = find(root, new NameAndTypeMatcher(name, type, showing));
    return type.cast(found);
  }

  /** ${@inheritDoc} */
  public Component findByName(Container root, String name) {
    return findByName(root, name, false);
  }

  /** ${@inheritDoc} */
  public Component findByName(Container root, String name, boolean showing) {
    return find(root, new NameMatcher(name, showing));
  }
  
  /** ${@inheritDoc} */
  @SuppressWarnings("unchecked") 
  public <T extends Component> T find(Container root, GenericTypeMatcher<T> m) {
    return (T)find(root, (ComponentMatcher)m);
  }

  /** ${@inheritDoc} */
  public Component find(Container root, ComponentMatcher m) {
    return find(hierarchy(root), m);
  }

  private Hierarchy hierarchy(Container root) {
    if (root == null) return hierarchy;
    return new SingleComponentHierarchy(root, hierarchy);
  }
  
  private Component find(Hierarchy hierarchy, ComponentMatcher matcher)  {
    Set<Component> found = new HashSet<Component>();
    for (Object o : hierarchy.getRoots()) find(hierarchy, matcher, (Component)o, found);
    if (found.isEmpty()) throw componentNotFound(hierarchy, matcher); 
    if (found.size() > 1) throw multipleComponentsFound(found, matcher);
    return found.iterator().next();
  }

  private void find(Hierarchy h, ComponentMatcher m, Component root, Set<Component> found) {
    for (Object o : h.getComponents(root)) find(h, m, (Component)o, found);
    if (m.matches(root)) found.add(root);
  }
  
  private ComponentLookupException componentNotFound(Hierarchy h, ComponentMatcher m) {
    String message = concat("Unable to find component using matcher ", m, ".");
    if (includeHierarchyIfComponentNotFound())
      message = concat(message, 
          LINE_SEPARATOR, LINE_SEPARATOR, "Component hierarchy:", LINE_SEPARATOR, formattedHierarchy(root(h)));
    throw new ComponentLookupException(message);
  }

  private Container root(Hierarchy h) {
    if (h instanceof SingleComponentHierarchy) return ((SingleComponentHierarchy)h).root();
    return null;
  }
  
  private String formattedHierarchy(Container root) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(out, true);
    printer.printComponents(printStream, root);
    printStream.flush();
    return new String(out.toByteArray());    
  }

  private ComponentLookupException multipleComponentsFound(Set<Component> found, ComponentMatcher m) {
    StringBuilder message = new StringBuilder();
    message.append("Found more than one component using matcher ").append(m).append(".").append(LINE_SEPARATOR)
           .append(LINE_SEPARATOR)
           .append("Found:");
    for (Component c : found) message.append(LINE_SEPARATOR).append(format(c));
    if (!found.isEmpty()) message.append(LINE_SEPARATOR);
    throw new ComponentLookupException(message.toString(), found);    
  }

  /** ${@inheritDoc} */
  public boolean includeHierarchyIfComponentNotFound() {
    return includeHierarchyInComponentLookupException;
  }

  /** ${@inheritDoc} */
  public void includeHierarchyIfComponentNotFound(boolean newValue) {
    includeHierarchyInComponentLookupException = newValue;
  }
}
