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

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands an action, executed in the event dispatch thread, that returns the text used in the "approve button" of a 
 * <code>{@link JFileChooser}</code>.
 *
 * @author Alex Ruiz
 */
class JFileChooserApproveButtonTextQuery extends GuiQuery<String> {
  
  private final JFileChooser fileChooser;

  static String approveButtonTextFrom(JFileChooser fileChooser) {
    return execute(new JFileChooserApproveButtonTextQuery(fileChooser));
  }
  
  private JFileChooserApproveButtonTextQuery(JFileChooser fileChooser) {
    this.fileChooser = fileChooser;
  }

  protected String executeInEDT() {
    String text = fileChooser.getApproveButtonText();
    if (isEmpty(text)) 
      text = fileChooser.getUI().getApproveButtonText(fileChooser);
    return text;
  }
}