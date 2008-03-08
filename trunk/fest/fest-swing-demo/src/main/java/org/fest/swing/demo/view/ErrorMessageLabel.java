/*
 * Created on Mar 7, 2008
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
package org.fest.swing.demo.view;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import static org.fest.swing.demo.view.Icons.DIALOG_ERROR_ICON;

/**
 * Understands a <code>{@link JLabel}</code> that displays error messages.
 *
 * @author Alex Ruiz
 */
public class ErrorMessageLabel extends JLabel {

  private static final long serialVersionUID = 1L;
  private final JComponent inputField;

  /**
   * Creates a new </code>{@link ErrorMessageLabel}</code>.
   * @param inputField the input field to bind this label to.
   */
  public ErrorMessageLabel(JComponent inputField) {
    this.inputField = inputField;
    setIcon(DIALOG_ERROR_ICON);
    setHorizontalTextPosition(SwingConstants.RIGHT);
    setVisible(false);
  }

  void errorMessage(String errorMessage) {
    super.setText(errorMessage);
    setVisible(true);
    inputField.requestFocusInWindow();
  }
}
