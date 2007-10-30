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

import java.awt.Window;
import java.util.concurrent.TimeUnit;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.WindowFixture;

/**
 * Understands a template for <code>{@link Window}</code> finders.
 * @param <T> the type of window this finder can search. 
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class WindowFinderTemplate<T extends Window> extends ComponentFinderTemplate<T> {

  WindowFinderTemplate(String windowName) {
    super(windowName);
  }
  
  WindowFinderTemplate(Class<? extends T> windowType) {
    super(windowType);
  }

  WindowFinderTemplate withTimeout(long timeout) {
    return (WindowFinderTemplate)super.withTimeout(timeout);
  }

  WindowFinderTemplate withTimeout(long timeout, TimeUnit unit) {
    return (WindowFinderTemplate) super.withTimeout(timeout, unit);
  }
  
  /**
   * Finds a window by name or type using the given robot.
   * @param robot contains the underlying finding to delegate the search to.
   * @return a fixture capable of managing the found window.
   * @throws ComponentLookupException if a window with the given name or of the given type could not be found.
   */
  public abstract WindowFixture<T> using(RobotFixture robot);

  protected ComponentLookupException cannotFindComponent() {
    String message = "Cannot find window ";
    if (searchingByType()) message = concat(message, "of type ", componentType().getName());
    else message = concat(message, "with name ", quote(componentName()));
    throw new ComponentLookupException(message);
  }
}
