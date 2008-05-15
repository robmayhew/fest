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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture.bug80;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=80">Bug 80</a>.
 * 
 * @author Wim Deblauwe
 */
public class JDialogStarter extends JDialog {
  private static final long serialVersionUID = 1L;

  public JDialogStarter(Frame owner) {
    super(owner);
    setContentPane(createContentPane());
  }

  private Container createContentPane() {
    JPanel panel = new JPanel();
    JButton startButton = new JButton(new OpenJDialogAction());
    startButton.setName("start");
    panel.add(startButton);
    return panel;
  }

  private class OpenJDialogAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    private OpenJDialogAction() { super("Start!"); }

    public void actionPerformed(ActionEvent e) {
      NestedJDialog dialog = new NestedJDialog(JDialogStarter.this);
      dialog.pack();
      dialog.setVisible(true);
    }
  }

  private static class NestedJDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private NestedJDialog(JDialog owner) {
      super(owner, true);
      setContentPane(createContentPane());
      setName("NestedDialog");
    }

    private JPanel createContentPane() {
      JPanel result = new JPanel();
      result.add(new JLabel("Nested dialog"));
      JButton closeButton = new JButton("Close");
      closeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          NestedJDialog.this.dispose();
        }
      });
      closeButton.setName("close");
      result.add(closeButton);
      return result;
    }
  }
}
