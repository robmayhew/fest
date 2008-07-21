/*
 * Created on Jul 20, 2008
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
package org.fest.assertions.condition;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.Condition;

import static org.fest.util.Collections.list;

/**
 * Understands verification that a given value satisfy at least one condition from a given list of conditions.
 * @param <T> the generic type of the conditions to use.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class AnyCondition<T extends Object> extends Condition<T> {
  
  private final List<Condition<T>> conditions = new ArrayList<Condition<T>>();
  
  /**
   * Creates a new <code>{@link AnyCondition}</code>.
   * @param <T> the generic type of the conditions to accept.
   * @param conditions the conditions to evaluate.
   * @return the created <code>AnyCondition</code>.
   * @throws NullPointerException if <code>conditions</code> is <code>null</code>.
   */
  public static <T extends Object> Condition<T> any(Condition<T>... conditions) {
    return new AnyCondition<T>(conditions);
  }

  private AnyCondition(Condition<T>[] conditions) {
    super(description(conditions));
    this.conditions.addAll(list(conditions));
  }

  private static <T extends Object> String description(Condition<T>[] conditions) {
    if (conditions == null) throw new NullPointerException("The array of conditions should not be null");
    StringBuilder b = new StringBuilder();
    b.append("Any of:[");
    int conditionCount = conditions.length;
    for (int i = 0; i < conditionCount; i++) {
      b.append(conditions[i].description());
      if (i < conditionCount - 1) b.append(", ");
    }
    b.append("]");
    return b.toString();
  }
  
  /**
   * Verifies that the given value satisfies at least one of the conditions.
   * @param value the value to verify.
   * @throws AssertionError if the value does not satisfy any of the conditions.
   */
  public boolean matches(T value) {
    for (Condition<T> condition : conditions) if (condition.matches(value)) return true;
    return false;
  }
}
