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

import javax.swing.JOptionPane;

import static java.lang.String.valueOf;
import static org.fest.util.Strings.*;

/**
 * Understands a formatter for <code>{@link JOptionPane}</code>s.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneFormatter extends ComponentFormatterTemplate {

  /**
   * Returns the <code>String</code> representation of the given <code>{@link Component}</code>, which should be a
   * <code>{@link JOptionPane}</code> (or subclass.)
   * @param c the given <code>Component</code>.
   * @return the <code>String</code> representation of the given <code>JOptionPane</code>.
   */
  protected String doFormat(Component c) {
    JOptionPane optionPane = (JOptionPane)c;
    return concat(
        optionPane.getClass().getName(), "[",
        "message=", quote(optionPane.getMessage()), ", ",
        "messageType=", MessageType.lookup(optionPane.getMessageType()), ", ",
        "optionType=", OptionType.lookup(optionPane.getOptionType()), ", ",
        "enabled=", valueOf(optionPane.isEnabled()), ", ",
        "showing=", valueOf(optionPane.isShowing()),
        "]"
    );
  }

  private static enum MessageType {
    ERROR_MESSAGE(JOptionPane.ERROR_MESSAGE), INFORMATION_MESSAGE(JOptionPane.INFORMATION_MESSAGE),
    WARNING_MESSAGE(JOptionPane.WARNING_MESSAGE), QUESTION_MESSAGE(JOptionPane.QUESTION_MESSAGE),
    PLAIN_MESSAGE(JOptionPane.PLAIN_MESSAGE);

    private final int type;

    private MessageType(int type) { this.type = type; }

    static String lookup(int type) {
      for (MessageType m : values()) if (m.type == type) return m.name();
      return String.valueOf(type);
    }
  }

  private static enum OptionType {
    DEFAULT_OPTION(JOptionPane.DEFAULT_OPTION), YES_NO_OPTION(JOptionPane.YES_NO_OPTION),
    YES_NO_CANCEL_OPTION(JOptionPane.YES_NO_CANCEL_OPTION), OK_CANCEL_OPTION(JOptionPane.OK_CANCEL_OPTION);

    private final int type;

    private OptionType(int type) { this.type = type; }

    static String lookup(int type) {
      for (OptionType m : values()) if (m.type == type) return m.name();
      return String.valueOf(type);
    }
  }

  /**
   * Indicates that this formatter supports <code>{@link JOptionPane}</code> only.
   * @return <code>JOptionPane.class</code>.
   */
  public Class<? extends Component> targetType() {
    return JOptionPane.class;
  }
}
