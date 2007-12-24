/*
 * Created on Dec 23, 2007
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
package org.fest.swing.format;

import java.awt.Component;

import javax.swing.JFileChooser;

import static java.lang.String.valueOf;
import static org.fest.util.Strings.*;

/**
 * Understands a formatter for <code>{@link JFileChooser}</code>es.
 *
 * @author Yvonne Wang
 */
public class JFileChooserFormatter extends ComponentFormatterTemplate {

  /**
   * Returns the <code>String</code> representation of the given <code>{@link Component}</code>, which should be a
   * <code>{@link JFileChooser}</code> (or subclass.)
   * @param c the given <code>Component</code>.
   * @return the <code>String</code> representation of the given <code>JFileChooser</code>.
   */
  protected String doFormat(Component c) {
    JFileChooser fileChooser = (JFileChooser)c;
    return concat(
        fileChooser.getClass().getName(), "[",
        "name=", quote(fileChooser.getName()), ", ",
        "dialogTitle=", quote(fileChooser.getDialogTitle()), ", ",
        "dialogType=", DialogType.lookup(fileChooser.getDialogType()), ", ",
        "currentDirectory=", fileChooser.getCurrentDirectory(), ", ",
        "enabled=", valueOf(fileChooser.isEnabled()),
        "]"
    );
  }

  private static enum DialogType {
    OPEN_DIALOG(JFileChooser.OPEN_DIALOG),
    SAVE_DIALOG(JFileChooser.SAVE_DIALOG),
    CUSTOM_DIALOG(JFileChooser.CUSTOM_DIALOG);

    private final int type;

    private DialogType(int type) { this.type = type; }

    static String lookup(int type) {
      for (DialogType t : values()) if (t.type == type) return t.name();
      return String.valueOf(type);
    }
  }

  /**
   * Indicates that this formatter supports <code>{@link JFileChooser}</code> only.
   * @return <code>JFileChooser.class</code>.
   */
  public Class<? extends Component> targetType() {
    return JFileChooser.class;
  }
}
