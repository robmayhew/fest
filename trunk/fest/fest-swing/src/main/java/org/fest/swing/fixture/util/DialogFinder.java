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
import java.awt.Dialog;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.DialogFixture;

/**
 * Understands a finder for <code>{@link Dialog}</code>s. This class cannot be used directly, please see 
 * <code>{@link WindowFinder}</code>.
 *
 * @author Yvonne Wang 
 * @author Alex Ruiz
 */
public final class DialogFinder extends WindowFinderTemplate<Dialog> {

  DialogFinder(String dialogName) {
    super(dialogName);
  }

  DialogFinder(Class<? extends Dialog> dialogType) {
    super(dialogType);
  }

  /**
   * Finds a <code>{@link Dialog}</code> by name or type.
   * @param robot contains the underlying finding to delegate the search to.
   * @return a <code>DialogFixture</code> managing the found <code>Dialog</code>.
   * @throws ComponentLookupException if a <code>Dialog</code> with the given name or of the given type could not be
  *           found.
   */
  public DialogFixture using(RobotFixture robot) {
    return new DialogFixture(robot, findWindowWith(robot));
  }
  
  protected ComponentMatcher nameMatcher() {
    return new ComponentMatcher() {
      public boolean matches(Component c) {
        return c instanceof Dialog && windowName().equals(c.getName());
      }
    };
  }
}