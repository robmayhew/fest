/*
 * Created on Dec 22, 2007
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
package org.fest.swing.core;

import java.awt.Component;

import static java.lang.String.valueOf;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands <code>{@link java.awt.Component}</code> matching by name and type.
 *
 * @author Alex Ruiz
 */
public final class NameAndTypeMatcher implements ComponentMatcher {

  private final String name;
  private final boolean requireShowing;
  private final Class<? extends Component> type;

  /**
   * Creates a new <code>{@link NameAndTypeMatcher}</code>. By default this constructor looks for a 
   * <code>{@link Component}</code> having a matching name and being shown. 
   * @param name the name of the component we are looking for.
   * @param type the type of the component we are looking for.
   */
  public NameAndTypeMatcher(String name, Class<? extends Component> type) {
    this(name, type, true);
  }

  /**
   * Creates a new <code>{@link TypeMatcher}</code>.
   * @param name the name of the component we are looking for.
   * @param type the type of the component we are looking for.
   * @param requireShowing indicates if the component we are looking should be shown or not.
   */
  public NameAndTypeMatcher(String name, Class<? extends Component> type, boolean requireShowing) {
    this.name = name;
    this.type = type;
    this.requireShowing = requireShowing;
  }
  
  /** 
   * Indicates whether the name and visibility of the given <code>{@link java.awt.Component}</code> matches the values
   * specified in this matcher.
   * @return <code>true</code> if the name and visibility of the given <code>Component</code> matches the values
   *         specified in this matcher, <code>false</code> otherwise.
   */
  public boolean matches(Component c) {
    return areEqual(name, c.getName()) && type.isAssignableFrom(c.getClass()) && (!requireShowing || c.isShowing());
  }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "name=", quote(name), ", ",
        "type=", type.getName(), ", ",
        "requireShowing=", valueOf(requireShowing), 
        "]"
    );
  }
}
