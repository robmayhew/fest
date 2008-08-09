/*
 * Created on Apr 5, 2008
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
package org.fest.swing.driver;

import java.awt.Component;

import org.fest.swing.core.Condition;

import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.IsComponentEnabledTask.isEnabled;
import static org.fest.util.Strings.concat;

/**
 * Understands a condition that verifies that a component is enabled.
 *
 * @author Yvonne Wang
 */
class ComponentEnabledCondition extends Condition {
  private final Component c;

  ComponentEnabledCondition(Component c) {
    super(concat(format(c), " to be enabled"));
    this.c = c;
  }

  public boolean test() {
    return isEnabled(c);
  }
}