/*
 * Created on Aug 10, 2008
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

import javax.swing.JSplitPane;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the size and divider location of a
 * <code>{@link JSplitPane}</code>.
 *
 * @author Alex Ruiz
 */
public class JSplitPaneSizeAndDividerLocationQuery extends GuiQuery<JSplitPaneSizeAndDividerLocation> {

  private final JSplitPane splitPane;

  static JSplitPaneSizeAndDividerLocation sizeAndDividerLocationOf(JSplitPane splitPane) {
    return execute(new JSplitPaneSizeAndDividerLocationQuery(splitPane));
  }
  
  JSplitPaneSizeAndDividerLocationQuery(JSplitPane splitPane) {
    this.splitPane = splitPane;
  }
  
  protected JSplitPaneSizeAndDividerLocation executeInEDT() {
    return new JSplitPaneSizeAndDividerLocation(splitPane.getSize(), splitPane.getDividerLocation());
  }

}
