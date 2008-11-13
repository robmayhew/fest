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

import java.awt.Component;

import javax.swing.JComboBox;

/**
 * Understands an action that indicates whether editor of a <code>{@link JComboBox}</code> is accessible or not. To be
 * accessible, a <code>JComboBox</code> needs to be enabled and editable. <b>Note:</b> this action is <b>not</b> 
 * executed in the event dispatch thread.
 * @see JComboBox#isEditable()
 * @see Component#isEnabled()
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JComboBoxEditorAccessibleQuery {

  static boolean isEditorAccessible(final JComboBox comboBox) {
    return comboBox.isEditable() && comboBox.isEnabled();
  }

  private JComboBoxEditorAccessibleQuery() {}
}