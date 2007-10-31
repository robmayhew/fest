/*
 * Created on Sep 11, 2007
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
package org.fest.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.UIManager;

import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * Understands the base window for all GUI tests.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestDialog extends JDialog {

  private static final long serialVersionUID = 1L;

  public static TestDialog showInTest(Frame owner) {
    TestDialog d = new TestDialog(owner);
    d.display();
    return d;
  }
  
  public TestDialog(Frame owner) {
    super(owner);
    setLayout(new FlowLayout());
  }

  public void addComponents(Component...components) {
    for (Component c : components) add(c);
  }
  
  public void display() {
    beVisible(new Dimension(400, 200));
  }
  
  public void beVisible(final Dimension size) {
    try {
      invokeAndWait(new Runnable() {
        public void run() {
          beforeDisplayed();
          chooseLookAndFeel();
          setPreferredSize(size);
          pack();
          setVisible(true);
        }
      });
    } catch (Exception e) {
      throw new RuntimeException("Unable to show dialog", e);
    }
  }
  
  protected void beforeDisplayed() {}

  protected void chooseLookAndFeel() {
    lookNative();
  }
  
  private void lookNative() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
      ignored.printStackTrace();
    }
  }
  
  public void destroy() {
    invokeLater(new Runnable() {
      public void run() {
        setVisible(false);
        dispose();
      }
    });
  }
}
