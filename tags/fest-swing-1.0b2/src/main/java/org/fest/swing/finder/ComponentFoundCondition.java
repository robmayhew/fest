/*
 * Created on Nov 15, 2007
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
package org.fest.swing.finder;

import java.awt.Component;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Condition;

/**
 * Understands a condition that is satisfied if a GUI component that matches certain search criteria can be found.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class ComponentFoundCondition extends Condition {

  private final ComponentFinder finder;
  private final ComponentMatcher matcher;

  private Component found;

  /**
   * Creates a new <code>{@link ComponentFoundCondition}</code>
   * @param description the description of this condition.
   * @param finder performs the component search.
   * @param matcher specifies the condition that the component we are looking for needs to match.
   */
  ComponentFoundCondition(String description, ComponentFinder finder, ComponentMatcher matcher) {
    super(description);
    this.finder = finder;
    this.matcher = matcher;
  }

  /**
   * Returns <code>true</code> if a component that matches the search criteria in this condition's
   * <code>{@link ComponentMatcher}</code> can be found. Otherwise, this method returns <code>false</code>.
   * @return <code>true</code> if a matching component can be found, <code>false</code> otherwise.
   */
  public boolean test() {
    try {
      found = finder.find(matcher);
    } catch (ComponentLookupException e) {
      found = null;
    }
    return found != null;
  }

  Component found() { return found; }
}
