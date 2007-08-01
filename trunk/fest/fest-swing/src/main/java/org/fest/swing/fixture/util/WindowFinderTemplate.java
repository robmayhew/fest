/*
 * Created on Jul 31, 2007
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

import java.awt.Component;
import java.awt.Window;

import static java.lang.System.currentTimeMillis;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.quote;

import org.fest.swing.ComponentFinder;
import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.WindowFixture;

/**
 * Understands a template for <code>{@link Window}</code> finders.
 * @param <T> the type of window this finder can search. 
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class WindowFinderTemplate<T extends Window> {

  private static final long TIMEOUT = 5000;
  
  private String windowName;
  private Class<? extends T> windowType;
  private long timeout = TIMEOUT;
  
  WindowFinderTemplate(String windowName) {
    if (isEmpty(windowName))
      throw new IllegalArgumentException("The name of the window to find should not be empty or null");
    this.windowName = windowName;
  }
  
  WindowFinderTemplate(Class<? extends T> windowType) {
    this.windowType = windowType;
    if (windowType == null) 
      throw new IllegalArgumentException("The type of window to find should not be null");
  }

  /**
   * Sets the timeout for this finder. The window to search should be found within the given time period. 
   * @param timeout the number of milliseconds before stopping the search.
   * @return this finder.
   */
  public WindowFinderTemplate timeout(long timeout) {
    if (timeout < 0) throw new IllegalArgumentException("Timeout cannot be a negative number");
    this.timeout = timeout;
    return this;
  }

  /**
   * Finds a window by name or type.
   * @param robot contains the underlying finding to delegate the search to.
   * @return a fixture capable of managing the found window.
   * @throws ComponentLookupException if a window with the given name or of the given type could not be found.
   */
  public abstract WindowFixture<T> with(RobotFixture robot);

  /**
   * Finds the window using either by name or type.
   * @param robot contains the underlying finding to delegate the search to.
   * @return the found window.
   * @throws ComponentLookupException if a window with the given name or of the given type could not be found.
   */
  @SuppressWarnings("unchecked") 
  protected final T findWindowWith(RobotFixture robot) {
    ComponentFinder finder = robot.finder();
    ComponentMatcher matcher = matcher();
    long start = currentTimeMillis();
    while (true) {
      Component c = null;
      try {
        c = finder.find(matcher);
      } catch (ComponentLookupException e) {
        if (isTimeout(start)) throw cannotFindWindow();
        sleep();
        continue;
      }
      return (T)c;
    }
  }
  
  private ComponentMatcher matcher() {
    if (searchingByType()) return typeMatcher();
    return nameMatcher();
  }

  protected abstract ComponentMatcher nameMatcher();
  
  private ComponentMatcher typeMatcher() {
    return new ComponentMatcher() {
      public boolean matches(Component c) {
        return c != null && windowType().isAssignableFrom(c.getClass());
      }
    };
  }
  
  private boolean isTimeout(long startTime) {
    long timePassed = currentTimeMillis() - startTime;
    return timePassed >= timeout;
  }
  
  private ComponentLookupException cannotFindWindow() {
    String message = "Cannot find window ";
    if (searchingByType()) message = concat(message, "of type ", windowType().getName());
    else message = concat(message, "with name ", quote(windowName()));
    throw new ComponentLookupException(message);
  }

  private void sleep() {
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }  

  protected final String windowName() { return windowName; }
  protected final Class<? extends T> windowType() { return windowType; }
  
  protected final boolean searchingByType() { return windowType != null; }
}
