/*
 * Created on Aug 28, 2008
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
package org.fest.swing.factory;

import javax.swing.JButton;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands creation of <code>{@link JButton}</code>s in the event dispatch thread.
 *
 * @author Alex Ruiz
 */
public final class JButtons {

  private JButtons() {}

  public static JButtonFactory button() {
    return new JButtonFactory();
  }
  
  public static class JButtonFactory {
    boolean enabled;
    String name;
    String text;
    
    public JButtonFactory enabled(boolean isEnabled) {
      enabled = isEnabled;
      return this;
    }
    
    public JButtonFactory withName(String newName) {
      name = newName;
      return this;
    }
    
    public JButtonFactory withText(String newText) {
      text = newText;
      return this;
    }
    
    public JButton createInEDT() {
      return execute(new GuiQuery<JButton>() {
        protected JButton executeInEDT()  {
          JButton button = new JButton();
          button.setEnabled(enabled);
          button.setName(name);
          button.setText(text);
          return button;
        }
      });
    }
  }
}