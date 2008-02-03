/*
 * Created on Jan 20, 2008
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

import static org.fest.swing.util.Objects.*;
import static org.fest.swing.util.Strings.isDefaultToString;
import static org.fest.util.Strings.isEmpty;

import java.awt.Component;

import javax.swing.JLabel;

/**
 * Understands Utility methods related to rendering text in cells of <code>Component</code>s that render
 * multiple items (e.g. <code>JList</code>s, <code>JTree</code>s.)
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class CellRendererComponents {

  static String textFrom(Component cellRendererComponent) {
    if (!(cellRendererComponent instanceof JLabel)) return null;
    String text = ((JLabel)cellRendererComponent).getText();
    if (text != null) text = text.trim();
    if (!isEmpty(text) && !isDefaultToString(text)) return text;
    text = toStringOf(text);
    return DEFAULT_TO_STRING.equals(text) ? null : text;
  }

  private CellRendererComponents() {}
}
