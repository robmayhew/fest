/*
 * Created on Nov 6, 2008
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

import java.awt.Container;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.driver.ComponentResizableQuery.isResizable;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabled;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands an action, executed in the event dispatch thread, that returns all the necessary information needed to
 * resize a <code>{@link Container}</code>.
 *
 * @author Alex Ruiz
 */
final class ContainerResizeInfoQuery {

  @RunsInEDT
  static ContainerResizeInfo resizeInfoOf(final Container c) {
    return execute(new GuiQuery<ContainerResizeInfo>() {
      protected ContainerResizeInfo executeInEDT() {
        validateIsEnabled(c);
        if (!isResizable(c)) 
          throw actionFailure(concat("Expecting container ", format(c), " to be resizable by the user"));
        return new ContainerResizeInfo(c.getWidth(), c.getHeight(), c.getInsets());
      }
    });
  }
  
  private ContainerResizeInfoQuery() {} 
}
