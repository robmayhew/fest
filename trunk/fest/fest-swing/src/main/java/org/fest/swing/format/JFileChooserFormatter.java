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
public class JFileChooserFormatter implements ComponentFormatter {

  private enum Type {
    
    OPEN_DIALOG(JFileChooser.OPEN_DIALOG), 
    SAVE_DIALOG(JFileChooser.SAVE_DIALOG), 
    CUSTOM_DIALOG(JFileChooser.CUSTOM_DIALOG);
    
    private final int type;
    
    private Type(int type) {
      this.type = type;
    }

    static String lookup(int type) {
      for (Type t : values()) if (t.type == type) return t.name();
      return String.valueOf(type);
    }
  }
  
  /**
   * Returns the <code>String</code> representation of the given <code>{@link Component}</code>, which should be a
   * <code>{@link JFileChooser}</code> (or subclass.)
   * @param c the given <code>Component</code>.
   * @return the <code>String</code> representation of the given <code>JFileChooser</code>.
   * @throws IllegalArgumentException if the given <code>Component</code> is not a <code>JFileChooser</code>.
   */
  public String format(Component c) {
    if (!(c instanceof JFileChooser)) 
      throw new IllegalArgumentException(concat("This formatter only supports ", targetType().getName()));
    JFileChooser fileChooser = (JFileChooser)c;
    return concat(
        fileChooser.getClass().getName(), "[",
        "name=", quote(fileChooser.getName()), ", ",
        "dialogTitle=", quote(fileChooser.getDialogTitle()), ", ",
        "dialogType=", Type.lookup(fileChooser.getDialogType()), ", ",
        "currentDirectory=", fileChooser.getCurrentDirectory(), ", ",
        "enabled=", valueOf(fileChooser.isEnabled()),
        "]"
    );
  }
  
  /**
   * Indicates that this formatter supports <code>{@link JFileChooser}</code> only.
   * @return <code>JFileChooser.class</code>.
   */
  public Class<? extends Component> targetType() {
    return JFileChooser.class;
  }
}
