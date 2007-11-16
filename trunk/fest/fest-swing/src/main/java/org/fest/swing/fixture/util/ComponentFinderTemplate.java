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
package org.fest.swing.fixture.util;

import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.ComponentFixture;
import static org.fest.swing.fixture.util.FinderConstants.TIMEOUT;
import static org.fest.util.Strings.*;

import java.awt.Component;
import java.util.concurrent.TimeUnit;

/**
 * Understands a template for <code>{@link Component}</code> finders.
 * @param <T> the type of component this finder can search. 
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class ComponentFinderTemplate<T extends Component> {

  private String componentName;
  private Class<? extends T> componentType;
  private long timeout = TIMEOUT;
  
  ComponentFinderTemplate(String componentName) {
    if (isEmpty(componentName))
      throw new IllegalArgumentException("The name of the component to find should not be empty or null");
    this.componentName = componentName;
  }
  
  ComponentFinderTemplate(Class<? extends T> componentType) {
    this.componentType = componentType;
    if (componentType == null) 
      throw new IllegalArgumentException("The type of component to find should not be null");
  }

  ComponentFinderTemplate withTimeout(long timeout) {
    if (timeout < 0) throw new IllegalArgumentException("Timeout cannot be a negative number");
    this.timeout = timeout;
    return this;
  }

  ComponentFinderTemplate withTimeout(long timeout, TimeUnit unit) {
    if (unit == null) throw new IllegalArgumentException("Time unit cannot be null");
    return withTimeout(unit.toMillis(timeout));
  }
  
  /**
   * Finds a component by name or type using the given robot.
   * @param robot contains the underlying finding to delegate the search to.
   * @return a fixture capable of managing the found component.
   * @throws org.fest.swing.WaitTimedOutError if a component with the given name or of the given type could not be found.
   */
  public abstract ComponentFixture<T> using(RobotFixture robot);

  /**
   * Finds the component using either by name or type.
   * @param robot contains the underlying finding to delegate the search to.
   * @return the found component.
   * @throws org.fest.swing.WaitTimedOutError if a component with the given name or of the given type could not be found.
   */
  @SuppressWarnings("unchecked") 
  protected final T findComponentWith(RobotFixture robot) {
    ComponentFoundCondition condition = new ComponentFoundCondition(searchDescription(), robot.finder(), matcher());
    robot.wait(condition, timeout);
    return (T)condition.found();
  }

  private String searchDescription() {
    String message = concat("Find ", componentTypeName());
    if (searchingByType()) message = concat(message, " of type ", componentType().getName());
    else message = concat(message, " with name ", quote(componentName()));
    return message;
  }

  protected abstract String componentTypeName();

  private ComponentMatcher matcher() {
    if (searchingByType()) return typeMatcher();
    return nameMatcher();
  }

  protected final boolean searchingByType() { return componentType != null; }

  private ComponentMatcher typeMatcher() {
    return new ComponentMatcher() {
      public boolean matches(Component c) {
        return c != null && c.isVisible() && componentType().isAssignableFrom(c.getClass());
      }
    };
  }

  protected abstract ComponentMatcher nameMatcher();

  protected final String componentName() { return componentName; }
  protected final Class<? extends T> componentType() { return componentType; }
  protected final long timeout() { return timeout; }
}
