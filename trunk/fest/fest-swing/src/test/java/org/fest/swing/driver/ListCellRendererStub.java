/*
 * Created on Apr 5, 2008
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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Understands <code>{@link ListCellRenderer}</code> for testing purposes.
 *
 * @author Yvonne Wang
 */
class ListCellRendererStub extends JLabel implements ListCellRenderer {
  private static final long serialVersionUID = 1L;

  ListCellRendererStub(String text) {
    this.setText(text);
  }

  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
      boolean cellHasFocus) {
    return this;
  }
}