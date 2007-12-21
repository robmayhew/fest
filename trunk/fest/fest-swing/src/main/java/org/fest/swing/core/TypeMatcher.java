/*
 * Created on Jun 18, 2007
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

import abbot.finder.matchers.ClassMatcher;
import static java.lang.String.valueOf;
import static org.fest.util.Strings.concat;

/**
 * Understands <code>{@link java.awt.Component}</code> matching by type.
 *
 * @author Alex Ruiz 
 */
public final class TypeMatcher extends ClassMatcher implements ComponentMatcher {

  private final Class<?> type;
  private final boolean requireShowing;

  /**
   * Creates a new <code>{@link TypeMatcher}</code>.
   * @param type the type of the component we are looking for.
   */
  public TypeMatcher(Class<?> type) {
    this(type, false);
  }

  /**
   * Creates a new <code>{@link TypeMatcher}</code>.
   * @param type the type of the component we are looking for.
   * @param requireShowing indicates if the component we are looking should be visible or not.
   */
  public TypeMatcher(Class<?> type, boolean requireShowing) {
    super(type, requireShowing);
    this.type = type;
    this.requireShowing = requireShowing;
  }

  /**
   * @return whether the type and visibility of the given <code>Component</code> matches the values specified in this
   *         matcher.
   */
  @Override public boolean matches(Component c) {
    return super.matches(c);
  }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "type=", type.getName(), ", ",
        "requireShowing=", valueOf(requireShowing), 
        "]"
    );
  }
}
