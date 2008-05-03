/*
 * Created on May 2, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.util.Strings.*;

/**
 * Understands a base class for fixtures for objects that are not Swing GUI components.
 *
 * @author Alex Ruiz
 */
public abstract class NonGUIFixture {

  private static final String PROPERTY_SEPARATOR = " - ";

  private final String description;

  /**
   * Creates a new </code>{@link NonGUIFixture}</code>.
   * @param description this fixture's description. 
   */
  public NonGUIFixture(String description) {
    this.description = description;
  }

  protected final String property(String s) {
    if (isEmpty(description)) return s;
    return concat(description, PROPERTY_SEPARATOR, s);
  }

  /**
   * Returns this fixture's description.
   * @return this fixture's description.
   */
  public final String description() { return description; }
}
