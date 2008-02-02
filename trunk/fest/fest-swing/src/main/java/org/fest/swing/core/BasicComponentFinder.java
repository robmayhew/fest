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
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import abbot.finder.Hierarchy;

import org.fest.swing.exception.ComponentLookupException;

import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.System.LINE_SEPARATOR;
import static org.fest.util.Strings.concat;

/**
 * Understands basic component lookup, examining each component in turn. Searches all components of interest in a given
 * hierarchy.
 * 
 * Adapted from <code>abbot.finder.BasicFinder</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * 
 * @author Alex Ruiz
 */
final class BasicComponentFinder {

  private final ComponentPrinter printer;
  
  BasicComponentFinder(ComponentPrinter printer) {
    this.printer = printer;
  }

  Component find(ComponentMatcher m) {
    return find(printer.hierarchy(), m);
  }
  
  Component find(Container root, ComponentMatcher m)  {
    return find(hierarchy(root), m);
  }
  
  private Hierarchy hierarchy(Container root) {
    if (root == null) return printer.hierarchy();
    return new SingleComponentHierarchy(root, printer.hierarchy());
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
    String message = concat(
        "Unable to find component using matcher ", m, ".", 
        LINE_SEPARATOR, LINE_SEPARATOR, "Component hierarchy:", LINE_SEPARATOR, formattedHierarchy(root(h))
    );
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
}
