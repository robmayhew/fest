/*
 * Created on Aug 8, 2008
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

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the text used in the "cancel button" of a
 * <code>{@link JFileChooser}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JFileChooserCancelButtonTextQuery extends GuiQuery<String> {

  private static final JFileChooserCancelButtonTextQuery QUERY_INSTANCE = new JFileChooserCancelButtonTextQuery();
  
  private static final String CANCEL_BUTTON_TEXT_KEY = "FileChooser.cancelButtonText";

  static String cancelButtonText() {
    return QUERY_INSTANCE.run();
  }

  private JFileChooserCancelButtonTextQuery() {}

  protected String executeInEDT() {
    return UIManager.getString(CANCEL_BUTTON_TEXT_KEY);
  }
}