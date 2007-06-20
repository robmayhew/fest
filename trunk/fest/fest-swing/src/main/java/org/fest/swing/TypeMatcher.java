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
package org.fest.swing;

import java.awt.Component;

import abbot.finder.matchers.ClassMatcher;

/**
 * Understands <code>{@link java.awt.Component}</code> matching by type.
 *
 * @author Alex Ruiz 
 */
public final class TypeMatcher extends ClassMatcher implements ComponentMatcher {

  /**
   * Creates a new </code>{@link TypeMatcher}</code>.
   * @param type the type of the component we are looking for.
   */
  public TypeMatcher(Class<?> type) {
    super(type);
  }

  /**
   * Creates a new </code>{@link TypeMatcher}</code>.
   * @param type the type of the component we are looking for.
   * @param requireShowing indicates if the component we are looking should be visible or not.
   */
  public TypeMatcher(Class<?> type, boolean requireShowing) {
    super(type, requireShowing);
  }

  /**
   * @return whether the type and visibility of the given <code>Component</code> matches the values specified in this
   *         matcher.
   */
  @Override public boolean matches(Component c) {
    return super.matches(c);
  }
}
