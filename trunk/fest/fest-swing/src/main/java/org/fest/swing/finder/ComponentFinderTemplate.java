/*
 * Created on Oct 29, 2007
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
package org.fest.swing.finder;

import java.awt.Component;
import java.util.concurrent.TimeUnit;

import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.NameAndTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.core.TypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.ComponentFixture;

import static org.fest.swing.core.Pause.pause;
import static org.fest.util.Strings.*;

/**
 * Understands a template for <code>{@link Component}</code> finders.
 * @param <T> the type of component this finder can search.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class ComponentFinderTemplate<T extends Component> {

  static final long TIMEOUT = 5000;
  
  private String componentName;
  private Class<? extends T> componentType;
  private long timeout = TIMEOUT;

  ComponentFinderTemplate(String componentName, Class<? extends T> componentType) {
    this(componentType);
    if (isEmpty(componentName))
      throw new IllegalArgumentException("The name of the component to find should not be empty or null");
    this.componentName = componentName;
  }

  ComponentFinderTemplate(Class<? extends T> componentType) {
    this.componentType = componentType;
    if (componentType == null)
      throw new IllegalArgumentException("The type of component to find should not be null");
  }

  ComponentFinderTemplate<T> withTimeout(long timeout, TimeUnit unit) {
    if (unit == null) throw new IllegalArgumentException("Time unit cannot be null");
    return withTimeout(unit.toMillis(timeout));
  }

  ComponentFinderTemplate<T> withTimeout(long timeout) {
    if (timeout < 0) throw new IllegalArgumentException("Timeout cannot be a negative number");
    this.timeout = timeout;
    return this;
  }

  /**
   * Finds a component by name or type using the given robot.
   * @param robot contains the underlying finding to delegate the search to.
   * @return a fixture capable of managing the found component.
   * @throws WaitTimedOutError if a component with the given name or of the given type could not be found.
   */
  public abstract ComponentFixture<T> using(Robot robot);

  /**
   * Finds the component using either by name or type.
   * @param robot contains the underlying finding to delegate the search to.
   * @return the found component.
   * @throws WaitTimedOutError if a component with the given name or of the given type could not be found.
   */
  @SuppressWarnings("unchecked")
  protected final T findComponentWith(Robot robot) {
    ComponentFoundCondition condition = new ComponentFoundCondition(searchDescription(), robot.finder(), matcher());
    pause(condition, timeout);
    return (T)condition.found();
  }

  private String searchDescription() {
    String message = concat("Find ", componentDisplayName());
    if (searchingByType()) return concat(message, " of type ", componentType.getName());
    return concat(message, " with name ", quote(componentName));
  }

  protected abstract String componentDisplayName();

  private ComponentMatcher matcher() {
    if (searchingByType()) return new TypeMatcher(componentType, true);
    return nameMatcher();
  }

  private boolean searchingByType() { return isEmpty(componentName); }

  protected ComponentMatcher nameMatcher() {
    return new NameAndTypeMatcher(componentName, componentType, true);
  }
}
