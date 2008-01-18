/*
 * Created on Dec 21, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture.bug76;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=76">Bug 76</a>.
 * 
 * @author Wim Deblauwe
 */
public class JOptionPaneStarter extends JDialog {

  private static final long serialVersionUID = 1L;

  public JOptionPaneStarter(Frame owner, String message) {
    super(owner, "JOptionPane Starter");
    setContentPane(createContentPane(message));
  }

  private Container createContentPane(String message) {
    JPanel panel = new JPanel();
    panel.add(new JButton(new OpenJOptionPaneAction(message)));
    return panel;
  }

  private class OpenJOptionPaneAction extends AbstractAction {
    private static final long serialVersionUID = 1L;
    
    private final String m_message;

    private OpenJOptionPaneAction(String message) {
      super("Start!");
      m_message = message;
    }

    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(JOptionPaneStarter.this, m_message);
    }
  }
}
